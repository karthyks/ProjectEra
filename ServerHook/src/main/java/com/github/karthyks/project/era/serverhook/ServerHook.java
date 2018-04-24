package com.github.karthyks.project.era.serverhook;

import com.github.karthyks.project.era.network.Constant;
import com.github.karthyks.project.era.network.GrpcServer;
import com.github.karthyks.project.era.proto.auth.AuthGrpc;
import com.github.karthyks.project.era.proto.auth.AuthRequest;
import com.github.karthyks.project.era.proto.auth.AuthResponse;
import com.github.karthyks.project.era.proto.hook.HookGrpc;
import com.github.karthyks.project.era.proto.hook.HookRequest;
import com.github.karthyks.project.era.proto.hook.HookResponse;
import com.github.karthyks.project.era.serverhook.auth.HookCredential;
import com.github.karthyks.project.era.network.auth.AuthUtils;
import io.grpc.Context;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerHook {

  private String serverAddress;
  private int serverPort;
  private String name;

  private ManagedChannel mChannel;
  private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  static final BlockingQueue<Runnable> hookQueue = new LinkedBlockingQueue<>();

  public ServerHook(String serverAddress, int serverPort, String name) {
    this.serverAddress = serverAddress;
    this.serverPort = serverPort;
    this.name = name;
  }

  public void authenticate() {
    mChannel = ManagedChannelBuilder.forAddress(serverAddress, serverPort)
        .usePlaintext()
        .build();
    AuthGrpc.AuthStub asyncStub = AuthGrpc.newStub(mChannel);
    String password = AuthUtils.digest(Constant.JWT_SECRET);
    asyncStub.authenticate(AuthRequest.newBuilder().setPassword(password).build(),
        new StreamObserver<AuthResponse>() {
          @Override
          public void onNext(AuthResponse value) {
            String token = value.getToken();
            System.out.println("Authentication token " + token);
            hook(token);
          }

          @Override
          public void onError(Throwable t) {
            System.out.println("Error Authentication " + t.getMessage());
          }

          @Override
          public void onCompleted() {
            System.out.println("Authentication Successful");
          }
        });
  }

  public void hookToServer() {
    System.out.println("Opened port is : " + GrpcServer.PORT);
    authenticate();
  }

  private void hook(String token) {
    final HookCredential hookCredential = new HookCredential(token, name, GrpcServer.PORT);
    mChannel = ManagedChannelBuilder.forAddress(serverAddress, serverPort)
        .usePlaintext()
        .build();
    Context.current().withValue(Constant.TRACE_ID_CTX_KEY, name)
        .run(() -> {
          HookGrpc.HookStub asyncStub = HookGrpc.newStub(mChannel)
              .withCallCredentials(hookCredential);

          StreamObserver<HookResponse> responseObserver = new StreamObserver<HookResponse>() {
            @Override
            public void onNext(HookResponse value) {
              System.out.println("On Next " + value.getStatus());
            }

            @Override
            public void onError(Throwable t) {
              System.out.println("Error " + t.toString());
              new Thread(() -> {
                try {
                  Thread.sleep(500);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                hookQueue.add(() -> hookToServer());
              }).start();
            }

            @Override
            public void onCompleted() {
              System.out.println("Completed");
            }
          };

          StreamObserver<HookRequest> requestObserver = asyncStub.initiate(responseObserver);
          scheduler.scheduleAtFixedRate(new HookRequestService(requestObserver, name),
              0, 10, TimeUnit.SECONDS);
        });
  }
}
