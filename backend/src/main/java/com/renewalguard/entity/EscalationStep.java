package com.renewalguard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.renewalguard.enums.AssetStatus;

@Entity
@Table(name = "escalation_steps")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EscalationStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false)
    private EscalationPolicy policy;

    @Column(nullable = false)
    private Integer daysBeforeExpiry; // e.g., 30, 7, 1

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetStatus targetStatus; // What status to transition to

    @Column(nullable = false)
    private Integer stepOrder; // 1, 2, 3, etc.
}