package com.battre.specsvc.repository;

import com.battre.specsvc.model.BatteryInfoType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BatteryInfoRepository extends JpaRepository<BatteryInfoType, Integer> {
  @Query("SELECT bit FROM BatteryInfoType bit ORDER BY RANDOM() LIMIT :numValues")
  List<BatteryInfoType> getRandomBatteries(@Param("numValues") int numValues);

  @Query("SELECT bit FROM BatteryInfoType bit WHERE batteryTypeId IN :ids ORDER BY batteryTypeId")
  List<BatteryInfoType> getBatterySpecsByTypeId(@Param("ids") List<Integer> ids);

  @Query("SELECT bit FROM BatteryInfoType bit ORDER BY batteryTypeId")
  List<BatteryInfoType> getAllBatterySpecs();

  @Query(
      "SELECT CAST(MIN(bit.minVoltage) AS DOUBLE), "
          + "CAST(MAX(bit.maxVoltage) AS DOUBLE), "
          + "CAST(MIN(bit.minCurrent) AS DOUBLE), "
          + "CAST(MAX(bit.maxCurrent) AS DOUBLE) "
          + "FROM BatteryInfoType bit")
  List<Object[]> getMinMaxBatterySpecs();

  @Query("SELECT count(*) FROM BatteryInfoType bit")
  Integer countBatterySpecs();

  @Query(
      "SELECT btt.tierLabel, COUNT(DISTINCT bit.batteryTypeId) AS count "
          + "FROM BatteryInfoType bit "
          + "JOIN BatteryTiersType btt ON bit.tierId = btt.tierId "
          + "GROUP BY btt.tierLabel")
  List<Object[]> getTierCounts();
}
