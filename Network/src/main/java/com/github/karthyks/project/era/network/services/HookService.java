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
        peerInfo.traceAddress = Constant.REMOTE_ADDRESS.get();
        peerInfo.tracePort = Constant.REMOTE_PORT.get();
        peerInfo.serverAddress = Constant.REMOTE_SERVER_ADDRESS.get();
        peerInfo.serverPort = Constant.REMOTE_SERVER_PORT.get();
        peerInfo.name = value.getName();
        peerInfo.freeMemory = Long.parseLong(value.getFreeMemory());
        peerInfo.maxMemory = Long.parseLong(value.getMaxMemory());
        peerInfo.totalMemory = Long.parseLong(value.getTotalMemory());
        String load = value.getSystemLoad();
        load = load.split(":")[load.split(":").length - 1];
        peerInfo.loadAverage = new double[]{Double.parseDouble(load.split(",")[0]),
            Double.parseDouble(load.split(",")[1]),
            Double.parseDouble(load.split(",")[2])};
        peersMap.put(peerInfo, responseObserver);
      }

      @Override
      public void onError(Throwable t) {
        PeerInfo peerInfo = new PeerInfo();
        peerInfo.traceAddress = Constant.REMOTE_ADDRESS.get();
        peerInfo.tracePort = Constant.REMOTE_PORT.get();
        System.out.println("Hook disconnected from " + peerInfo.traceAddress);
        peersMap.remove(peerInfo);
      }

      @Override
      public void onCompleted() {
        PeerInfo peerInfo = new PeerInfo();
        peerInfo.traceAddress = Constant.REMOTE_ADDRESS.get();
        peerInfo.tracePort = Constant.REMOTE_PORT.get();
        peersMap.remove(peerInfo);
        responseObserver.onCompleted();
      }
    };
  }
}

