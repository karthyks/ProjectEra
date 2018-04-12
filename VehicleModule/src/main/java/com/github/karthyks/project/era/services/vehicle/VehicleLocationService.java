package com.github.karthyks.project.era.services.vehicle;

import com.github.karthyks.project.era.proto.vehicle.FetchVehicleLocationGrpc;
import com.github.karthyks.project.era.proto.vehicle.TrackingRequest;
import com.github.karthyks.project.era.proto.vehicle.TrackingResponse;
import com.github.karthyks.project.era.proto.vehicle.VehicleLocation;
import io.grpc.stub.StreamObserver;

public class VehicleLocationService extends FetchVehicleLocationGrpc.FetchVehicleLocationImplBase {

  @Override
  public void getLocationHistory(TrackingRequest request,
                                 StreamObserver<TrackingResponse> responseObserver) {
    responseObserver.onNext(TrackingResponse.newBuilder()
        .addVehicleLocations(VehicleLocation.newBuilder()
            .setAddress("Address 1")
            .setLatitude(2.1)
            .setLongitude(3.1)
            .build())
        .addVehicleLocations(VehicleLocation.newBuilder()
            .setAddress("Address 2")
            .setLatitude(2.2)
            .setLongitude(3.2)
            .build())
        .build());
    responseObserver.onCompleted();
  }

  @Override
  public StreamObserver<TrackingRequest>
  trackVehicle(StreamObserver<TrackingResponse> responseObserver) {
    return new StreamObserver<TrackingRequest>() {
      @Override
      public void onNext(TrackingRequest value) {
        responseObserver.onNext(TrackingResponse.newBuilder()
            .addVehicleLocations(VehicleLocation.newBuilder()
                .setAddress(value.getVehicleId())
                .setLatitude(2.1)
                .setLongitude(3.1)
                .build())
            .build());
      }

      @Override
      public void onError(Throwable t) {
        responseObserver.onError(t);
      }

      @Override
      public void onCompleted() {
        responseObserver.onCompleted();
      }
    };
  }
}
