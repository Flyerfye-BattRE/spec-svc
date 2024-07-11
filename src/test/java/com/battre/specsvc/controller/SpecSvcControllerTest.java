package com.battre.specsvc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.battre.specsvc.model.BatteryInfoType;
import com.battre.specsvc.model.BatteryTiersType;
import com.battre.specsvc.repository.BatteryInfoRepository;
import com.battre.specsvc.repository.BatteryTiersRepository;
import com.battre.specsvc.service.SpecSvc;
import com.battre.stubs.services.GetAllBatterySpecsRequest;
import com.battre.stubs.services.GetAllBatterySpecsResponse;
import com.battre.stubs.services.GetBatteryTerminalLayoutsRequest;
import com.battre.stubs.services.GetBatteryTerminalLayoutsResponse;
import com.battre.stubs.services.GetBatteryTiersRequest;
import com.battre.stubs.services.GetBatteryTiersResponse;
import com.battre.stubs.services.GetRandomBatteryTypesRequest;
import com.battre.stubs.services.GetRandomBatteryTypesResponse;
import io.grpc.stub.StreamObserver;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SpecSvcControllerTest {
    @Mock
    private SpecSvc specSvc;
    @Mock
    private BatteryInfoRepository batteryInfoRepo;
    @Mock
    private BatteryTiersRepository batteryTiersRepo;

    @Mock
    private StreamObserver<GetRandomBatteryTypesResponse> responseGetRandomBatteryTypesResponse;

    @Mock
    private StreamObserver<GetBatteryTerminalLayoutsResponse> responseGetBatteryTerminalLayoutsResponse;
    @Mock
    private StreamObserver<GetAllBatterySpecsResponse> responseGetAllBatterySpecsResponse;
    @Mock
    private StreamObserver<GetBatteryTiersResponse> responseGetBatteryTiersResponse;

    private SpecSvcController specSvcController;

    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
        specSvcController = new SpecSvcController(specSvc);
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
        when(batteryInfoRepo.getRandomBatteries(1)).thenReturn(returnedBattery);

        GetRandomBatteryTypesRequest request = GetRandomBatteryTypesRequest.newBuilder().setNumBatteryTypes(1).build();

        specSvcController.getRandomBatteryTypes(request, responseGetRandomBatteryTypesResponse);
        verify(batteryInfoRepo).getRandomBatteries(1);
        // Capture the response
        ArgumentCaptor<GetRandomBatteryTypesResponse> responseCaptor = ArgumentCaptor.forClass(GetRandomBatteryTypesResponse.class);
        verify(responseGetRandomBatteryTypesResponse).onNext(responseCaptor.capture());
        verify(responseGetRandomBatteryTypesResponse).onCompleted();

        GetRandomBatteryTypesResponse response = responseCaptor.getValue();
        assertNotNull(response);
        assertEquals(returnedBattery.size(), response.getBatteriesCount());
        for (int i = 0; i < returnedBattery.size(); i++) {
            assertEquals(returnedBattery.get(i).getBatteryTypeId(), response.getBatteries(i).getBatteryTypeId());
            assertEquals(returnedBattery.get(i).getTierId(), response.getBatteries(i).getBatteryTierId());
        }
    }

    @Test
    void testGetBatteryTerminalLayouts() {
        List<BatteryInfoType> returnedBattery = List.of(
                new BatteryInfoType(3, 2, 1)
        );
        List<Integer> testBatteryTypeIds = List.of(3);
        when(batteryInfoRepo.getBatterySpecsByTypeId(testBatteryTypeIds)).thenReturn(returnedBattery);

        GetBatteryTerminalLayoutsRequest request =
                GetBatteryTerminalLayoutsRequest.newBuilder().addAllBatteryTypeIds(testBatteryTypeIds).build();

        specSvcController.getBatteryTerminalLayouts(request, responseGetBatteryTerminalLayoutsResponse);
        verify(batteryInfoRepo).getBatterySpecsByTypeId(testBatteryTypeIds);
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

    @Test
    void testGetAllBatterySpecs() {

        List<BatteryInfoType> returnedBatteries = List.of(
                populateBatteryInfoType(1, 2, 3, "testMfc", "Nickle stuff", "V dangerous", 1, 2, 3, 4),
                populateBatteryInfoType(2, 4, 6, "otherMfc", "Irony", "Safest", 2, 4, 6, 8)
        );

        when(batteryInfoRepo.getAllBatterySpecs()).thenReturn(returnedBatteries);

        GetAllBatterySpecsRequest request =
                GetAllBatterySpecsRequest.newBuilder().build();

        specSvcController.getAllBatterySpecs(request, responseGetAllBatterySpecsResponse);

        verify(batteryInfoRepo).getAllBatterySpecs();
        // Capture the response
        ArgumentCaptor<GetAllBatterySpecsResponse> responseCaptor = ArgumentCaptor.forClass(GetAllBatterySpecsResponse.class);
        verify(responseGetAllBatterySpecsResponse).onNext(responseCaptor.capture());
        verify(responseGetAllBatterySpecsResponse).onCompleted();

        GetAllBatterySpecsResponse response = responseCaptor.getValue();
        assertNotNull(response);
        assertEquals(returnedBatteries.size(), response.getBatterySpecsListCount());
        for (int i = 0; i < returnedBatteries.size(); i++) {
            assertEquals(returnedBatteries.get(i).getBatteryTypeId(), response.getBatterySpecsList(i).getBatteryTypeId());
            assertEquals(returnedBatteries.get(i).getTerminalLayoutId(), response.getBatterySpecsList(i).getTerminalLayoutId());
            assertEquals(returnedBatteries.get(i).getTierId(), response.getBatterySpecsList(i).getTierId());
        }
    }

    private BatteryInfoType populateBatteryInfoType(
            int typeId,
            int terminalLayoutId,
            int tierId,
            String mfc,
            String composition,
            String safetyInfo,
            double minVoltage,
            double maxVoltage,
            double minCurrent,
            double maxCurrent
    ) {
        BatteryInfoType returnedBattery = new BatteryInfoType(typeId, terminalLayoutId, tierId);
        returnedBattery.setMfc(mfc);
        returnedBattery.setComposition(composition);
        returnedBattery.setSafetyInfo(safetyInfo);
        returnedBattery.setMinVoltage(minVoltage);
        returnedBattery.setMaxVoltage(maxVoltage);
        returnedBattery.setMinCurrent(minCurrent);
        returnedBattery.setMaxCurrent(maxCurrent);

        return returnedBattery;
    }

    @Test
    void testGetBatteryTiers() {
        BatteryTiersType batteryTier = new BatteryTiersType();
        batteryTier.setBatteryTierId(1);
        batteryTier.setBatteryTierLabel("AX");

        List<BatteryTiersType> returnedBatteryTiers = List.of(
                batteryTier
        );
        when(batteryTiersRepo.getBatteryTiers()).thenReturn(returnedBatteryTiers);

        GetBatteryTiersRequest request =
                GetBatteryTiersRequest.newBuilder().build();

        specSvcController.getBatteryTiers(request, responseGetBatteryTiersResponse);

        verify(batteryTiersRepo).getBatteryTiers();
        // Capture the response
        ArgumentCaptor<GetBatteryTiersResponse> responseCaptor = ArgumentCaptor.forClass(GetBatteryTiersResponse.class);
        verify(responseGetBatteryTiersResponse).onNext(responseCaptor.capture());
        verify(responseGetBatteryTiersResponse).onCompleted();

        GetBatteryTiersResponse response = responseCaptor.getValue();
        assertNotNull(response);
        assertEquals(returnedBatteryTiers.size(), response.getBatteryTierListCount());
        for (int i = 0; i < returnedBatteryTiers.size(); i++) {
            assertEquals(returnedBatteryTiers.get(i).getBatteryTierId(), response.getBatteryTierList(i).getBatteryTierId());
            assertEquals(returnedBatteryTiers.get(i).getBatteryTierLabel(), response.getBatteryTierList(i).getBatteryTierLabel());
        }

    }
}
