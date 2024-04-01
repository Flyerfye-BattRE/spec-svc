package com.battre.specsvc.service;

import com.battre.specsvc.model.BatteryType;
import com.battre.specsvc.repository.BatteryTypeRepository;
import com.battre.stubs.services.SpecSvcEmptyRequest;
import com.battre.stubs.services.SpecSvcResponse;
import com.battre.stubs.services.SpecSvcGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Level;
import java.util.logging.Logger;

@GrpcService
public class SpecsvcServiceImpl extends SpecSvcGrpc.SpecSvcImplBase {
    private static final Logger logger = Logger.getLogger(SpecsvcServiceImpl.class.getName());
    private final BatteryTypeRepository batteryTypeRepository;

    @Autowired
    public SpecsvcServiceImpl(BatteryTypeRepository batteryTypeRepository) {
        this.batteryTypeRepository = batteryTypeRepository;
    }

    @Override
    public void getRandomBatteryType(SpecSvcEmptyRequest request, StreamObserver<SpecSvcResponse> responseObserver) {
        logger.log(Level.INFO, "SpecSvc sees request");

        BatteryType batteryType = batteryTypeRepository.getRandomBatteryType();
        String batteryInfo = String.valueOf(batteryType.getBatteryTypeId());

        logger.log(Level.INFO, "SpecSvc sends response of : " + batteryInfo);

        SpecSvcResponse myResponse = SpecSvcResponse.newBuilder()
                .setResponse(batteryInfo)
                .build();

        responseObserver.onNext(myResponse);
        responseObserver.onCompleted();
    }
}
