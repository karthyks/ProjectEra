package com.github.karthyks.project.era.network.services;

import com.github.karthyks.project.era.network.Constant;
import com.github.karthyks.project.era.network.model.PeerInfo;
import com.github.karthyks.project.era.proto.hook.HookGrpc;
import com.github.karthyks.project.era.proto.hook.HookRequest;
import com.github.karthyks.project.era.proto.hook.HookResponse;
import io.grpc.stub.StreamObserver;

import static com.github.karthyks.project.era.network.GrpcServer.peersMap;

public class HookService extends HookGrpc.HookImplBase {

  public HookService() {
    System.out.println("Ready to be hooked!");
  }

  @Override
  public StreamObserver<HookRequest> initiate(final StreamObserver<HookResponse> responseObserver) {
    return new StreamObserver<HookRequest>() {
      @Override
      public void onNext(HookRequest value) {
        PeerInfo peerInfo = new PeerInfo();
        peerInfo.address = Constant.REMOTE_ADDRESS.get();
        peerInfo.name = value.getName();
        peersMap.put(peerInfo, responseObserver);
        System.out.println("Received from " + peerInfo.name);
        System.out.println("With Load " + value.getSystemLoad());
        System.out.println("Free Memory " + value.getFreeMemory());
        System.out.println("Max Memory " + value.getMaxMemory());
      }

      @Override
      public void onError(Throwable t) {
        PeerInfo peerInfo = new PeerInfo();
        peerInfo.address = Constant.REMOTE_ADDRESS.get();
        System.out.println("Hook disconnected from " + peerInfo.address);
        peersMap.remove(peerInfo);
      }

      @Override
      public void onCompleted() {
        PeerInfo peerInfo = new PeerInfo();
        peerInfo.address = Constant.REMOTE_ADDRESS.get();
        peersMap.remove(peerInfo);
        responseObserver.onCompleted();
      }
    };
  }
}

