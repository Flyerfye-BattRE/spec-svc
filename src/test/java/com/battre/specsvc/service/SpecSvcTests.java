package com.battre.specsvc.service;

import static org.mockito.Mockito.verify;

import com.battre.specsvc.repository.BatteryInfoRepository;
import com.battre.specsvc.repository.BatteryTiersRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SpecSvcTests {
    @Mock private BatteryInfoRepository batteryInfoRepo;
    @Mock private BatteryTiersRepository batteryTiersRepo;

    @InjectMocks
    private SpecSvc specSvc;

    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
        specSvc = new SpecSvc(batteryInfoRepo, batteryTiersRepo);
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void testGetRandomBatteryInfo() {
        specSvc.getRandomBatteries(1);
        verify(batteryInfoRepo).getRandomBatteries(1);
    }

    @Test
    public void testGetBatterySpecsByTypeId() {
        List<Integer> testBatteryTypeIds = List.of(3);
        specSvc.getBatterySpecsByTypeId(testBatteryTypeIds);
        verify(batteryInfoRepo).getBatterySpecsByTypeId(testBatteryTypeIds);
    }

    @Test
    public void testGetAllBatterySpecs() {
        specSvc.getAllBatterySpecs();
        verify(batteryInfoRepo).getAllBatterySpecs();
    }

    @Test
    public void testGetBatteryTiers() {
        specSvc.getBatteryTiers();
        verify(batteryTiersRepo).getBatteryTiers();
    }

    @Test
    public void testGetMinMaxBatterySpecs() {
        specSvc.getMinMaxBatterySpecs();
        verify(batteryInfoRepo).getMinMaxBatterySpecs();
    }

    @Test
    public void testGetTierCounts() {
        specSvc.getTierCounts();
        verify(batteryInfoRepo).getTierCounts();
    }

    @Test
    public void testCountBatterySpecs() {
        specSvc.countBatterySpecs();
        verify(batteryInfoRepo).countBatterySpecs();
    }

}
