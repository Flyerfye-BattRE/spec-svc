package com.battre.specsvc.controller;

import com.battre.specsvc.model.BatteryInfoType;
import com.battre.specsvc.model.BatteryTiersType;
import com.battre.specsvc.service.SpecSvc;
import com.battre.stubs.services.BatterySpecs;
import com.battre.stubs.services.BatteryTier;
import com.battre.stubs.services.BatteryTypeTerminalPair;
import com.battre.stubs.services.BatteryTypeTierPair;
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
import com.battre.stubs.services.SpecSvcGrpc;
import com.battre.stubs.services.TierCount;
import com.google.protobuf.DoubleValue;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class SpecSvcController extends SpecSvcGrpc.SpecSvcImplBase {
  private static final Logger logger = Logger.getLogger(SpecSvcController.class.getName());
  private final SpecSvc specSvc;

  @Autowired
  public SpecSvcController(SpecSvc specSvc) {
    this.specSvc = specSvc;
  }

  @Override
  public void getRandomBatteryTypes(
      GetRandomBatteryTypesRequest request,
      StreamObserver<GetRandomBatteryTypesResponse> responseObserver) {
    logger.info("getRandomBatteryTypes() started");

    int numBatteryTypes = request.getNumBatteryTypes();
    List<BatteryInfoType> batteries = specSvc.getRandomBatteries(numBatteryTypes);
    List<BatteryTypeTierPair> batteryTypesTierInfo = getBatteryTypeTierInfo(batteries);

    logger.info("getRandomBatteryTypes() response : " + batteryTypesTierInfo);

    GetRandomBatteryTypesResponse response =
        GetRandomBatteryTypesResponse.newBuilder().addAllBatteries(batteryTypesTierInfo).build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();

    logger.info("getRandomBatteryTypes() completed");
  }

  @Override
  public void getBatteryTerminalLayouts(
      GetBatteryTerminalLayoutsRequest request,
      StreamObserver<GetBatteryTerminalLayoutsResponse> responseObserver) {
    logger.info("getBatteryTerminalLayouts() started");

    List<BatteryInfoType> batteries =
        specSvc.getBatterySpecsByTypeId(request.getBatteryTypeIdsList());
    List<BatteryTypeTerminalPair> batteryTypesTerminalsInfo =
        getBatteryTypeTerminalsInfo(batteries);

    GetBatteryTerminalLayoutsResponse response =
        GetBatteryTerminalLayoutsResponse.newBuilder()
            .addAllBatteries(batteryTypesTerminalsInfo)
            .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();

    logger.info("getBatteryTerminalLayouts() completed");
  }

  @Override
  public void getAllBatterySpecs(
      GetAllBatterySpecsRequest request,
      StreamObserver<GetAllBatterySpecsResponse> responseObserver) {
    logger.info("getAllBatterySpecs() started");

    List<BatteryInfoType> batterySpecs = specSvc.getAllBatterySpecs();
    List<BatteryTiersType> batteryTiers = specSvc.getBatteryTiers();

    Map<Integer, String> tierMap = batteryTiers.stream()
            .collect(Collectors.toMap(BatteryTiersType::getBatteryTierId, BatteryTiersType::getBatteryTierLabel));

    GetAllBatterySpecsResponse.Builder responseBuilder = GetAllBatterySpecsResponse.newBuilder();
    for (BatteryInfoType batterySpec : batterySpecs) {
      BatterySpecs.Builder batteryTierBuilder =
          BatterySpecs.newBuilder()
              .setBatteryTypeId(batterySpec.getBatteryTypeId())
              .setBatteryName(batterySpec.getBatteryName())
              .setMfc(batterySpec.getMfc())
              .setTerminalLayoutId(batterySpec.getTerminalLayoutId())
              .setTierLabel(tierMap.get(batterySpec.getTierId()))
              .setComposition(batterySpec.getComposition());

      if (batterySpec.getSafetyInfo() != null) {
        batteryTierBuilder.setOptionalSafetyInfo(StringValue.of(batterySpec.getSafetyInfo()));
      }
      if (batterySpec.getMinVoltage() != null) {
        batteryTierBuilder.setOptionalMinVoltage(DoubleValue.of(batterySpec.getMinVoltage()));
      }
      if (batterySpec.getMaxVoltage() != null) {
        batteryTierBuilder.setOptionalMaxVoltage(DoubleValue.of(batterySpec.getMaxVoltage()));
      }
      if (batterySpec.getMinCurrent() != null) {
        batteryTierBuilder.setOptionalMinCurrent(DoubleValue.of(batterySpec.getMinCurrent()));
      }
      if (batterySpec.getMaxCurrent() != null) {
        batteryTierBuilder.setOptionalMaxCurrent(DoubleValue.of(batterySpec.getMaxCurrent()));
      }

      responseBuilder.addBatterySpecsList(batteryTierBuilder.build());
    }

    GetAllBatterySpecsResponse response = responseBuilder.build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();

    logger.info("getAllBatterySpecs() completed");
  }

  @Override
  public void getBatteryTiers(
      GetBatteryTiersRequest request, StreamObserver<GetBatteryTiersResponse> responseObserver) {
    logger.info("getBatteryTiers() started");

    List<BatteryTiersType> batteryTiers = specSvc.getBatteryTiers();

    GetBatteryTiersResponse.Builder responseBuilder = GetBatteryTiersResponse.newBuilder();
    for (BatteryTiersType batteryTier : batteryTiers) {
      BatteryTier.Builder batteryTierBuilder =
          BatteryTier.newBuilder()
              .setBatteryTierId(batteryTier.getBatteryTierId())
              .setBatteryTierLabel(batteryTier.getBatteryTierLabel());

      responseBuilder.addBatteryTierList(batteryTierBuilder.build());
    }

    GetBatteryTiersResponse response = responseBuilder.build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();

    logger.info("getBatteryTiers() completed");
  }

  @Override
  public void getSpecSvcOverview(
      GetSpecSvcOverviewRequest request,
      StreamObserver<GetSpecSvcOverviewResponse> responseObserver) {
    logger.info("getSpecSvcOverview() started");

    Integer numBatterySpecs = specSvc.countBatterySpecs();
    Object[] minMaxBatterySpecs = specSvc.getMinMaxBatterySpecs();
    List<TierCount> tierCountsList = specSvc.getTierCounts();

    GetSpecSvcOverviewResponse.Builder responseBuilder = GetSpecSvcOverviewResponse.newBuilder();
    responseBuilder.setSpecsCount(numBatterySpecs);
    responseBuilder.setMinVoltage((Double) minMaxBatterySpecs[0]);
    responseBuilder.setMaxVoltage((Double) minMaxBatterySpecs[1]);
    responseBuilder.setMinCurrent((Double) minMaxBatterySpecs[2]);
    responseBuilder.setMaxCurrent((Double) minMaxBatterySpecs[3]);

    for (TierCount tierCount : tierCountsList) {
      TierCount.Builder tierCountBuilder =
          TierCount.newBuilder()
                  .setTier(tierCount.getTier())
                  .setCount(tierCount.getCount())
                  .setMinVoltage(tierCount.getMinVoltage())
                  .setMaxVoltage(tierCount.getMaxVoltage())
                  .setMinCurrent(tierCount.getMinCurrent())
                  .setMaxCurrent(tierCount.getMaxCurrent());

      responseBuilder.addTierCountList(tierCountBuilder.build());
    }

    GetSpecSvcOverviewResponse response = responseBuilder.build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();

    logger.info("getSpecSvcOverview() completed");
  }

  private List<BatteryTypeTierPair> getBatteryTypeTierInfo(List<BatteryInfoType> batteryInfoTypes) {
    return batteryInfoTypes.stream()
        .map(
            batteryInfoType ->
                BatteryTypeTierPair.newBuilder()
                    .setBatteryTypeId(batteryInfoType.getBatteryTypeId())
                    .setBatteryTierId(batteryInfoType.getTierId())
                    .build())
        .collect(Collectors.toList());
  }

  private List<BatteryTypeTerminalPair> getBatteryTypeTerminalsInfo(
      List<BatteryInfoType> batteryInfoTypes) {
    return batteryInfoTypes.stream()
        .map(
            batteryInfoType ->
                BatteryTypeTerminalPair.newBuilder()
                    .setBatteryTypeId(batteryInfoType.getBatteryTypeId())
                    .setBatteryTerminalLayoutId(batteryInfoType.getTerminalLayoutId())
                    .build())
        .collect(Collectors.toList());
  }
}
