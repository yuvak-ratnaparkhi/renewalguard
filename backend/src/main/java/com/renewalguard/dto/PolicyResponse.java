package com.renewalguard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyResponse {

    private Long id;
    private String name;
    private String description;
    private List<PolicyStepResponse> steps;
}