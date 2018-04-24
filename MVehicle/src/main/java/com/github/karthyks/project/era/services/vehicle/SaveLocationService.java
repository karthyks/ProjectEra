package com.github.karthyks.project.era.services.vehicle;

import com.github.karthyks.project.era.proto.vehicle.SaveLocationRequest;
import com.github.karthyks.project.era.proto.vehicle.SaveLocationResponse;
import com.github.karthyks.project.era.proto.vehicle.SaveVehicleLocationGrpc;
import com.github.karthyks.project.era.services.vehicle.dao.VehicleLocationDao;
import io.grpc.stub.StreamObserver;

public class SaveLocationService extends SaveVehicleLocationGrpc.SaveVehicleLocationImplBase {
  private VehicleLocationDao locationDao;

  public SaveLocationService(VehicleLocationDao locationDao) {
    this.locationDao = locationDao;
  }

  @Override
  public StreamObserver<SaveLocationRequest>
  saveVehicleLocation(StreamObserver<SaveLocationResponse> responseObserver) {
    return new StreamObserver<SaveLocationRequest>() {
      @Override
      public void onNext(SaveLocationRequest value) {
        System.out.println("Request " + value.getVehicleLocationsCount());
        locationDao.saveLocations(value);
      }

      @Override
      public void onError(Throwable t) {

      }

      @Override
      public void onCompleted() {

      }
    };
  }
}
