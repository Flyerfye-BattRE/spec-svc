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
import com.battre.stubs.services.GetSpecSvcOverviewRequest;
import com.battre.stubs.services.GetSpecSvcOverviewResponse;
import com.battre.stubs.services.TierCount;
import io.grpc.stub.StreamObserver;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "grpc.server.port=9011")
public class SpecSvcControllerTests {
  @Mock private SpecSvc specSvc;
  @Mock private BatteryInfoRepository batteryInfoRepo;
  @Mock private BatteryTiersRepository batteryTiersRepo;

  @Mock private StreamObserver<GetRandomBatteryTypesResponse> responseGetRandomBatteryTypesResponse;

  @Mock
  private StreamObserver<GetBatteryTerminalLayoutsResponse>
      responseGetBatteryTerminalLayoutsResponse;

  @Mock private StreamObserver<GetAllBatterySpecsResponse> responseGetAllBatterySpecsResponse;
  @Mock private StreamObserver<GetBatteryTiersResponse> responseGetBatteryTiersResponse;
  @Mock private StreamObserver<GetSpecSvcOverviewResponse> responseGetSpecSvcOverviewResponse;

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
    List<BatteryInfoType> returnedBattery = List.of(new BatteryInfoType(1, 5, 2));
    when(specSvc.getRandomBatteries(1)).thenReturn(returnedBattery);

    GetRandomBatteryTypesRequest request =
        GetRandomBatteryTypesRequest.newBuilder().setNumBatteryTypes(1).build();

    specSvcController.getRandomBatteryTypes(request, responseGetRandomBatteryTypesResponse);
    verify(specSvc).getRandomBatteries(1);
    // Capture the response
    ArgumentCaptor<GetRandomBatteryTypesResponse> responseCaptor =
        ArgumentCaptor.forClass(GetRandomBatteryTypesResponse.class);
    verify(responseGetRandomBatteryTypesResponse).onNext(responseCaptor.capture());
    verify(responseGetRandomBatteryTypesResponse).onCompleted();

