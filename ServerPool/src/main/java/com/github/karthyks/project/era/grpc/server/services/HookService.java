package com.github.karthyks.project.era.grpc.server.services;


import com.github.karthyks.project.era.network.Constant;
import com.github.karthyks.project.era.proto.hook.HookGrpc;
import com.github.karthyks.project.era.proto.hook.HookRequest;
import com.github.karthyks.project.era.proto.hook.HookResponse;
import io.grpc.stub.StreamObserver;

import static com.github.karthyks.project.era.grpc.server.ServerPool.observersMap;

public class HookService extends HookGrpc.HookImplBase {

  public HookService() {
    System.out.println("Ready to be hooked!");
  }

  @Override
  public StreamObserver<HookRequest> initiate(final StreamObserver<HookResponse> responseObserver) {
    return new StreamObserver<HookRequest>() {
      @Override
      public void onNext(HookRequest value) {
        String address = Constant.REMOTE_ADDRESS.get();
        observersMap.put(address, responseObserver);
        System.out.println("Packets received : " + value.getName());
      }

      @Override
      public void onError(Throwable t) {
        String address = Constant.REMOTE_ADDRESS.get();
        System.out.println("Hook disconnected from " + address);
        observersMap.remove(address);
      }

      @Override
      public void onCompleted() {
        String address = Constant.REMOTE_ADDRESS.get();
        observersMap.remove(address);
        responseObserver.onCompleted();
      }
    };
  }
}
