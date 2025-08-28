package com.azm.payments_api.dto;

import com.azm.payments_api.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
		String pk_transaction,
		BigDecimal amount,
		String description,
		Transaction.Transactionstatus status,
		Transaction.TransactionType type,
		String fromAccountNumer,
		String toAccountNumber,
		LocalDateTime createdAt,
		LocalDateTime processedAt
)
{
	public static TransactionResponseDTO fromEntity(Transaction transaction)
	{
		return new TransactionResponseDTO(
				transaction.getTransactionCode(),
				transaction.getAmount(),
				transaction.getDescription(),
				transaction.getTransactionstatus(),
				transaction.getTransactionType(),
				transaction.getFromAccount().getAccountNumber(),
				transaction.getToAccount().getAccountNumber(),
				transaction.getCreatedAt(),
				transaction.getProcessedAt()
		);
	}
}
