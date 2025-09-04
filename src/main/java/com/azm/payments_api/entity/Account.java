package com.azm.payments_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk_accounts;

    @Column(name = "account_number", unique = true, nullable = false, updatable = false)
    private String accountNumber;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO.setScale (2, RoundingMode.HALF_UP);

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "daily_transfer_limit", nullable = false, precision = 19, scale = 2)
    private BigDecimal dailyTransferLimit;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "daily_transferred_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal dailyTransferredAmount = BigDecimal.ZERO.setScale (2, RoundingMode.HALF_UP);

    @Column(name = "last_transfer_date")
    private LocalDateTime lastTransferDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pk_users")
    private User user;

    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transaction> outgoingTransactions = new HashSet<> ();

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transaction> incomingTransactions = new HashSet<> ();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Account (String accountNumber, BigDecimal dailyTransferLimit, User user)
    {
        this.accountNumber = accountNumber;
        this.dailyTransferLimit = dailyTransferLimit;
        this.user = user;
    }
}
