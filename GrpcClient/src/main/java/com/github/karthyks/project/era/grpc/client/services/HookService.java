package com.github.karthyks.project.era.grpc.client.services;

import com.gihub.karthyks.project.era.proto.hook.HookRequest;
import io.grpc.stub.StreamObserver;

public class HookService implements Runnable {

  private StreamObserver<HookRequest> hookRequestStreamObserver;

  public HookService(StreamObserver<HookRequest> hookRequestStreamObserver) {
    this.hookRequestStreamObserver = hookRequestStreamObserver;
  }

  @Override
  public void run() {
    if (hookRequestStreamObserver == null) return;
    HookRequest hookRequest = HookRequest.newBuilder().setName("Client 1").build();
    hookRequestStreamObserver.onNext(hookRequest);
  }
}
