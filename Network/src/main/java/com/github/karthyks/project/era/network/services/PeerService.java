package com.github.karthyks.project.era.network.services;

import com.github.karthyks.project.era.network.GrpcServer;
import com.github.karthyks.project.era.network.model.PeerInfo;
import com.github.karthyks.project.era.proto.peer.PeerInfoServiceGrpc;
import com.github.karthyks.project.era.proto.peer.PeerRequest;
import com.github.karthyks.project.era.proto.peer.PeerResponse;
import io.grpc.stub.StreamObserver;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class PeerService extends PeerInfoServiceGrpc.PeerInfoServiceImplBase {

  @Override
  public StreamObserver<PeerRequest> getConnectedPeers(StreamObserver<PeerResponse> responseObserver) {
    return new StreamObserver<PeerRequest>() {
      @Override
      public void onNext(PeerRequest value) {
        System.out.println("Peer requested from " + value.getRequester());
        Set<PeerInfo> peerInfoList = GrpcServer.peersMap.keySet();
        PeerResponse.Builder peerBuilder = PeerResponse.newBuilder();
        for (PeerInfo peerInfo : peerInfoList) {
          List<Double> loadAverage = new LinkedList<>();
          for (double load : peerInfo.loadAverage) {
            loadAverage.add(load);
          }
          com.github.karthyks.project.era.proto.peer.PeerInfo peer =
              com.github.karthyks.project.era.proto.peer.PeerInfo.newBuilder()
                  .setAddress(peerInfo.serverAddress)
                  .setFreeMemory(peerInfo.freeMemory)
                  .setMaxMemory(peerInfo.maxMemory)
                  .setName(peerInfo.name)
                  .setPort(peerInfo.serverPort)
                  .setTotalMemory(peerInfo.totalMemory)
                  .addAllLoadAverage(loadAverage)
                  .build();
          peerBuilder.addPeerInfos(peer);
        }
        responseObserver.onNext(peerBuilder.build());
      }

      @Override
      public void onError(Throwable t) {

      }

      @Override
      public void onCompleted() {

      }
    };
  }
}
