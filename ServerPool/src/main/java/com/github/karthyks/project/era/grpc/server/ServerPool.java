package com.github.karthyks.project.era.grpc.server;

import com.github.karthyks.project.era.network.Constant;
import com.github.karthyks.project.era.network.GrpcServer;
import com.github.karthyks.project.era.network.interceptors.JwtServerInterceptor;
import com.github.karthyks.project.era.network.interceptors.TraceIdServerInterceptor;
import com.github.karthyks.project.era.network.services.HookService;
import io.grpc.ServerInterceptors;

public class ServerPool extends GrpcServer {

  private ServerPool(int port) {
    super(port);
  }

  /**
   * Main launches the server from the command line.
   */
  public static void main(String[] args) {
    int port = 8086;
    if (args != null && args.length > 0) {
      port = Integer.parseInt(args[0]);
    }
    JwtServerInterceptor jwtInterceptor = new JwtServerInterceptor(Constant.JWT_SECRET);
    final ServerPool serverPool = new ServerPool(port);
    serverPool.withServices(ServerInterceptors.intercept(new HookService(), jwtInterceptor,
        new TraceIdServerInterceptor()));
    serverPool.buildServer().startServer();
  }
}
