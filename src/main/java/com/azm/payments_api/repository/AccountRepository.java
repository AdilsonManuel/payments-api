package com.azm.payments_api.repository;

import com.azm.payments_api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>
{
    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByUser(Long pk_user);

    List<Account> findActiveAccountByUserId(@Param("pk_user") Long pk_user);

    Boolean existsByAccountNumber(String accountNumber);
}
