package com.renewalguard.controller;

import com.renewalguard.dto.AssetRequest;
import com.renewalguard.dto.AssetResponse;
import com.renewalguard.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
@CrossOrigin(origins = "*")
public class AssetController {

    @Autowired
    private AssetService assetService;

    /**
     * Create a new asset (ADMIN or OWNER can create for themselves)
     */
    @PostMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<AssetResponse> createAsset(
            @Valid @RequestBody AssetRequest request,
            Authentication authentication) {

        String email = (String) authentication.getPrincipal();
        AssetResponse response = assetService.createAsset(request, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all assets for the current user (OWNER sees only own, ADMIN sees all)
     */
    @GetMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<List<AssetResponse>> getAssets(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority())
                .orElse("");

        List<AssetResponse> assets;

        if (role.contains("ADMIN")) {
            assets = assetService.getAllAssets();
        } else {
            assets = assetService.getAssetsByOwner(email);
        }

        return ResponseEntity.ok(assets);
    }

    /**
     * Get a specific asset (with permission check)
     */
    @GetMapping("/{assetId}")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<AssetResponse> getAsset(
            @PathVariable Long assetId,
            Authentication authentication) {

        AssetResponse asset = assetService.getAsset(assetId);
        String email = (String) authentication.getPrincipal();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority())
                .orElse("");

        // OWNER can only see their own assets
        if (role.contains("OWNER") && !asset.getOwnerEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(asset);
    }

    /**
     * Get assets expiring soon
     */
    @GetMapping("/expiring")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<List<AssetResponse>> getExpiringAssets(
            @RequestParam(defaultValue = "30") long days,
            Authentication authentication) {

        String email = (String) authentication.getPrincipal();
        List<AssetResponse> assets = assetService.getExpiringAssets(email, days);
        return ResponseEntity.ok(assets);
    }

    /**
     * Update an asset (OWNER can update their own, ADMIN can update any)
     */
    @PutMapping("/{assetId}")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<AssetResponse> updateAsset(
            @PathVariable Long assetId,
            @Valid @RequestBody AssetRequest request,
            Authentication authentication) {

        AssetResponse asset = assetService.getAsset(assetId);
        String email = (String) authentication.getPrincipal();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority())
                .orElse("");

        if (role.contains("OWNER") && !asset.getOwnerEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        AssetResponse updated = assetService.updateAsset(assetId, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Renew an asset
     */
    @PostMapping("/{assetId}/renew")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<AssetResponse> renewAsset(
            @PathVariable Long assetId,
            @RequestParam LocalDate newExpiryDate,
            Authentication authentication) {

        AssetResponse asset = assetService.getAsset(assetId);
        String email = (String) authentication.getPrincipal();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority())
                .orElse("");

        if (role.contains("OWNER") && !asset.getOwnerEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        AssetResponse renewed = assetService.renewAsset(assetId, newExpiryDate);
        return ResponseEntity.ok(renewed);
    }

    /**
     * Delete an asset (ADMIN only)
     */
    @DeleteMapping("/{assetId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long assetId) {
        assetService.deleteAsset(assetId);
        return ResponseEntity.noContent().build();
    }
}