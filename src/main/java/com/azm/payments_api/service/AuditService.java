package com.azm.payments_api.service;

import com.azm.payments_api.entity.AuditLog;
import com.azm.payments_api.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class AuditService
{
    @Autowired
    private AuditRepository auditRepository;

    @Value("${app.audit.enable:true}")
    private boolean auditEnabled;

    public void logAction(String action, String entityType, Long entityPk, Long fk_user, String userName, String ipAddress, String details)
    {
        if (!auditEnabled) {
            return;

        }

        AuditLog auditLog = new AuditLog(action, entityType, entityPk, fk_user, userName, ipAddress, details);
        auditRepository.save(auditLog);
    }

    public void logAction(String action, String entityType, Long entityPk, Long fk_user, String userName, String ipAddress, String details, AuditLog.AuditLevel level)
    {
        AuditLog auditLog = new AuditLog(action, entityType, entityPk, fk_user, userName, ipAddress, details);
        auditLog.setLevel(level);
        auditRepository.save(auditLog);

    }

    public Page<AuditLog> getAuditLogByUserPk(Long fk_user, Pageable pageable)
    {
        return auditRepository.findByUserPkOrderByCreatedAtDesc(fk_user, pageable);
    }

    public Page<AuditLog> getAudtiLogsByAction(String action, Pageable pageable)
    {
        return auditRepository.findByActionOrderByCreatedAtDesc(action, pageable);
    }

    public Page<AuditLog> getAuditLogsByDateRang(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable)
    {
        return auditRepository.findByDateRangeOrderByCreatedAtDesc(startDate, endDate, pageable);
    }
}
