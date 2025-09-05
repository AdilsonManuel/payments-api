/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.azm.payments_api.repository;

import com.azm.payments_api.entity.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author AdilsonManuel
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long>
{

    Optional<Transaction> findByTransactionPk (String transactionId);

    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.id = :accountId OR t.toAccount.id = :accountId ORDER BY t.createdAt DESC")
    Page<Transaction> findByAccountPk (@Param("accountId") Long accountId, Pageable pageable);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.fromAccount.id = :accountId AND t.status = 'COMPLETED' AND t.createdAt >= :startDate AND t.createdAt <= :endDate")
    BigDecimal sumTransferredAmountByAccountIdAndDateRange (@Param("accountId") Long accountId,
                                                            @Param("startDate") LocalDateTime startDate,
                                                            @Param("endDate") LocalDateTime endDate);

    List<Transaction> findByFromAccountIdAndCreatedAtBetween (Long accountId, LocalDateTime startDate, LocalDateTime endDate);

    List<Transaction> findByToAccountIdAndCreatedAtBetween (Long accountId, LocalDateTime startDate, LocalDateTime endDate);

}
