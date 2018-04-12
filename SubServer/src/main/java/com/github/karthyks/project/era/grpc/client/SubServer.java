package com.github.karthyks.project.era.grpc.client;

import com.github.karthyks.project.era.network.Constant;
import com.github.karthyks.project.era.network.GrpcServer;
import com.github.karthyks.project.era.network.interceptors.JwtServerInterceptor;
import com.github.karthyks.project.era.network.interceptors.TraceIdServerInterceptor;
import com.github.karthyks.project.era.network.services.HookService;
import com.github.karthyks.project.era.serverhook.ServerHook;
import io.grpc.ServerInterceptors;

public class SubServer extends GrpcServer {
  public static final String CLIENT_NAME = System.getProperty("user.name");

  private SubServer() {
    super(8087);
  }

  public static void main(String[] args) {
    ServerHook serverHook = new ServerHook("localhost", 8086, CLIENT_NAME);
    serverHook.hookToServer();

    final SubServer subServer = new SubServer();
    JwtServerInterceptor jwtInterceptor = new JwtServerInterceptor(Constant.JWT_SECRET);
    subServer.withServices(ServerInterceptors.intercept(new HookService(), jwtInterceptor,
        new TraceIdServerInterceptor()));
    subServer.buildServer().startServer();
  }
}
