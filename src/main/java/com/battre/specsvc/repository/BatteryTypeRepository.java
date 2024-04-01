package com.battre.specsvc.repository;

import com.battre.specsvc.model.BatteryType;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatteryTypeRepository extends CrudRepository<BatteryType, Integer> {
    @Query("SELECT * FROM SpecSvcDb.batterytypes ORDER BY RANDOM() LIMIT 1;")
    BatteryType getRandomBatteryType();
}
