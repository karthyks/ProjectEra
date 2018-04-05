package com.github.karthyks.project.era.grpc.server.services;

import com.gihub.karthyks.project.era.proto.hook.HookGrpc;
import com.gihub.karthyks.project.era.proto.hook.HookRequest;
import com.gihub.karthyks.project.era.proto.hook.HookResponse;
import io.grpc.stub.StreamObserver;

import java.util.LinkedList;
import java.util.List;

public class HookService extends HookGrpc.HookImplBase {
  private static final List<StreamObserver> observers = new LinkedList<>();

  public HookService() {
    System.out.println("Ready to be hooked!");
  }

  @Override
  public StreamObserver<HookRequest> initiate(final StreamObserver<HookResponse> responseObserver) {
    return new StreamObserver<HookRequest>() {
      @Override
      public void onNext(HookRequest value) {
        if (!observers.contains(responseObserver))
          observers.add(responseObserver);
        System.out.println("Request value " + value.getName());
      }

      @Override
      public void onError(Throwable t) {
        observers.remove(responseObserver);
      }

      @Override
      public void onCompleted() {
        responseObserver.onCompleted();
        observers.remove(responseObserver);
      }
    };
  }
}
