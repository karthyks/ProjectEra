package com.github.karthyks.project.era.grpc.server.services;

import com.github.karthyks.project.era.grpc.server.model.PeerInfo;

public interface IPeerListener {

  void onConnected(PeerInfo peerInfo);
}
