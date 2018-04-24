package com.github.karthyks.project.era.grpc.client;

import com.github.karthyks.project.era.grpc.client.servlets.SaveVehicleServlet;
import com.github.karthyks.project.era.grpc.client.servlets.Servlet;
import com.github.karthyks.project.era.grpc.client.servlets.TrackVehicleServlet;
import com.github.karthyks.project.era.grpc.client.servlets.VehicleInfoServlet;
import in.mtap.iincube.mongoapi.MongoClient;
import in.mtap.iincube.mongoser.Mongoser;
import in.mtap.iincube.mongoser.config.MongoConfig;
import in.mtap.iincube.mongoser.config.ServerConfig;
import io.airlift.airline.Command;
import io.airlift.airline.HelpOption;
import io.airlift.airline.Option;

@Command(name = RestServer.NAME, description = "rest server")
public class RestServer extends HelpOption implements Runnable {
  static final String NAME = "Rest Server";

  @Option(name = {"-p", "--port"}, description = "port number to listen")
  private int portNo = 8081;

  @Option(name = {"-m", "--mongo"}, description = "mongo servers in format [host:port]")
  private String server = "localhost:27017";

  @Option(name = {"-db", "--database"}, description = "database name")
  public String realm = "test";

  private ServerConfig getServerConfig() {
    return new ServerConfig.Builder(portNo).build();
  }

  private MongoConfig getMongoConfig() {
    return new MongoConfig.Builder(server).build();
  }

  @Override
  public void run() {
    System.out.println("Running rest ");
    MongoConfig mongoConfig = getMongoConfig();
    ServerConfig serverConfig = getServerConfig();
    MongoClient mongoClient = mongoConfig.getMongoClient();

    final Mongoser mongoser = buildWithAllServlets(Mongoser.using(mongoConfig, serverConfig)
        .enableDefaultAuth(), mongoClient)
        .build();

    try {
      mongoser.start();
    } catch (Exception e) {
      e.printStackTrace();
    }

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      try {
        mongoser.shutdown();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }));
  }

  private Mongoser.Builder buildWithAllServlets(Mongoser.Builder mongoserBuilder,
                                                MongoClient mongoClient) {
    return mongoserBuilder.enableDefaultServlets()
        .addServlet(Servlet.VEHICLE_INFO, new VehicleInfoServlet())
        .addServlet(Servlet.TRACK_VEHICLE, new TrackVehicleServlet())
        .addServlet(Servlet.SAVE_VEHICLE, new SaveVehicleServlet());
  }
}