package com.github.karthyks.project.era.network;

import com.github.karthyks.project.era.network.model.PeerInfo;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

public abstract class GrpcServer {
  public static final Map<PeerInfo, StreamObserver> peersMap = new LinkedHashMap<>();
  private static final Logger logger = Logger.getLogger(GrpcServer.class.getName());
  private int port;

  private Server server;
  private BindableService[] services;
  private ServerServiceDefinition[] serverDefinitions;

  public GrpcServer(int port) {
    this.port = port;
  }

  public void startServer() {
    if (server == null) {
      logger.severe("Server not initialized. " +
          "Try initializing server before calling startServer()");
      return;
    }
    try {
      server.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
    logger.info("Server started, listening on " + port);
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      // Use stderr here since the logger may have been reset by its JVM shutdown hook.
      System.err.println("*** shutting down gRPC server since JVM is shutting down");
      GrpcServer.this.stop();
      System.err.println("*** server shut down");
    }));
    blockUntilShutdown();
  }

  private void stop() {
    if (server != null) {
      server.shutdown();
    }
  }

  public void withServices(BindableService... services) {
    this.services = services;
  }

  public void withServices(ServerServiceDefinition... serverDefinitions) {
    this.serverDefinitions = serverDefinitions;
  }

  public GrpcServer buildServer() {
    ServerBuilder serverBuilder = ServerBuilder.forPort(port);
    if (services != null) {
      for (BindableService service : services) {
        serverBuilder.addService(service);
      }
    }
    if (serverDefinitions != null) {
      for (ServerServiceDefinition serviceDefinition : serverDefinitions) {
        serverBuilder.addService(serviceDefinition);
      }
    }
    server = serverBuilder.build();
    return this;
  }

  /**
   * Await termination on the main thread since the grpc library uses daemon threads.
   */
  private void blockUntilShutdown() {
    if (server != null) {
      try {
        server.awaitTermination();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}