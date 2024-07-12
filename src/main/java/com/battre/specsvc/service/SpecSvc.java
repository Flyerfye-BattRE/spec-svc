package com.battre.specsvc.service;

import com.battre.specsvc.model.BatteryInfoType;
import com.battre.specsvc.model.BatteryTiersType;
import com.battre.specsvc.repository.BatteryInfoRepository;
import com.battre.specsvc.repository.BatteryTiersRepository;
import com.battre.stubs.services.TierCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class SpecSvc {
  private static final Logger logger = Logger.getLogger(SpecSvc.class.getName());
  private final BatteryInfoRepository batteryInfoRepo;
  private final BatteryTiersRepository batteryTiersRepo;

  @Autowired
  public SpecSvc(BatteryInfoRepository batteryInfoRepo, BatteryTiersRepository batteryTiersRepo) {
    this.batteryInfoRepo = batteryInfoRepo;
    this.batteryTiersRepo = batteryTiersRepo;
  }

  public List<BatteryInfoType> getRandomBatteries(int numBatteryTypes) {
    return batteryInfoRepo.getRandomBatteries(numBatteryTypes);
  }

  public List<BatteryInfoType> getBatterySpecsByTypeId(List<Integer> batteryTypeIdsList) {
    return batteryInfoRepo.getBatterySpecsByTypeId(batteryTypeIdsList);
  }

  public List<BatteryInfoType> getAllBatterySpecs() {
    return batteryInfoRepo.getAllBatterySpecs();
  }

  public List<BatteryTiersType> getBatteryTiers() {
    return batteryTiersRepo.getBatteryTiers();
  }

  public Object[] getMinMaxBatterySpecs() {
    List<Object[]> minMaxBatterySpecs = batteryInfoRepo.getMinMaxBatterySpecs();

    if(minMaxBatterySpecs.size() == 1) {
      return minMaxBatterySpecs.get(0);
    } else {
      return null;
    }
  }

  public List<TierCount> getTierCounts() {
    List<Object[]> tierCountsList = batteryInfoRepo.getTierCounts();

    return tierCountsList.stream()
        .map(
            tierCount ->
                TierCount.newBuilder()
                    .setTier((String) tierCount[0])
                    .setCount(((Long) tierCount[1]).intValue())
                    .setMinVoltage((Double) tierCount[2])
                    .setMaxVoltage((Double) tierCount[3])
                    .setMinCurrent((Double) tierCount[4])
                    .setMaxCurrent((Double) tierCount[5])
                    .build())
        .collect(Collectors.toList());
  }

  public Integer countBatterySpecs() {
    return batteryInfoRepo.countBatterySpecs();
  }
}
