package com.github.karthyks.project.era.grpc.server;

import com.github.karthyks.project.era.network.Constant;
import com.github.karthyks.project.era.network.GrpcServer;
import com.github.karthyks.project.era.network.interceptors.ServerTokenInterceptor;
import com.github.karthyks.project.era.network.interceptors.TraceIdServerInterceptor;
import com.github.karthyks.project.era.network.services.HookService;
import com.github.karthyks.project.era.network.services.PeerService;
import com.github.karthyks.project.era.network.auth.AuthUtils;
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
    String password = AuthUtils.digest(Constant.JWT_SECRET);
    ServerTokenInterceptor tokenInterceptor = new ServerTokenInterceptor(password);
    final ServerPool serverPool = new ServerPool(port);
    serverPool.withServices(ServerInterceptors.intercept(new HookService(), tokenInterceptor,
        new TraceIdServerInterceptor()));
    serverPool.withServices(ServerInterceptors.intercept(new PeerService()));
    serverPool.enableAuth();
    serverPool.buildServer().startServerAsync();
    System.out.println("Server started ...");
  }
}
