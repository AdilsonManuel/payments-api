/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.azm.payments_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author AdilsonManuel
 */
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class TransferResponse
{

    private String fk_transaction;
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime precessedAt;

    public TransferResponse(String fk_transaction, String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description, String status, LocalDateTime createdAt, LocalDateTime processedAt)
    {
        this.fk_transaction = fk_transaction;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.precessedAt = processedAt;
    }

}
