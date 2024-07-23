package com.battre.specsvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.battre.grpcifc.GrpcTestMethodInvoker;
import com.battre.specsvc.controller.SpecSvcController;
import com.battre.specsvc.model.BatteryInfoType;
import com.battre.specsvc.model.BatteryTiersType;
import com.battre.specsvc.repository.BatteryInfoRepository;
import com.battre.specsvc.repository.BatteryTiersRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(properties = "grpc.server.port=9013")
@ExtendWith(MockitoExtension.class)
public class SpecSvcIntegrationTests {
  private static final Logger logger = Logger.getLogger(SpecSvcIntegrationTests.class.getName());

  @MockBean private BatteryInfoRepository batteryInfoRepo;
  @MockBean private BatteryTiersRepository batteryTiersRepo;
  @Autowired private SpecSvcController specSvcController;
  private final GrpcTestMethodInvoker grpcTestMethodInvoker = new GrpcTestMethodInvoker();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetRandomBatteryTypes_Success() throws NoSuchMethodException {
    // Test
    List<BatteryInfoType> returnedBattery = List.of(new BatteryInfoType(1, 2, 3));
    when(batteryInfoRepo.getRandomBatteries(1)).thenReturn(returnedBattery);

    // Request
    GetRandomBatteryTypesRequest request =
        GetRandomBatteryTypesRequest.newBuilder().setNumBatteryTypes(1).build();
    GetRandomBatteryTypesResponse response =
        grpcTestMethodInvoker.invokeNonblock(specSvcController, "getRandomBatteryTypes", request);
    assertEquals(response.getBatteriesCount(), 1);
    assertEquals(response.getBatteries(0).getBatteryTypeId(), 1);
    assertEquals(response.getBatteries(0).getBatteryTierId(), 3);

    // Verify
    verify(batteryInfoRepo).getRandomBatteries(eq(1));
  }

  @Test
  public void testGetBatteryTerminalLayouts_Success() throws NoSuchMethodException {
    // Test
    List<BatteryInfoType> returnedBattery = List.of(new BatteryInfoType(4, 5, 6));
    List<Integer> testBatteryTypeIds = List.of(3);
    when(batteryInfoRepo.getBatterySpecsByTypeId(testBatteryTypeIds)).thenReturn(returnedBattery);

    // Request
    GetBatteryTerminalLayoutsRequest request =
        GetBatteryTerminalLayoutsRequest.newBuilder()
            .addAllBatteryTypeIds(testBatteryTypeIds)
            .build();
    GetBatteryTerminalLayoutsResponse response =
        grpcTestMethodInvoker.invokeNonblock(
            specSvcController, "getBatteryTerminalLayouts", request);
    assertEquals(response.getBatteriesCount(), 1);
    assertEquals(response.getBatteries(0).getBatteryTypeId(), 4);
    assertEquals(response.getBatteries(0).getBatteryTerminalLayoutId(), 5);

    // Verify
    verify(batteryInfoRepo).getBatterySpecsByTypeId(eq(testBatteryTypeIds));
  }

  @Test
  public void testGetAllBatterySpecs_Success() throws NoSuchMethodException {
    // Test
    List<BatteryInfoType> returnedBatterySpecs =
        List.of(
            new BatteryInfoType(
                1,
                "Battery1",
                1,
                "testMfc",
                1,
                2,
                3,
                "Nickle stuff",
                "V dangerous",
                1D,
                2D,
                3D,
                4D),
            new BatteryInfoType(
                2,
                "Battery2",
                2,
                "otherMfc",
                2,
                4,
                6,
                "Irony",
                "Safest",
                2D,
                4D,
                6D,
                8D));

    List<BatteryTiersType> returnedBatteryTiers =
        List.of(new BatteryTiersType(3, "AX"), new BatteryTiersType(6, "L"));
    when(batteryInfoRepo.getAllBatterySpecs()).thenReturn(returnedBatterySpecs);
    when(batteryTiersRepo.getBatteryTiers()).thenReturn(returnedBatteryTiers);

    // Request
    GetAllBatterySpecsRequest request = GetAllBatterySpecsRequest.newBuilder().build();
    GetAllBatterySpecsResponse response =
        grpcTestMethodInvoker.invokeNonblock(specSvcController, "getAllBatterySpecs", request);
    assertEquals(response.getBatterySpecsListCount(), 2);
    assertEquals(response.getBatterySpecsList(0).getBatteryName(), "Battery1");
    assertEquals(response.getBatterySpecsList(0).getTerminalLayoutId(), 2);
    assertEquals(response.getBatterySpecsList(0).getOptionalMaxVoltage().getValue(), 2.0D);

    // Verify
    verify(batteryInfoRepo).getAllBatterySpecs();
    verify(batteryTiersRepo).getBatteryTiers();
  }

  @Test
  public void testGetBatteryTiers_Success() throws NoSuchMethodException {
    // Test
    BatteryTiersType batteryTier = new BatteryTiersType();
    batteryTier.setBatteryTierId(1);
    batteryTier.setBatteryTierLabel("AX");
    List<BatteryTiersType> returnedBatteryTiers = List.of(batteryTier);
    when(batteryTiersRepo.getBatteryTiers()).thenReturn(returnedBatteryTiers);

    // Request
    GetBatteryTiersRequest request = GetBatteryTiersRequest.newBuilder().build();
    GetBatteryTiersResponse response =
        grpcTestMethodInvoker.invokeNonblock(specSvcController, "getBatteryTiers", request);
    assertEquals(response.getBatteryTierListCount(), 1);
    assertEquals(response.getBatteryTierList(0).getBatteryTierId(), 1);
    assertEquals(response.getBatteryTierList(0).getBatteryTierLabel(), "AX");

    // Verify
    verify(batteryTiersRepo).getBatteryTiers();
  }

  @Test
  public void testGetSpecSvcOverview_Success() throws NoSuchMethodException {
    // Test
    Integer numBatterySpecs = 1;
    List<Object[]> minMaxBatterySpecs = new ArrayList<>();
    minMaxBatterySpecs.add(new Object[] {1.1D, 2.2D, 3.3D, 4.4D});
    List<Object[]> tierCountsList =
        List.of(
            new Object[] {"AX", 1L, 1.1D, 2.2D, 3.3D, 4.4D},
            new Object[] {"BY", 2L, 2.2D, 4.4D, 6.6D, 8.8D});
    when(batteryInfoRepo.countBatterySpecs()).thenReturn(numBatterySpecs);
    when(batteryInfoRepo.getMinMaxBatterySpecs()).thenReturn(minMaxBatterySpecs);
    when(batteryInfoRepo.getTierCounts()).thenReturn(tierCountsList);

    // Request
    GetSpecSvcOverviewRequest request = GetSpecSvcOverviewRequest.newBuilder().build();
    GetSpecSvcOverviewResponse response =
        grpcTestMethodInvoker.invokeNonblock(specSvcController, "getSpecSvcOverview", request);
    assertEquals(response.getSpecsCount(), 1);
    assertEquals(response.getMaxCurrent(), 4.4D);
    assertEquals(response.getTierCountListCount(), 2);
    assertEquals(response.getTierCountList(0).getTier(), "AX");
    assertEquals(response.getTierCountList(0).getMinVoltage(), 1.1D);

    // Verify
    verify(batteryInfoRepo).countBatterySpecs();
    verify(batteryInfoRepo).getMinMaxBatterySpecs();
    verify(batteryInfoRepo).getTierCounts();
  }
}
