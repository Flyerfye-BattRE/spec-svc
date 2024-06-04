package com.battre.specsvc.repository;

import com.battre.specsvc.model.BatteryTiersType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BatteryTiersRepositoryTest {
    private static final Logger logger = Logger.getLogger(BatteryTiersRepositoryTest.class.getName());

    @Autowired
    private BatteryTiersRepository batteryTiersRepository;

    @Test
    public void testGetBatteryTiers() {
        // Call the method under test
        List<BatteryTiersType> batteryTiers = batteryTiersRepository.getBatteryTiers();

        // Verify the result
        assertNotNull(batteryTiers);
        assertEquals(8, batteryTiers.size());


        // Check properties of first battery tier returned
        assertEquals(1, batteryTiers.get(0).getBatteryTierId());
        assertEquals("AX", batteryTiers.get(0).getBatteryTierLabel());

        // Check properties of second battery tier returned
        assertEquals(2, batteryTiers.get(1).getBatteryTierId());
        assertEquals("CX", batteryTiers.get(1).getBatteryTierLabel());
    }
}
