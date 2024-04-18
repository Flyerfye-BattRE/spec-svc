package com.battre.specsvc.repository;

import com.battre.specsvc.model.BatteryType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BatteryTypesRepositoryTest {
    private static final Logger logger = Logger.getLogger(BatteryTypesRepositoryTest.class.getName());

    @Autowired
    private BatteryTypesRepository batteryTypesRepository;

    @Test
    public void testGetRandomBatteries() {
        // Call the method under test
        List<BatteryType> randomBatteries = batteryTypesRepository.getRandomBatteries(3);

        // Verify the result
        assertNotNull(randomBatteries);
        assertEquals(3, randomBatteries.size());
        // All entries in the test database are expected to have the same test manufacturer
        for (BatteryType battery : randomBatteries) {
            assertEquals("Test Manufacturer", battery.getMfc());
        }
    }

    @Test
    public void testGetBatteriesByTypeId() {
        List<Integer> batteryTypeIds = Arrays.asList(1, 2);

        // Call the method under test
        List<BatteryType> selectBatteries = batteryTypesRepository.getBatteriesByTypeId(batteryTypeIds);

        // Verify the result
        assertNotNull(selectBatteries);
        assertEquals(2, selectBatteries.size());

        // Check properties of first battery returned
        assertEquals(1, selectBatteries.get(0).getBatteryTypeId());
        assertEquals(5, selectBatteries.get(0).getBatteryTierId());

        // Check properties of second battery returned
        assertEquals(2, selectBatteries.get(1).getBatteryTypeId());
        assertEquals(7, selectBatteries.get(1).getBatteryTierId());
    }
}
