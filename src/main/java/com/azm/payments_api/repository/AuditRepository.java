package com.azm.payments_api.repository;

import com.azm.payments_api.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<AuditLog, Long>
{
    Page<AuditLog> findByUserPkOrderByCreatedAtDesc(Long pk_user, Pageable pageable);

    Page<AuditLog> findByActionOrderByCreatedAtDesc(String action, Pageable pageable);

    @Query("SELECT a FROM AuditLog a WHERE a.createdAt >= : startDate AND a.createdAt <= : endDate ORDER BY a.createdAt DESC")
    Page<AuditLog> findByDateRangeOrderByCreatedAtDesc(@Param("startDete") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

    List<AuditLog> findByEntityTypeAndEntityPkOrderByCreatedAtDesc(String entityType, Long entityPk);

}
