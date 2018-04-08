package com.github.karthyks.project.era.grpc.client;

import com.github.karthyks.project.era.grpc.client.auth.JwtCallCredential;
import com.github.karthyks.project.era.grpc.client.services.HookRequestService;
import com.github.karthyks.project.era.network.Constant;
import com.github.karthyks.project.era.network.auth.JwtCreator;
import com.github.karthyks.project.era.proto.hook.HookGrpc;
import com.github.karthyks.project.era.proto.hook.HookRequest;
import com.github.karthyks.project.era.proto.hook.HookResponse;
import io.grpc.CallOptions;
import io.grpc.Context;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {
  public static final String CLIENT_NAME = System.getProperty("user.name");

  private static ManagedChannel channel;
  private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  public static final BlockingQueue<Runnable> hookQueue = new LinkedBlockingQueue<>();

  public static void main(String[] args) {
    String jwt = JwtCreator.create(Constant.ISSUER, CLIENT_NAME);
    System.out.println("JWT " + jwt);
    JwtCallCredential jwtCallCredential = new JwtCallCredential(jwt);

    channel = ManagedChannelBuilder.forAddress("localhost", 8086)
        .usePlaintext()
        .build();
    hookToServer(jwtCallCredential);
  }

  private static void hookToServer(final JwtCallCredential jwtCallCredential) {
    Context.current().withValue(Constant.TRACE_ID_CTX_KEY, "1")
        .run(() -> {
          HookGrpc.HookStub asyncStub = HookGrpc.newStub(channel)
              .withCallCredentials(jwtCallCredential)
              .withOption(CallOptions.Key.of("client", CLIENT_NAME), CLIENT_NAME);
          StreamObserver<HookResponse> responseStreamObserver = new StreamObserver<HookResponse>() {
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
                hookQueue.add(() -> {
                  hookToServer(new JwtCallCredential(JwtCreator.create(Constant.ISSUER,
                      CLIENT_NAME)));
                });
              }).start();
            }

            @Override
            public void onCompleted() {
              System.out.println("Completed");
            }
          };

          StreamObserver<HookRequest> requestStreamObserver = asyncStub
              .initiate(responseStreamObserver);

          scheduler.scheduleAtFixedRate(new HookRequestService(requestStreamObserver),
              0, 10, TimeUnit.SECONDS);
        });
  }
}
