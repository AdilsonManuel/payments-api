package com.azm.payments_api.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponse implements Serializable
{
    private String accounNumber;
    private BigDecimal balance;
    private BigDecimal dailyTransferLimit;
    private BigDecimal remainingDailyLimit;


}
