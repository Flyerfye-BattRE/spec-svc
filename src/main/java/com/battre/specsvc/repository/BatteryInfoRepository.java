package com.battre.specsvc.repository;

import com.battre.specsvc.model.BatteryInfoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatteryInfoRepository extends JpaRepository<BatteryInfoType, Integer> {
    @Query("SELECT bit FROM BatteryInfoType bit ORDER BY RANDOM() LIMIT :numValues")
    List<BatteryInfoType> getRandomBatteries(@Param("numValues") int numValues);

    @Query("SELECT bit FROM BatteryInfoType bit WHERE batteryTypeId IN :ids ORDER BY batteryTypeId")
    List<BatteryInfoType> getBatterySpecsByTypeId(@Param("ids") List<Integer> ids);

    @Query("SELECT bit FROM BatteryInfoType bit ORDER BY batteryTypeId")
    List<BatteryInfoType> getAllBatterySpecs();
}
