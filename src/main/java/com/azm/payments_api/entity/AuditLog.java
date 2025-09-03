package com.azm.payments_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk_audit_log;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String action;

    @Size(max = 50)
    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "pk_entity")
    private Long pk_entity;

    @Column(name = "fk_user")
    private Long fk_user;

    @Size(max = 100)
    @Column(name = "userName")
    private String userName;

    @Size(max = 45)
    @Column(name = "ip_address")
    private String ip_address;

    @Size(max = 255)
    @Column(name = "user_agent")
    private String user_agent;

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditLevel level = AuditLevel.INFO;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public enum AuditLevel
    {
        INFO, WARNING, ERROR, CRITICAL
    }

    public AuditLog(String action, String entityType, Long pk_entity, Long fk_user, String userName, String ip_address, String details)
    {
        this.action = action;
        this.entityType = entityType;
        this.pk_entity = pk_entity;
        this.fk_user = fk_user;
        this.userName = userName;
        this.ip_address = ip_address;
        this.details = details;
    }

}
