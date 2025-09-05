/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.azm.payments_api.service;

import com.azm.payments_api.entity.Account;
import com.azm.payments_api.entity.AuditLog;
import com.azm.payments_api.entity.AuditLog.AuditLevel;
import com.azm.payments_api.entity.Transaction;
import com.azm.payments_api.entity.Transaction.Transactionstatus;
import com.azm.payments_api.repository.AccountRepository;
import com.azm.payments_api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author AdilsonManuel
 */
@Service
@Transactional
public class TransferService
{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuditService auditService;

    public Transaction performTransfer (String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description, Long fk_user)
    {
        // Validações básicas
        if (amount.compareTo (BigDecimal.ZERO) <= 0)
        {
            throw new RuntimeException ("Transfer amount must be positive");
        }
        if (fromAccountNumber.equals (toAccountNumber))
        {
            throw new RuntimeException ("Cannot Transfer to the same Account");
        }

        // Buscar contas
        Account fromAccount = accountRepository.findByAccountNumber (fromAccountNumber).orElseThrow (() -> new RuntimeException ("Source account not found"));

        Account toAccount = accountRepository.findByAccountNumber (toAccountNumber).orElseThrow (() -> new RuntimeException ("Destination account not found"));

        // Verificar se o usuário é dono da conta de origem
        if (!fromAccount.getUser ().getPk_users ().equals (fk_user))
        {
            auditService.logAction ("UNAUTHORIZED_TRANSFER_ATTEMPT", "Transaction", null, fk_user, null, null, String.format ("unthorized transfer attempt from account %s", fromAccountNumber), AuditLevel.WARNING);

            throw new RuntimeException ("Access denied to source account");
        }

        // Verificar se as contas estão activas
        if (!fromAccount.isActive () || toAccount.isActive ())
        {
            throw new RuntimeException ("one or both accounts are inactive");
        }

        // Verificar saldo suficiente
        if (fromAccount.getBalance ().compareTo (amount) < 0)
        {
            auditService.logAction ("INSUFICIENT_BALANCE", "transaction", null, fk_user, fromAccount.getUser ().getUserName (), null, String.format ("Insufficient balance for transfer of %s from account %s", amount, fromAccountNumber), AuditLevel.WARNING);

            throw new RuntimeException ("Insufficient balance");
        }

        // Verificar limite diário
        checkDailyTransferLimit (toAccount, amount);

        // Criar transação
        String transactionId = UUID.randomUUID ().toString ();

        Transaction transaction = new Transaction (transactionId, amount, description, fromAccount, toAccount);
        transaction.setTransactionstatus (Transactionstatus.PENDING);

        try
        {
            // Processar transferência
            fromAccount.setBalance (fromAccount.getBalance ().subtract (amount));
            toAccount.setBalance (toAccount.getBalance ().add (amount));

            updateDailyTransferLimit (fromAccount, amount);

            // Salvar contas actualizadas
            accountRepository.save (fromAccount);
            accountRepository.save (toAccount);

            // Marcar transação como concluída
            transaction.setTransactionstatus (Transactionstatus.COMPLETED);
            transaction.setProcessedAt (LocalDateTime.now ());

            Transaction savedTransaction = transactionRepository.save (transaction);

            // Log de auditoria
            auditService.logAction ("TRANSFER_COMPLETED", "Transaction", savedTransaction.getPk_transactions (), fk_user, fromAccount.getUser ().getUserName (), null, String.format ("Transfer of %s from %s to %s completed successfully", amount, fromAccountNumber, toAccountNumber));

            return savedTransaction;
        }
        catch (Exception e)
        {
            transaction.setTransactionstatus (Transactionstatus.FAILED);
            transactionRepository.save (transaction);

            auditService.logAction ("TRANSFER_FAILED", "Transaction", transaction.getPk_transactions (), fk_user, fromAccount.getUser ().getUserName (), null, String.format ("Transfer failed: %s", e.getMessage ()), AuditLog.AuditLevel.ERROR);

            throw new RuntimeException ("Transfer failed" + e.getMessage ());
        }
    }

    private void checkDailyTransferLimit (Account account, BigDecimal tansferAccount)
    {
        LocalDateTime today = LocalDateTime.now ();
        LocalDateTime startOfday = today.with (LocalDateTime.MIN);
        LocalDateTime endOIday = today.with (LocalDateTime.MAX);

        // Verificar se é um novo dia e resetar o limite se necessário
        if (account.getLastTransferDate () == null || account.getLastTransferDate ().toLocalDate ().isBefore (today.toLocalDate ()))
        {
            accountRepository.save (account);
        }

        BigDecimal totalTransferredToday = account.getDailyTransferredAmount ();
        BigDecimal newTotal = totalTransferredToday.add (tansferAccount);

        if (newTotal.compareTo (account.getDailyTransferLimit ()) > 0)
        {
            auditService.logAction ("DAILY_LIMIT_EXCEEDED", "Account", account.getPk_accounts (), account.getUser ().getPk_users (), account.getUser ().getUserName (), null, String.format ("Daily transfer limit exceeded. Limit: %s, Attempted: %s, Already transferred: %s", account.getDailyTransferLimit (), tansferAccount, totalTransferredToday), AuditLog.AuditLevel.WARNING);
            throw new RuntimeException ("Daily transfer limit exceeded");
        }
    }

    public void updateDailyTransferLimit (Account account, BigDecimal amount)
    {
        account.setDailyTransferredAmount (amount);
        account.setLastTransferDate (LocalDateTime.now ());
    }

    public BigDecimal getRemaingDailyLimit (Long fk_account, Long fk_user)
    {
        Account account = accountRepository.findById (fk_account).orElseThrow (() -> new RuntimeException ("Account not found"));

        if (!account.getUser ().getPk_users ().equals (fk_user))
        {
            throw new RuntimeException ("Access denied to this account");
        }

        LocalDateTime today = LocalDateTime.now ();

        // Se é um novo dia, o limite está completo
        if (account.getLastTransferDate () == null || account.getLastTransferDate ().toLocalDate ().isBefore (today.toLocalDate ()))
        {
            return account.getDailyTransferLimit ();
        }
        return account.getDailyTransferLimit ().subtract (account.getDailyTransferredAmount ());
    }
}
