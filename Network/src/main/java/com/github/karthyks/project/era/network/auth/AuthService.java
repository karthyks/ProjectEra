package com.github.karthyks.project.era.network.auth;

import com.github.karthyks.project.era.proto.auth.AuthGrpc;
import com.github.karthyks.project.era.proto.auth.AuthRequest;
import com.github.karthyks.project.era.proto.auth.AuthResponse;
import io.grpc.stub.StreamObserver;

public class AuthService extends AuthGrpc.AuthImplBase {

  @Override
  public void authenticate(AuthRequest request,
                           StreamObserver<AuthResponse> responseObserver) {
    String password = request.getPassword();
    String token = JwtCreator.create(password);
    responseObserver.onNext(AuthResponse.newBuilder().setToken(token).build());
    responseObserver.onCompleted();
  }
}
