package com.battre.specsvc.repository;

import com.battre.specsvc.model.BatteryType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatteryTypesRepository extends JpaRepository<BatteryType, Integer> {
    @Query("SELECT bt FROM BatteryType bt ORDER BY RANDOM() LIMIT :numValues")
    List<BatteryType> getRandomBatteries(@Param("numValues") int numValues);

    @Query("SELECT bt FROM BatteryType bt WHERE batteryTypeId IN :ids")
    List<BatteryType> getBatteriesByTypeId(@Param("ids") List<Integer> ids);
}
