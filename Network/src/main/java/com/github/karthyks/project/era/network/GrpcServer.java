package com.github.karthyks.project.era.network;

import com.github.karthyks.project.era.network.auth.AuthService;
import com.github.karthyks.project.era.network.model.PeerInfo;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import io.grpc.stub.StreamObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public abstract class GrpcServer {
  public static String PUBLIC_ADDRESS = "";
  public static int PORT = -1;

  public static final Map<PeerInfo, StreamObserver> peersMap = new LinkedHashMap<>();
  private static final Logger logger = Logger.getLogger(GrpcServer.class.getName());

  private Server server;
  private ServerBuilder serverBuilder;
  private List<BindableService> services = new LinkedList<>();
  private List<ServerServiceDefinition> serverDefinitions = new LinkedList<>();

  /**
   * @param port The port which the Grpc server is running.
   *             -1 in case if it is a client only application.
   */
  public GrpcServer(int port) {
    getPublicAddress();
    PORT = port;
    serverBuilder = ServerBuilder.forPort(port);
  }

  /**
   * Starts the server in the main thread
   */
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
    logger.info("Server started, listening on " + PORT);
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      // Use stderr here since the logger may have been reset by its JVM shutdown hook.
      System.err.println("*** shutting down gRPC server since JVM is shutting down");
      GrpcServer.this.stop();
      System.err.println("*** server shut down");
    }));
    blockUntilShutdown();
  }

  /**
   * Starts the server in a different thread
   */

  public void startServerAsync() {
    ExecutorService singleThread = Executors.newSingleThreadExecutor();
    singleThread.submit(this::startServer);
  }

  private void stop() {
    if (server != null) {
      server.shutdown();
    }
  }

  public void withServices(BindableService... services) {
    this.services.addAll(Arrays.asList(services));
  }

  public void withServices(ServerServiceDefinition... serverDefinitions) {
    this.serverDefinitions.addAll(Arrays.asList(serverDefinitions));
  }

  /**
   * On enabling this method services should not be added with ServerServiceDefinition
   */
  public void enableAuth() {
    this.services.add(new AuthService());
  }

  public GrpcServer buildServer() {
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

  private static void getPublicAddress() {
    URL whatismyip = null;
    try {
      whatismyip = new URL("http://checkip.amazonaws.com");
      BufferedReader in = new BufferedReader(new InputStreamReader(
          whatismyip.openStream()));
      PUBLIC_ADDRESS = in.readLine();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}