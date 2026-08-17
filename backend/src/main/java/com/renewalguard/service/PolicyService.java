package com.renewalguard.service;

import com.renewalguard.dto.PolicyRequest;
import com.renewalguard.dto.PolicyResponse;
import com.renewalguard.dto.PolicyStepResponse;
import com.renewalguard.entity.EscalationPolicy;
import com.renewalguard.entity.EscalationStep;
import com.renewalguard.enums.AssetStatus;
import com.renewalguard.repository.EscalationPolicyRepository;
import com.renewalguard.repository.EscalationStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyService {

    @Autowired
    private EscalationPolicyRepository policyRepository;

    @Autowired
    private EscalationStepRepository escalationStepRepository;

    public PolicyResponse createPolicy(PolicyRequest request) {
        EscalationPolicy policy = new EscalationPolicy();
        policy.setName(request.getName());
        policy.setDescription(request.getDescription());

        EscalationPolicy savedPolicy = policyRepository.save(policy);

        List<EscalationStep> steps = request.getSteps().stream()
                .map(stepReq -> {
                    EscalationStep step = new EscalationStep();
                    step.setPolicy(savedPolicy);
                    step.setDaysBeforeExpiry(stepReq.getDaysBeforeExpiry());
                    step.setTargetStatus(AssetStatus.valueOf(stepReq.getTargetStatus()));
                    step.setStepOrder(stepReq.getStepOrder());
                    return escalationStepRepository.save(step);
                })
                .collect(Collectors.toList());

        savedPolicy.setSteps(steps);
        policyRepository.save(savedPolicy);

        return mapToResponse(savedPolicy);
    }

    public PolicyResponse getPolicy(Long policyId) {
        EscalationPolicy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found"));
        return mapToResponse(policy);
    }

    public List<PolicyResponse> getAllPolicies() {
        List<EscalationPolicy> policies = policyRepository.findAll();
        return policies.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public PolicyResponse updatePolicy(Long policyId, PolicyRequest request) {
        EscalationPolicy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        policy.setName(request.getName());
        policy.setDescription(request.getDescription());
        policy.getSteps().clear();
        policyRepository.save(policy);

        List<EscalationStep> steps = request.getSteps().stream()
                .map(stepReq -> {
                    EscalationStep step = new EscalationStep();
                    step.setPolicy(policy);
                    step.setDaysBeforeExpiry(stepReq.getDaysBeforeExpiry());
                    step.setTargetStatus(AssetStatus.valueOf(stepReq.getTargetStatus()));
                    step.setStepOrder(stepReq.getStepOrder());
                    return escalationStepRepository.save(step);
                })
                .collect(Collectors.toList());

        policy.setSteps(steps);
        EscalationPolicy updated = policyRepository.save(policy);
        return mapToResponse(updated);
    }

    public void deletePolicy(Long policyId) {
        policyRepository.deleteById(policyId);
    }

    private PolicyResponse mapToResponse(EscalationPolicy policy) {
        List<PolicyStepResponse> stepResponses = policy.getSteps().stream()
                .map(step -> new PolicyStepResponse(
                        step.getId(),
                        step.getDaysBeforeExpiry(),
                        step.getTargetStatus(),
                        step.getStepOrder()
                ))
                .collect(Collectors.toList());

        return new PolicyResponse(
                policy.getId(),
                policy.getName(),
                policy.getDescription(),
                stepResponses
        );
    }
}