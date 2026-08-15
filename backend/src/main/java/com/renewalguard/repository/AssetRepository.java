package com.renewalguard.repository;

import com.renewalguard.entity.Asset;
import com.renewalguard.entity.User;
import com.renewalguard.enums.AssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByOwner(User owner);
    List<Asset> findByOwnerAndStatus(User owner, AssetStatus status);
    List<Asset> findByStatus(AssetStatus status);
    List<Asset> findByExpiryDateBetween(LocalDate startDate, LocalDate endDate);
}