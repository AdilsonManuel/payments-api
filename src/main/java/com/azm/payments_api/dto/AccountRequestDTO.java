package com.azm.payments_api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountRequestDTO(@NotBlank String accountNumber,
                                @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal dailyTransferLimit,
                                @NotNull Long pk_accounts)
{

}
