package com.renewalguard.repository;

import com.renewalguard.entity.EscalationStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscalationStepRepository extends JpaRepository<EscalationStep, Long> {
}