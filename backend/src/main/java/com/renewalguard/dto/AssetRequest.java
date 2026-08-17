package com.renewalguard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetRequest {

    @NotBlank(message = "Asset name is required")
    private String name;

    @NotBlank(message = "Asset type is required")
    private String type; // SSL_CERT, LICENSE, CONTRACT, DOMAIN, INSURANCE, OTHER

    @NotNull(message = "Policy ID is required")
    private Long policyId;

    @NotNull(message = "Expiry date is required")
    private LocalDate expiryDate;
}