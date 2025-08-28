package com.azm.payments_api.dto;

import com.azm.payments_api.entity.Transaction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record TransactionRequestDTO(
		@NotNull BigDecimal amount,
		@Size(max = 255) String description,
		@NotNull Transaction.TransactionType transactionType,
		@NotNull String fromAccountNumber,
		@NotNull String toAccountNumber
)
{
}
