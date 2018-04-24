package com.github.karthyks.project.era.services.vehicle;

import com.github.karthyks.project.era.network.Constant;
import com.github.karthyks.project.era.network.GrpcServer;
import com.github.karthyks.project.era.network.interceptors.ServerTokenInterceptor;
import com.github.karthyks.project.era.network.interceptors.TraceIdServerInterceptor;
import com.github.karthyks.project.era.serverhook.ServerHook;
import com.github.karthyks.project.era.network.auth.AuthUtils;
import com.github.karthyks.project.era.services.vehicle.dao.VehicleLocationDao;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.grpc.ServerInterceptors;

public class VehicleServer extends GrpcServer {
  private static final String DB_NAME = "test";
  private static MongoDatabase mongoDatabase;

  private VehicleServer() {
    super(8089);
  }


  public static void main(String[] args) {
    connectMongo();
    ServerHook serverHook = new ServerHook("localhost", 8088,
        "vehicle");
    serverHook.hookToServer();

    final VehicleServer vehicleServer = new VehicleServer();
    String password = AuthUtils.digest(Constant.JWT_SECRET);
    ServerTokenInterceptor interceptor = new ServerTokenInterceptor(password);

    VehicleLocationDao locationDao = new VehicleLocationDao(mongoDatabase);

    TraceIdServerInterceptor traceInterceptor = new TraceIdServerInterceptor();
    vehicleServer.withServices(
        ServerInterceptors.intercept(new VehicleInfoService(), interceptor, traceInterceptor),
        ServerInterceptors.intercept(new FetchLocationService(locationDao), interceptor,
            traceInterceptor),
        ServerInterceptors.intercept(new SaveLocationService(locationDao), interceptor,
            traceInterceptor));
    vehicleServer.enableAuth();
    vehicleServer.buildServer().startServer();
  }


  private static void connectMongo() {
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    mongoDatabase = mongoClient.getDatabase(DB_NAME);
  }
}