package com.renewalguard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetResponse {

    private Long id;
    private String name;
    private String type;
    private String ownerEmail;
    private Long policyId;
    private String policyName;
    private LocalDate expiryDate;
    private String status;
    private Long createdAt;
}