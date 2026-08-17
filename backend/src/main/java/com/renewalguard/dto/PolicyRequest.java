package com.renewalguard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyRequest {

    @NotBlank(message = "Policy name is required")
    private String name;

    private String description;

    private List<PolicyStepRequest> steps;
}