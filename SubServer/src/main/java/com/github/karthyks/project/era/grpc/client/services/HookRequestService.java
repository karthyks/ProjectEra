package com.github.karthyks.project.era.grpc.client.services;

import com.github.karthyks.project.era.grpc.client.SubServer;
import com.github.karthyks.project.era.proto.hook.HookRequest;
import io.grpc.stub.StreamObserver;

public class HookRequestService implements Runnable {

  private StreamObserver<HookRequest> requestStreamObserver;

  public HookRequestService(StreamObserver<HookRequest> requestStreamObserver) {
    this.requestStreamObserver = requestStreamObserver;
  }

  @Override
  public void run() {
    HookRequest hookRequest = HookRequest.newBuilder().setName(SubServer.CLIENT_NAME).build();
    requestStreamObserver.onNext(hookRequest);
    if (SubServer.hookQueue.size() > 0) {
      try {
        SubServer.hookQueue.take().run();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
