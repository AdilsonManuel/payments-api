package com.azm.payments_api.dto;

import com.azm.payments_api.entity.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponseDTO
		(
				
				Long pk_accounts,
				String accountNumber,
				BigDecimal balance,
				BigDecimal dailyTransferLimit,
				BigDecimal dailyTransferredAmount,
				boolean active,
				LocalDateTime createdAt,
				LocalDateTime updatedAt
		)
{
	public static AccountResponseDTO fromEntity(Account account)
	{
		return new AccountResponseDTO(
				account.getPk_accounts(),
				account.getAccountNumber(),
				account.getBalance(),
				account.getDailyTransferLimit(),
				account.getDailyTransferredAmount(),
				account.isActive(),
				account.getCreatedAt(),
				account.getUpdatedAt()
		);
	}
}
