package com.azm.payments_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pk_transactions;
	
	@Column(name = "transaction_code", unique = true, nullable = false, updatable = false)
	private String transactionCode;
	
	@NotNull
	@DecimalMin(value = "0.01")
	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal amount;
	
	@Size(max = 255)
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Transactionstatus transactionstatus = Transactionstatus.PENDING;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionType transactionType = TransactionType.TRANSFER;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "from_account_code")
	private Account fromAccount;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "to_account_code")
	private Account toAccount;
	
	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "processed_at")
	private LocalDateTime processedAt;
	
	
	public enum Transactionstatus
	{
		PENDING, COMPLETED, FAILED, CANCELED
	}
	
	public enum TransactionType
	{
		TRANSFER, DEPOSIT, WITHDRAWL
	}
}
