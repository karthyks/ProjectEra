package com.github.karthyks.project.era.grpc.server.interceptors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.karthyks.project.era.grpc.server.Constant;
import com.github.karthyks.project.era.grpc.server.model.PeerInfo;
import com.github.karthyks.project.era.grpc.server.services.IPeerListener;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Grpc;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;

import java.io.UnsupportedEncodingException;

public class JwtServerInterceptor implements ServerInterceptor {

  private static final ServerCall.Listener NOOP_LISTENER = new ServerCall.Listener() {
  };

  private final JWTVerifier verifier;
  private IPeerListener listener;

  public JwtServerInterceptor(String secret, IPeerListener listener) {
    this.listener = listener;
    Algorithm algorithm;
    JWTVerifier verifier;
    try {
      algorithm = Algorithm.HMAC256(secret);
      verifier = JWT.require(algorithm).withIssuer("auth0").build();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      verifier = JWT.require(Algorithm.none()).withIssuer("auth0").build();
    }
    this.verifier = verifier;
  }

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
    String jwt = headers.get(Constant.JWT_METADATA_KEY);
    if (jwt == null) {
      call.close(Status.UNAUTHENTICATED.withDescription("Token missing from Metadata"), headers);
      return NOOP_LISTENER;
    }
    Context ctx;
    try {
      DecodedJWT verified = verifier.verify(jwt);
      ctx = Context.current().withValue(Constant.USER_ID_CTX_KEY, verified.getToken())
          .withValue(Constant.JWT_CTX_KEY, jwt);
      PeerInfo peerInfo = new PeerInfo();
      String address = call.getAttributes().get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR).toString();
      try {
        address = address.substring(1);
        address = address.split(":")[0];
      } catch (Exception e) {
        System.out.println("Address parse error");
        call.close(Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e), headers);
        return NOOP_LISTENER;
      }
      peerInfo.address = address;
      peerInfo.clientName = headers.get(Constant.CLIENT_ID_MD_KEY);
      listener.onConnected(peerInfo);
    } catch (Exception e) {
      System.out.println("Verification failed - Unauthenticated");
      call.close(Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e), headers);
      return NOOP_LISTENER;
    }
    return Contexts.interceptCall(ctx, call, headers, next);
  }
}
