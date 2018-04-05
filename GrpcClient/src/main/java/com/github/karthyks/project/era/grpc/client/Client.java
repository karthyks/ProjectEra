package com.github.karthyks.project.era.grpc.client;

import com.gihub.karthyks.project.era.proto.hook.HookGrpc;
import com.gihub.karthyks.project.era.proto.hook.HookRequest;
import com.gihub.karthyks.project.era.proto.hook.HookResponse;
import com.github.karthyks.project.era.grpc.client.services.HookService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {

  private static ManagedChannel channel;
  private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

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
      }

      @Override
      public void onCompleted() {
        System.out.println("Completed");
      }
    };

    StreamObserver<HookRequest> requestStreamObserver = asyncStub.initiate(responseStreamObserver);
    scheduler.scheduleAtFixedRate(new HookService(requestStreamObserver), 0, 10, TimeUnit.SECONDS);
  }
}
