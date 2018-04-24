package com.github.karthyks.project.era.services.vehicle;

import com.github.karthyks.project.era.proto.vehicle.FetchVehicleInfoGrpc;
import com.github.karthyks.project.era.proto.vehicle.VehicleInfoRequest;
import com.github.karthyks.project.era.proto.vehicle.VehicleInfoResponse;
import io.grpc.stub.StreamObserver;

public class VehicleInfoService extends FetchVehicleInfoGrpc.FetchVehicleInfoImplBase {

  @Override
  public void getVehicleInfo(VehicleInfoRequest request,
                             StreamObserver<VehicleInfoResponse> responseObserver) {
    responseObserver.onNext(VehicleInfoResponse.newBuilder()
        .setCabCode("CabCode")
        .build());
    responseObserver.onCompleted();
  }
}