    GetRandomBatteryTypesResponse response = responseCaptor.getValue();
    assertNotNull(response);
    assertEquals(returnedBattery.size(), response.getBatteriesCount());
    for (int i = 0; i < returnedBattery.size(); i++) {
      assertEquals(
          returnedBattery.get(i).getBatteryTypeId(), response.getBatteries(i).getBatteryTypeId());
      assertEquals(returnedBattery.get(i).getTierId(), response.getBatteries(i).getBatteryTierId());
    }
  }

  @Test
  void testGetBatteryTerminalLayouts() {
    List<BatteryInfoType> returnedBattery = List.of(new BatteryInfoType(3, 2, 1));
    List<Integer> testBatteryTypeIds = List.of(3);
    when(specSvc.getBatterySpecsByTypeId(testBatteryTypeIds)).thenReturn(returnedBattery);

    GetBatteryTerminalLayoutsRequest request =
        GetBatteryTerminalLayoutsRequest.newBuilder()
            .addAllBatteryTypeIds(testBatteryTypeIds)
            .build();

    specSvcController.getBatteryTerminalLayouts(request, responseGetBatteryTerminalLayoutsResponse);
    verify(specSvc).getBatterySpecsByTypeId(testBatteryTypeIds);
    // Capture the response
    ArgumentCaptor<GetBatteryTerminalLayoutsResponse> responseCaptor =
        ArgumentCaptor.forClass(GetBatteryTerminalLayoutsResponse.class);
    verify(responseGetBatteryTerminalLayoutsResponse).onNext(responseCaptor.capture());
    verify(responseGetBatteryTerminalLayoutsResponse).onCompleted();

    GetBatteryTerminalLayoutsResponse response = responseCaptor.getValue();
    assertNotNull(response);
    assertEquals(returnedBattery.size(), response.getBatteriesCount());
    for (int i = 0; i < returnedBattery.size(); i++) {
      assertEquals(
          returnedBattery.get(i).getBatteryTypeId(), response.getBatteries(i).getBatteryTypeId());
      assertEquals(
          returnedBattery.get(i).getTerminalLayoutId(),
          response.getBatteries(i).getBatteryTerminalLayoutId());
    }
  }

  @Test
  void testGetAllBatterySpecs() {
    List<BatteryInfoType> returnedBatterySpecs =
        List.of(
                new BatteryInfoType(1, "Battery1", 1, "testMfc", 1, 2, 3, "Nickle stuff", "V dangerous", 1D, 2D, 3D, 4D),
                new BatteryInfoType(2, "Battery2", 2, "otherMfc", 2, 4, 6, "Irony", "Safest", 2D, 4D, 6D, 8D));
    List<BatteryTiersType> returnedBatteryTiers =
            List.of(new BatteryTiersType(3, "AX"), new BatteryTiersType(6, "L"));

    when(specSvc.getAllBatterySpecs()).thenReturn(returnedBatterySpecs);
    when(specSvc.getBatteryTiers()).thenReturn(returnedBatteryTiers);

    GetAllBatterySpecsRequest request = GetAllBatterySpecsRequest.newBuilder().build();

    specSvcController.getAllBatterySpecs(request, responseGetAllBatterySpecsResponse);

    verify(specSvc).getAllBatterySpecs();
    // Capture the response
    ArgumentCaptor<GetAllBatterySpecsResponse> responseCaptor =
        ArgumentCaptor.forClass(GetAllBatterySpecsResponse.class);
    verify(responseGetAllBatterySpecsResponse).onNext(responseCaptor.capture());
    verify(responseGetAllBatterySpecsResponse).onCompleted();

    GetAllBatterySpecsResponse response = responseCaptor.getValue();
    assertNotNull(response);
    assertEquals(returnedBatterySpecs.size(), response.getBatterySpecsListCount());
    for (int i = 0; i < returnedBatterySpecs.size(); i++) {
      assertEquals(
          returnedBatterySpecs.get(i).getBatteryTypeId(),
          response.getBatterySpecsList(i).getBatteryTypeId());
      assertEquals(
          returnedBatterySpecs.get(i).getTerminalLayoutId(),
          response.getBatterySpecsList(i).getTerminalLayoutId());
    }
  }

  @Test
  void testGetBatteryTiers() {
    BatteryTiersType batteryTier = new BatteryTiersType();
    batteryTier.setBatteryTierId(1);
    batteryTier.setBatteryTierLabel("AX");

    List<BatteryTiersType> returnedBatteryTiers = List.of(batteryTier);
    when(specSvc.getBatteryTiers()).thenReturn(returnedBatteryTiers);

    GetBatteryTiersRequest request = GetBatteryTiersRequest.newBuilder().build();

    specSvcController.getBatteryTiers(request, responseGetBatteryTiersResponse);

    verify(specSvc).getBatteryTiers();
    // Capture the response
    ArgumentCaptor<GetBatteryTiersResponse> responseCaptor =
        ArgumentCaptor.forClass(GetBatteryTiersResponse.class);
    verify(responseGetBatteryTiersResponse).onNext(responseCaptor.capture());
    verify(responseGetBatteryTiersResponse).onCompleted();

    GetBatteryTiersResponse response = responseCaptor.getValue();
    assertNotNull(response);
    assertEquals(returnedBatteryTiers.size(), response.getBatteryTierListCount());
    for (int i = 0; i < returnedBatteryTiers.size(); i++) {
      assertEquals(
          returnedBatteryTiers.get(i).getBatteryTierId(),
          response.getBatteryTierList(i).getBatteryTierId());
      assertEquals(
          returnedBatteryTiers.get(i).getBatteryTierLabel(),
          response.getBatteryTierList(i).getBatteryTierLabel());
    }
  }

  @Test
  void testGetSpecSvcOverview() {
    Integer numBatterySpecs = 1;
    Object[] minMaxBatterySpecs = {1.1D, 2.2D, 3.3D, 4.4D};
    TierCount returnTierCount =
        TierCount.newBuilder()
            .setTier("AX")
            .setCount(1)
            .setMinVoltage(1.1)
            .setMaxVoltage(2.2)
            .setMinCurrent(3.3)
            .setMaxCurrent(4.4)
            .build();
    List<TierCount> tierCountsList = List.of(returnTierCount);

    when(specSvc.countBatterySpecs()).thenReturn(numBatterySpecs);
    when(specSvc.getMinMaxBatterySpecs()).thenReturn(minMaxBatterySpecs);
    when(specSvc.getTierCounts()).thenReturn(tierCountsList);

    GetSpecSvcOverviewRequest request = GetSpecSvcOverviewRequest.newBuilder().build();

    specSvcController.getSpecSvcOverview(request, responseGetSpecSvcOverviewResponse);

    verify(specSvc).countBatterySpecs();
    verify(specSvc).getMinMaxBatterySpecs();
    verify(specSvc).getTierCounts();

    // Capture the response
    ArgumentCaptor<GetSpecSvcOverviewResponse> responseCaptor =
        ArgumentCaptor.forClass(GetSpecSvcOverviewResponse.class);
    verify(responseGetSpecSvcOverviewResponse).onNext(responseCaptor.capture());
    verify(responseGetSpecSvcOverviewResponse).onCompleted();

    GetSpecSvcOverviewResponse response = responseCaptor.getValue();
    assertNotNull(response);

    assertEquals(numBatterySpecs, response.getSpecsCount());

    assertEquals((Double) minMaxBatterySpecs[0], response.getMinVoltage());
    assertEquals((Double) minMaxBatterySpecs[1], response.getMaxVoltage());
    assertEquals((Double) minMaxBatterySpecs[2], response.getMinCurrent());
    assertEquals((Double) minMaxBatterySpecs[3], response.getMaxCurrent());

    assertEquals(1, response.getTierCountListList().size());
    TierCount responseTierCount = response.getTierCountListList().get(0);
    assertEquals(returnTierCount, responseTierCount);
  }
}
