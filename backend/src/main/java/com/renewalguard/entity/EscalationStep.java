package com.renewalguard.entity;

import com.renewalguard.enums.AssetStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer daysBeforeExpiry;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetStatus targetStatus;

    @Column(nullable = false)
    private Integer stepOrder;
}