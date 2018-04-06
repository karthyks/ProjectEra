package com.github.karthyks.project.era.grpc.client.services;

import com.gihub.karthyks.project.era.proto.hook.HookRequest;
import com.github.karthyks.project.era.grpc.client.Client;
import io.grpc.stub.StreamObserver;

public class HookRequestService implements Runnable {

  private StreamObserver<HookRequest> requestStreamObserver;

  public HookRequestService(StreamObserver<HookRequest> requestStreamObserver) {
    this.requestStreamObserver = requestStreamObserver;
  }

  @Override
  public void run() {
    HookRequest hookRequest = HookRequest.newBuilder().setName(Client.CLIENT_NAME).build();
    requestStreamObserver.onNext(hookRequest);
    if (Client.hookQueue.size() > 0) {
      try {
        Client.hookQueue.take().run();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
