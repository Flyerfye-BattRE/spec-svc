package com.battre.specsvc.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.battre.specsvc.model.BatteryInfoType;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BatteryInfoRepositoryTests {
  private static final Logger logger = Logger.getLogger(BatteryInfoRepositoryTests.class.getName());

  @Autowired private BatteryInfoRepository batteryInfoRepository;

  @Test
  public void testGetRandomBatteries() {
    List<BatteryInfoType> randomBatteries = batteryInfoRepository.getRandomBatteries(3);

    // Verify the result
    assertNotNull(randomBatteries);
    assertEquals(3, randomBatteries.size());
    // All entries in the test database are expected to have the same test manufacturer
    for (BatteryInfoType battery : randomBatteries) {
      assertEquals("Test Manufacturer", battery.getMfc());
    }
  }

  @Test
  public void testGetBatterySpecsByTypeId() {
    List<Integer> batteryTypeIds = Arrays.asList(1, 2);

    List<BatteryInfoType> selectBatterySpecs =
        batteryInfoRepository.getBatterySpecsByTypeId(batteryTypeIds);

    // Verify the result
    assertNotNull(selectBatterySpecs);
    assertEquals(2, selectBatterySpecs.size());

    // Check properties of first battery returned
    assertEquals(1, selectBatterySpecs.get(0).getBatteryTypeId());
    assertEquals(5, selectBatterySpecs.get(0).getTierId());

    // Check properties of second battery returned
    assertEquals(2, selectBatterySpecs.get(1).getBatteryTypeId());
    assertEquals(7, selectBatterySpecs.get(1).getTierId());
  }

  @Test
  public void testGetAllBatterySpecs() {
    List<BatteryInfoType> allBatterySpecs = batteryInfoRepository.getAllBatterySpecs();

    // Verify the result
    assertNotNull(allBatterySpecs);
    assertEquals(3, allBatterySpecs.size());

    // Check properties of first battery returned
    assertEquals(1, allBatterySpecs.get(0).getBatteryTypeId());
    assertEquals(5, allBatterySpecs.get(0).getTierId());
    assertEquals(10, allBatterySpecs.get(0).getMaxVoltage());

    // Check properties of second battery returned
    assertEquals(2, allBatterySpecs.get(1).getBatteryTypeId());
    assertEquals(7, allBatterySpecs.get(1).getTierId());
    assertEquals(40, allBatterySpecs.get(1).getMaxCurrent());
  }

  @Test
  public void testGetMinMaxBatterySpecs() {
    List<Object[]> minMaxBatterySpecsList = batteryInfoRepository.getMinMaxBatterySpecs();

    // Verify the result
    assertNotNull(minMaxBatterySpecsList);
    assertEquals(1, minMaxBatterySpecsList.size());

    Object[] minMaxBatterySpecs = minMaxBatterySpecsList.get(0);
    assertEquals(0.0, (Double) minMaxBatterySpecs[0]);
    assertEquals(20.0, (Double) minMaxBatterySpecs[1]);
    assertEquals(0.0, (Double) minMaxBatterySpecs[2]);
    assertEquals(40.0, (Double) minMaxBatterySpecs[3]);
  }

  @Test
  public void testCountBatterySpecs() {
    Integer batterySpecsCount = batteryInfoRepository.countBatterySpecs();

    // Verify the result
    assertNotNull(batterySpecsCount);
    assertEquals(3, batterySpecsCount);
  }

  @Test
  public void testGetTierCounts() {
    List<Object[]> tierCountsList = batteryInfoRepository.getTierCounts();

    // Verify the result
    assertNotNull(tierCountsList);
    assertEquals(3, tierCountsList.size());

    Object[] tierCount = tierCountsList.get(0);
    assertEquals("CX", tierCount[0]);
    assertEquals(1L, tierCount[1]);
    assertEquals(0.0, tierCount[2]);
    assertEquals(5.0, tierCount[3]);
    assertEquals(0.0, tierCount[4]);
    assertEquals(20.0, tierCount[5]);
  }
}
