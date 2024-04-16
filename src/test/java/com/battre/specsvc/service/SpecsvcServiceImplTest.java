package com.battre.specsvc.service;

import com.battre.specsvc.model.BatteryType;
import com.battre.specsvc.repository.BatteryTypesRepository;
import com.battre.stubs.services.GetRandomBatteryTypesRequest;
import com.battre.stubs.services.GetRandomBatteryTypesResponse;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SpecsvcServiceImplTest {

    @Mock
    private BatteryTypesRepository batteryTypesRepository;

    @Mock
    private StreamObserver<GetRandomBatteryTypesResponse> responseObserver;

    private SpecsvcServiceImpl specsvcServiceImpl;

    @Test
    void testGetRandomBatteryType() {
        MockitoAnnotations.openMocks(this);

        BatteryType testBattery = new BatteryType();
        testBattery.setBatteryTypeId(5);
        List<BatteryType> testBatteries = List.of(testBattery);
        when(batteryTypesRepository.getRandomBatteries(1)).thenReturn(testBatteries);

        specsvcServiceImpl = new SpecsvcServiceImpl(batteryTypesRepository);

        GetRandomBatteryTypesRequest request = GetRandomBatteryTypesRequest.newBuilder().setNumBatteryTypes(1).build();

        specsvcServiceImpl.getRandomBatteryTypes(request, responseObserver);
        verify(batteryTypesRepository).getRandomBatteries(1);
    }
}

