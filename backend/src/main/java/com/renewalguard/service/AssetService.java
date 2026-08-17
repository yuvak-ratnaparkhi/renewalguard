package com.renewalguard.service;

import com.renewalguard.dto.AssetRequest;
import com.renewalguard.dto.AssetResponse;
import com.renewalguard.entity.Asset;
import com.renewalguard.entity.EscalationPolicy;
import com.renewalguard.entity.User;
import com.renewalguard.enums.AssetStatus;
import com.renewalguard.repository.AssetRepository;
import com.renewalguard.repository.EscalationPolicyRepository;
import com.renewalguard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private EscalationPolicyRepository policyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EscalationEngine escalationEngine;

    /**
     * Create a new asset
     */
    public AssetResponse createAsset(AssetRequest request, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        EscalationPolicy policy = policyRepository.findById(request.getPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        Asset asset = new Asset();
        asset.setName(request.getName());
        asset.setType(request.getType());
        asset.setOwner(owner);
        asset.setPolicy(policy);
        asset.setExpiryDate(request.getExpiryDate());
        asset.setStatus(AssetStatus.ACTIVE);

        Asset saved = assetRepository.save(asset);
        return mapToResponse(saved);
    }

    /**
     * Get asset by ID (with ownership check done by controller)
     */
    public AssetResponse getAsset(Long assetId) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));
        return mapToResponse(asset);
    }

    /**
     * Update asset
     */
    public AssetResponse updateAsset(Long assetId, AssetRequest request) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        EscalationPolicy policy = policyRepository.findById(request.getPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        asset.setName(request.getName());
        asset.setType(request.getType());
        asset.setPolicy(policy);
        asset.setExpiryDate(request.getExpiryDate());
        asset.setUpdatedAt(System.currentTimeMillis());

        Asset updated = assetRepository.save(asset);
        return mapToResponse(updated);
    }

    /**
     * Delete asset
     */
    public void deleteAsset(Long assetId) {
        assetRepository.deleteById(assetId);
    }

    /**
     * Get all assets for an owner
     */
    public List<AssetResponse> getAssetsByOwner(String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Asset> assets = assetRepository.findByOwner(owner);
        return assets.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /**
     * Get all assets (for ADMIN only)
     */
    public List<AssetResponse> getAllAssets() {
        List<Asset> assets = assetRepository.findAll();
        return assets.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /**
     * Get assets expiring within N days (for any user)
     */
    public List<AssetResponse> getExpiringAssets(String ownerEmail, long days) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate today = LocalDate.now();
        LocalDate expiryBoundary = today.plusDays(days);

        List<Asset> assets = assetRepository.findByExpiryDateBetween(today, expiryBoundary);

        // Filter by owner if not ADMIN
        return assets.stream()
                .filter(asset -> asset.getOwner().getId().equals(owner.getId()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Renew an asset (reset to ACTIVE)
     */
    public AssetResponse renewAsset(Long assetId, LocalDate newExpiryDate) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        asset.setExpiryDate(newExpiryDate);
        asset.setStatus(AssetStatus.ACTIVE);
        asset.setUpdatedAt(System.currentTimeMillis());

        Asset updated = assetRepository.save(asset);
        return mapToResponse(updated);
    }

    /**
     * Convert Asset entity to AssetResponse DTO
     */
    private AssetResponse mapToResponse(Asset asset) {
        return new AssetResponse(
                asset.getId(),
                asset.getName(),
                asset.getType(),
                asset.getOwner().getEmail(),
                asset.getPolicy().getId(),
                asset.getPolicy().getName(),
                asset.getExpiryDate(),
                asset.getStatus().name(),
                asset.getCreatedAt()
        );
    }
}