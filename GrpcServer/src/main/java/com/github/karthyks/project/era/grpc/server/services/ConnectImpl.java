package com.github.karthyks.project.era.grpc.server.services;

import com.gihub.karthyks.project.era.proto.connect.ConnectGrpc;
import com.gihub.karthyks.project.era.proto.connect.ConnectRequest;
import com.gihub.karthyks.project.era.proto.connect.ConnectResponse;
import io.grpc.stub.StreamObserver;

public class ConnectImpl extends ConnectGrpc.ConnectImplBase {

    @Override
    public void initiate(ConnectRequest request, StreamObserver<ConnectResponse> responseObserver) {
       ConnectResponse response = ConnectResponse.newBuilder().setStatus("OK").build();
       responseObserver.onNext(response);
       responseObserver.onCompleted();
    }
}
