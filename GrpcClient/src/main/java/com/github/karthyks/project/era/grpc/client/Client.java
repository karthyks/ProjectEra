package com.github.karthyks.project.era.grpc.client;

import com.gihub.karthyks.project.era.proto.hook.HookGrpc;
import com.gihub.karthyks.project.era.proto.hook.HookRequest;
import com.gihub.karthyks.project.era.proto.hook.HookResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {
  private static final String CLIENT_NAME = System.getProperty("user.name");

  private static ManagedChannel channel;
  private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  private static final BlockingQueue<Runnable> hookQueue = new LinkedBlockingQueue<>();

  public static void main(String[] args) {
    channel = ManagedChannelBuilder.forAddress("localhost", 8086)
        .usePlaintext()
        .build();
    hookToServer();
  }

  private static void hookToServer() {
    HookGrpc.HookStub asyncStub = HookGrpc.newStub(channel);
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
          hookQueue.add(Client::hookToServer);
        }).start();
      }

      @Override
      public void onCompleted() {
        System.out.println("Completed");
      }
    };

    final StreamObserver<HookRequest> requestStreamObserver = asyncStub
        .initiate(responseStreamObserver);

    scheduler.scheduleAtFixedRate(() -> {
      HookRequest hookRequest = HookRequest.newBuilder().setName(CLIENT_NAME).build();
      requestStreamObserver.onNext(hookRequest);
      if (hookQueue.size() > 0) {
        try {
          hookQueue.take().run();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }, 0, 10, TimeUnit.SECONDS);
  }
}
