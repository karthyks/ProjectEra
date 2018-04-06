package com.github.karthyks.project.era.grpc.server;

import com.github.karthyks.project.era.grpc.server.interceptors.JwtServerInterceptor;
import com.github.karthyks.project.era.grpc.server.interceptors.TraceIdServerInterceptor;
import com.github.karthyks.project.era.grpc.server.services.HookService;
import com.github.karthyks.project.era.grpc.server.services.PeerListener;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;

import java.io.IOException;
import java.util.logging.Logger;

public class ServerPool {
  private static final Logger logger = Logger.getLogger(ServerPool.class.getName());
  private Server server;

  private void start(int port) throws IOException {
    JwtServerInterceptor jwtInterceptor = new JwtServerInterceptor(Constant.JWT_SECRET,
        new PeerListener());
    server = ServerBuilder.forPort(port)
        .addService(ServerInterceptors.intercept(new HookService(), jwtInterceptor,
            new TraceIdServerInterceptor()))
        .build()
        .start();
    logger.info("Server started, listening on " + port);
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      // Use stderr here since the logger may have been reset by its JVM shutdown hook.
      System.err.println("*** shutting down gRPC server since JVM is shutting down");
      ServerPool.this.stop();
      System.err.println("*** server shut down");
    }));
  }

  private void stop() {
    if (server != null) {
      server.shutdown();
    }
  }

  /**
   * Await termination on the main thread since the grpc library uses daemon threads.
   */
  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  /**
   * Main launches the server from the command line.
   */
  public static void main(String[] args) throws IOException, InterruptedException {
    final ServerPool server = new ServerPool();
    if (args == null || args.length <= 0) {
      server.start(50051);
    } else {
      server.start(Integer.parseInt(args[0]));
    }
    server.blockUntilShutdown();
  }
}
