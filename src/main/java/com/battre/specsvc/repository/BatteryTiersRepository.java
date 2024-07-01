package com.battre.specsvc.repository;

import com.battre.specsvc.model.BatteryTiersType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatteryTiersRepository extends JpaRepository<BatteryTiersType, Integer> {
  @Query("SELECT btt FROM BatteryTiersType btt ORDER BY tierId")
  List<BatteryTiersType> getBatteryTiers();
}
