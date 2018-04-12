package com.github.karthyks.project.era.services.vehicle;

import com.github.karthyks.project.era.network.GrpcServer;
import com.github.karthyks.project.era.serverhook.ServerHook;

public class VehicleService extends GrpcServer {

  private VehicleService(int port) {
    super(port);
  }


  public static void main(String[] args) {
    ServerHook serverHook = new ServerHook("localhost", 8087,
        "vehicle");
    serverHook.hookToServer();
    final VehicleService vehicleService = new VehicleService(8088);
    vehicleService.withServices(new VehicleInfoService(),
        new VehicleLocationService());
    vehicleService.buildServer().startServer();
  }
}