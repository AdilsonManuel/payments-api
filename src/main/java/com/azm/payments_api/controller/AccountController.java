package com.azm.payments_api.controller;

import com.azm.payments_api.entity.Account;
import com.azm.payments_api.entity.BalanceResponse;
import com.azm.payments_api.security.UserPrincipal;
import com.azm.payments_api.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Accounts", description = "Account management APIs")
@SecurityRequirement(name = "bearerAuth")
public class AccountController
{
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransferService transferService;

    @GetMapping("my-accounts")
    @Operation(summary = "Get User Accounts", description = "Get All active for the authenticated user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getMuAccounts(@AuthenticationPrincipal UserPrincipal userPrincipal)
    {
        List<Account> accounts = accountService.findActiveAccountByuserPk(userPrincipal.getPk_user());
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{pk_account}/balance")
    @Operation(summary = "Get account balance", description = "Get balance and daily transfer limit information for a specific account")
    @PreAuthorize("hasRole('USER or hasRole('ADMIN")
    public ResponseEntity<?> getbalance(@PathVariable Long pk_account, @AuthenticationPrincipal UserPrincipal userPrincipal)
    {
        try {
            BigDecimal balance = accountService.getBalance(pk_account, userPrincipal.getPk_user());
            BigDecimal remainingLimit = transferService.getRemainingDailyLimit(accountId, userPrincipal.getId());

            // Buscar informações da conta para incluir na resposta
            Account account = accountService.findActiveAccountByuserPk(userPrincipal.getPk_user()).stream().filter(acc -> acc.getPk_accounts().equals(pk_account)).findFirst().orElseThrow(() -> new RuntimeException("Account not found"));

            BalanceResponse response = new BalanceResponse(account.getAccountNumber(), balance, account.getDailyTransferLimit(), remainingLimit);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/transfer")
    @Operation(summary = "Perform transfer", description = "Transfer money between accounts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> performTransfer(@Valid @RequestBody TransferRequest transferRequest, @AuthenticationPrincipal UserPrincipal userPrincipal)
    {
        try {
            Transaction transaction = transferService.performTransfer(transferRequest.getFromAccountNumber(), transferRequest.getToAccountNumber(), transferRequest.getAmount(), transferRequest.getDescription(), userPrincipal.getId());

            TransferResponse response = new TransferResponse(transaction.getTransactionId(), transaction.getFromAccount().getAccountNumber(), transaction.getToAccount().getAccountNumber(), transaction.getAmount(), transaction.getDescription(), transaction.getStatus().name(), transaction.getCreatedAt(), transaction.getProcessedAt());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{accountId}/daily-limit")
    @Operation(summary = "Get remaining daily limit", description = "Get remaining daily transfer limit for a specific account")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getRemainingDailyLimit(@PathVariable Long pk_account, @AuthenticationPrincipal UserPrincipal userPrincipal)
    {
        try {
            BigDecimal remaininglimit = transferService.getRemainingDailyLimit(accountId, userPrincipal.getId());
            Map<String, BigDecimal> response = new HashMap<>();
            response.put("remainingdailyLimit", remaininglimit);

            return ResponseEntity.ok(response)
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

}
