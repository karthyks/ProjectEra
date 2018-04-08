package com.github.karthyks.project.era.grpc.server.services;

import com.github.karthyks.project.era.grpc.server.model.PeerInfo;

public class PeerListener implements IPeerListener {

  @Override
  public void onConnected(PeerInfo peerInfo) {
    System.out.println("On Connected " + peerInfo.clientName + " : " + peerInfo.address);
  }
}
