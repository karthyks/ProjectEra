package com.github.karthyks.project.era.grpc.client;

import com.github.karthyks.project.era.network.Constant;
import com.github.karthyks.project.era.network.GrpcServer;
import com.github.karthyks.project.era.network.interceptors.ServerTokenInterceptor;
import com.github.karthyks.project.era.network.interceptors.TraceIdServerInterceptor;
import com.github.karthyks.project.era.network.services.HookService;
import com.github.karthyks.project.era.network.services.PeerService;
import com.github.karthyks.project.era.serverhook.ServerHook;
import com.github.karthyks.project.era.network.auth.AuthUtils;
import io.grpc.ServerInterceptors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SubServer extends GrpcServer {
  //  public static final String CLIENT_NAME = System.getProperty("user.name");
  public static final String CLIENT_NAME = "karthik - 1";

  public static final ExecutorService restThread = Executors.newSingleThreadExecutor();

  private SubServer() {
    super(8087);
  }

  public static void main(String[] args) {
    ServerHook serverHook = new ServerHook("localhost", 8086, CLIENT_NAME);
    serverHook.hookToServer();

    restThread.submit(new RestServer());

    final SubServer subServer = new SubServer();
    String password = AuthUtils.digest(Constant.JWT_SECRET);
    ServerTokenInterceptor tokenInterceptor = new ServerTokenInterceptor(password);
    subServer.withServices(ServerInterceptors.intercept(new HookService(), tokenInterceptor,
        new TraceIdServerInterceptor()));
    subServer.withServices(ServerInterceptors.intercept(new PeerService(), tokenInterceptor));
    subServer.enableAuth();
    subServer.buildServer().startServer();
  }
}
