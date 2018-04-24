package com.github.karthyks.project.era.grpc.client.servlets;

public class Servlet {
  public static final String I_VEHICLE = "/IVehicle";
  public static final String VEHICLE_INFO = I_VEHICLE + "/getVehicleInfo";
  public static final String TRACK_VEHICLE = I_VEHICLE + "/trackVehicle";
  public static final String SAVE_VEHICLE = I_VEHICLE + "/saveVehicle";
  public static final String VEHICLE_HISTORY = I_VEHICLE + "/vehicleHistory";
}
