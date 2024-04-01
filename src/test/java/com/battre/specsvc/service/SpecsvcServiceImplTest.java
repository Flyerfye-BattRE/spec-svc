package com.battre.specsvc.service;

import com.battre.specsvc.model.BatteryType;
import com.battre.specsvc.repository.BatteryTypeRepository;
import com.battre.stubs.services.SpecSvcEmptyRequest;
import com.battre.stubs.services.SpecSvcResponse;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SpecsvcServiceImplTest {

    @Mock
    private BatteryTypeRepository batteryTypeRepository;

    @Mock
    private StreamObserver<SpecSvcResponse> responseObserver;

    private SpecsvcServiceImpl specsvcServiceImpl;

    @Test
    void testGetRandomBatteryType() {
        MockitoAnnotations.openMocks(this);

        BatteryType testBattery = new BatteryType();
        testBattery.setBatteryTypeId(5);
        when(batteryTypeRepository.getRandomBatteryType()).thenReturn(testBattery);

        specsvcServiceImpl = new SpecsvcServiceImpl(batteryTypeRepository);

        SpecSvcEmptyRequest request = SpecSvcEmptyRequest.newBuilder().build();

        specsvcServiceImpl.getRandomBatteryType(request, responseObserver);
        verify(batteryTypeRepository).getRandomBatteryType();
    }
}

