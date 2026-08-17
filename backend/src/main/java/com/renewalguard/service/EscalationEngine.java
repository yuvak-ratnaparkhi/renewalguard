package com.renewalguard.service;

import com.renewalguard.entity.Asset;
import com.renewalguard.entity.EscalationStep;
import com.renewalguard.enums.AssetStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

@Service
public class EscalationEngine {

    public AssetStatus evaluateAssetStatus(Asset asset) {
        if (asset.getExpiryDate().isBefore(LocalDate.now())) {
            return AssetStatus.EXPIRED;
        }

        if (asset.getStatus() == AssetStatus.RENEWED) {
            return AssetStatus.RENEWED;
        }

        long daysUntilExpiry = ChronoUnit.DAYS.between(LocalDate.now(), asset.getExpiryDate());

        return asset.getPolicy().getSteps().stream()
                .filter(step -> daysUntilExpiry <= step.getDaysBeforeExpiry())
                .max(Comparator.comparingInt(EscalationStep::getDaysBeforeExpiry))
                .map(step -> step.getTargetStatus())
                .orElse(AssetStatus.ACTIVE);
    }

    public long getDaysUntilExpiry(Asset asset) {
        return ChronoUnit.DAYS.between(LocalDate.now(), asset.getExpiryDate());
    }
}