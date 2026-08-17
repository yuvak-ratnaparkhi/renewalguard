package com.renewalguard.dto;

import com.renewalguard.enums.AssetStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyStepResponse {

    private Long id;
    private Integer daysBeforeExpiry;
    private AssetStatus targetStatus;
    private Integer stepOrder;
}