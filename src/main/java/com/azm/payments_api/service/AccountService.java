package com.azm.payments_api.service;

import com.azm.payments_api.entity.Account;
import com.azm.payments_api.entity.User;
import com.azm.payments_api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AccountService
{
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuditService auditService;

    @Value("${app.daily-transfer-limit}")
    private BigDecimal defaultDailyTransferLimit;

    public Account createAccount(User user)
    {
        String accounNumber = generateAccountNumber();
        Account account = new Account(accounNumber, defaultDailyTransferLimit, user);
        Account savedAccount = accountRepository.save(account);

        auditService.logAction("ACCOUNT_CREATED", "Account", savedAccount.getPk_accounts(), user.getPk_users(), user.getUserName(), null, "Account Created with Number" + accounNumber);

        return savedAccount;
    }

    public Optional<Account> findByAccountNumber(String accountNumber)
    {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public List<Account> findActiveAccountByuserPk(Long pk_user)
    {
        return accountRepository.findActiveAccountByUserId(pk_user);
    }

    public BigDecimal getBalance(Long fk_account, Long fk_user)
    {
        Account account = accountRepository.findById(fk_account).orElseThrow(() -> new RuntimeException("Account not found"));

        auditService.logAction("BALANCE_CONSULTED", "Account", fk_account, fk_user, account.getUser().getUserName(), null, "Balance Consulted for account: " + account.getAccountNumber());

        return account.getBalance();
    }

    public void updateBalance(Long pk_account, BigDecimal newBalance)
    {
        Account account = accountRepository.findById(pk_account).orElseThrow(() -> new RuntimeException("Account Not Found");

        BigDecimal oldBalance = account.getBalance();
        account.setBalance(newBalance);
        accountRepository.save(account);

        auditService.logAction("BALANCE_UPDATED", "Account", pk_account, account.getUser().getPk_users(), account.getUser().getUserName(), null, String.format("Balance updated from %s to %s", oldBalance, newBalance));
    }

    public void updateDailyTransferedAccount(Long pk_account, BigDecimal amount)
    {
        Account account = accountRepository.findById(pk_account).orElseThrow(() -> new RuntimeException("Accoutn Not Found"));

        account.setDailyTransferredAmount(account.getDailyTransferredAmount().add(amount));
        accountRepository.save(account);


    }

    public void resetdailyTranferredaMOUNT(Long pk_account)
    {
        Account account = accountRepository.findById(pk_account).orElseThrow(() -> new RuntimeException("Account Not Found"));

        account.setDailyTransferredAmount(BigDecimal.ZERO);
        accountRepository.save(account);
    }


    private String generateAccountNumber()
    {
        String accountNumber;

        do {
            accountNumber = String.format("%010d", Math.abs(UUID.randomUUID().hashCode() % 10000000000L));
        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }
}
