package com.renewalguard.controller;

import com.renewalguard.dto.PolicyRequest;
import com.renewalguard.dto.PolicyResponse;
import com.renewalguard.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/policies")
@CrossOrigin(origins = "*")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    /**
     * Create a new escalation policy (ADMIN only)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PolicyResponse> createPolicy(@Valid @RequestBody PolicyRequest request) {
        PolicyResponse response = policyService.createPolicy(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all policies
     */
    @GetMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<List<PolicyResponse>> getAllPolicies() {
        List<PolicyResponse> policies = policyService.getAllPolicies();
        return ResponseEntity.ok(policies);
    }

    /**
     * Get a specific policy
     */
    @GetMapping("/{policyId}")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<PolicyResponse> getPolicy(@PathVariable Long policyId) {
        PolicyResponse policy = policyService.getPolicy(policyId);
        return ResponseEntity.ok(policy);
    }

    /**
     * Update a policy (ADMIN only)
     */
    @PutMapping("/{policyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PolicyResponse> updatePolicy(
            @PathVariable Long policyId,
            @Valid @RequestBody PolicyRequest request) {

        PolicyResponse updated = policyService.updatePolicy(policyId, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a policy (ADMIN only)
     */
    @DeleteMapping("/{policyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long policyId) {
        policyService.deletePolicy(policyId);
        return ResponseEntity.noContent().build();
    }
}