package com.renewalguard.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyStepRequest {

    @NotNull(message = "Days before expiry is required")
    private Integer daysBeforeExpiry;

    @NotNull(message = "Target status is required")
    private String targetStatus; // NEARING_EXPIRY, ESCALATED, etc.

    @NotNull(message = "Step order is required")
    private Integer stepOrder;
}