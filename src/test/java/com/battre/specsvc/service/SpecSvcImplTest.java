package com.battre.specsvc.service;

import com.battre.specsvc.model.BatteryInfoType;
import com.battre.specsvc.repository.BatteryInfoRepository;
import com.battre.stubs.services.GetBatteryTerminalLayoutsRequest;
import com.battre.stubs.services.GetBatteryTerminalLayoutsResponse;
import com.battre.stubs.services.GetRandomBatteryTypesRequest;
import com.battre.stubs.services.GetRandomBatteryTypesResponse;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SpecSvcImplTest {
    @Mock
    private BatteryInfoRepository batTypRepo;

    @Mock
    private StreamObserver<GetRandomBatteryTypesResponse> responseGetRandomBatteryTypesResponse;

    @Mock
    private StreamObserver<GetBatteryTerminalLayoutsResponse> responseGetBatteryTerminalLayoutsResponse;

    private SpecSvcImpl specSvcImpl;

    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
        specSvcImpl = new SpecSvcImpl(batTypRepo);
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    void testGetRandomBatteryTypes() {
        List<BatteryInfoType> returnedBattery = List.of(
                new BatteryInfoType(1, 5, 2)
        );
        when(batTypRepo.getRandomBatteries(1)).thenReturn(returnedBattery);

        GetRandomBatteryTypesRequest request = GetRandomBatteryTypesRequest.newBuilder().setNumBatteryTypes(1).build();

        specSvcImpl.getRandomBatteryTypes(request, responseGetRandomBatteryTypesResponse);
        verify(batTypRepo).getRandomBatteries(1);
        // Capture the response
        ArgumentCaptor<GetRandomBatteryTypesResponse> responseCaptor = ArgumentCaptor.forClass(GetRandomBatteryTypesResponse.class);
        verify(responseGetRandomBatteryTypesResponse).onNext(responseCaptor.capture());
        verify(responseGetRandomBatteryTypesResponse).onCompleted();

        GetRandomBatteryTypesResponse response = responseCaptor.getValue();
        assertNotNull(response);
        assertEquals(returnedBattery.size(), response.getBatteriesCount());
        for (int i = 0; i < returnedBattery.size(); i++) {
            assertEquals(returnedBattery.get(i).getBatteryTypeId(), response.getBatteries(i).getBatteryTypeId());
            assertEquals(returnedBattery.get(i).getBatteryTierId(), response.getBatteries(i).getBatteryTierId());
        }
    }

    @Test
    void testGetBatteryTerminalLayouts() {
        List<BatteryInfoType> returnedBattery = List.of(
                new BatteryInfoType(3, 2, 1)
        );
        List<Integer> testBatteryTypeIds = List.of(3);
        when(batTypRepo.getBatteriesByTypeId(testBatteryTypeIds)).thenReturn(returnedBattery);

        GetBatteryTerminalLayoutsRequest request =
                GetBatteryTerminalLayoutsRequest.newBuilder().addAllBatteryTypeIds(testBatteryTypeIds).build();

        specSvcImpl.getBatteryTerminalLayouts(request, responseGetBatteryTerminalLayoutsResponse);
        verify(batTypRepo).getBatteriesByTypeId(testBatteryTypeIds);
        // Capture the response
        ArgumentCaptor<GetBatteryTerminalLayoutsResponse> responseCaptor = ArgumentCaptor.forClass(GetBatteryTerminalLayoutsResponse.class);
        verify(responseGetBatteryTerminalLayoutsResponse).onNext(responseCaptor.capture());
        verify(responseGetBatteryTerminalLayoutsResponse).onCompleted();

        GetBatteryTerminalLayoutsResponse response = responseCaptor.getValue();
        assertNotNull(response);
        assertEquals(returnedBattery.size(), response.getBatteriesCount());
        for (int i = 0; i < returnedBattery.size(); i++) {
            assertEquals(returnedBattery.get(i).getBatteryTypeId(), response.getBatteries(i).getBatteryTypeId());
            assertEquals(returnedBattery.get(i).getTerminalLayoutId(), response.getBatteries(i).getBatteryTerminalLayoutId());
        }
    }
}

