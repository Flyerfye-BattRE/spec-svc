package com.battre.specsvc.service;

import com.battre.specsvc.model.BatteryType;
import com.battre.specsvc.repository.BatteryTypesRepository;
import com.battre.stubs.services.BatteryTypeTerminalPair;
import com.battre.stubs.services.BatteryTypeTierPair;
import com.battre.stubs.services.GetBatteryTerminalLayoutsRequest;
import com.battre.stubs.services.GetBatteryTerminalLayoutsResponse;
import com.battre.stubs.services.GetRandomBatteryTypesRequest;
import com.battre.stubs.services.GetRandomBatteryTypesResponse;
import com.battre.stubs.services.SpecSvcGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@GrpcService
public class SpecsvcServiceImpl extends SpecSvcGrpc.SpecSvcImplBase {
    private static final Logger logger = Logger.getLogger(SpecsvcServiceImpl.class.getName());
    private final BatteryTypesRepository batTypRepo;

    @Autowired
    public SpecsvcServiceImpl(BatteryTypesRepository batTypRepo) {
        this.batTypRepo = batTypRepo;
    }

    @Override
    public void getRandomBatteryTypes(GetRandomBatteryTypesRequest request, StreamObserver<GetRandomBatteryTypesResponse> responseObserver) {
        logger.info("getRandomBatteryTypes() started");

        int numBatteryTypes = request.getNumBatteryTypes();
        List<BatteryType> batteries = batTypRepo.getRandomBatteries(numBatteryTypes);
        List<BatteryTypeTierPair> batteryTypesTierInfo = getBatteryTypeTierInfo(batteries);

        logger.info("getRandomBatteryTypes() response : " + batteryTypesTierInfo);

        GetRandomBatteryTypesResponse response = GetRandomBatteryTypesResponse.newBuilder()
                .addAllBatteries(batteryTypesTierInfo)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        logger.info("getRandomBatteryTypes() completed");
    }

    @Override
    public void getBatteryTerminalLayouts(GetBatteryTerminalLayoutsRequest request, StreamObserver<GetBatteryTerminalLayoutsResponse> responseObserver) {
        logger.info("getBatteryTerminalLayouts() started");

        List<BatteryType> batteries = batTypRepo.getBatteriesByTypeId(request.getBatteryTypeIdsList());
        List<BatteryTypeTerminalPair> batteryTypesTerminalsInfo = getBatteryTypeTerminalsInfo(batteries);

        GetBatteryTerminalLayoutsResponse response = GetBatteryTerminalLayoutsResponse.newBuilder()
                .addAllBatteries(batteryTypesTerminalsInfo)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        logger.info("getBatteryTerminalLayouts() completed");
    }

    private List<BatteryTypeTierPair> getBatteryTypeTierInfo(List<BatteryType> batteryTypes) {
        return batteryTypes.stream()
                .map(batteryType -> BatteryTypeTierPair.newBuilder()
                        .setBatteryTypeId(batteryType.getBatteryTypeId())
                        .setBatteryTierId(batteryType.getBatteryTierId())
                        .build())
                .collect(Collectors.toList());
    }

    private List<BatteryTypeTerminalPair> getBatteryTypeTerminalsInfo(List<BatteryType> batteryTypes) {
        return batteryTypes.stream()
                .map(batteryType -> BatteryTypeTerminalPair.newBuilder()
                        .setBatteryTypeId(batteryType.getBatteryTypeId())
                        .setBatteryTerminalLayoutId(batteryType.getTerminalLayoutId())
                        .build())
                .collect(Collectors.toList());
    }
}
