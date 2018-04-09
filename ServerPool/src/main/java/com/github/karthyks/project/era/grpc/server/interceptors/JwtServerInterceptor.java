package com.github.karthyks.project.era.grpc.server.interceptors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.karthyks.project.era.grpc.server.model.PeerInfo;
import com.github.karthyks.project.era.network.Constant;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Grpc;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;

import java.io.UnsupportedEncodingException;

import static com.github.karthyks.project.era.grpc.server.ServerPool.observersMap;

public class JwtServerInterceptor implements ServerInterceptor {

  private static final ServerCall.Listener NOOP_LISTENER = new ServerCall.Listener() {
  };

  private final JWTVerifier verifier;

  public JwtServerInterceptor(String secret) {
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
      String address = call.getAttributes().get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR).toString();
      try {
        address = address.substring(1);
        address = address.split(":")[0];
      } catch (Exception e) {
        System.out.println("Address parse error");
        call.close(Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e), headers);
        return NOOP_LISTENER;
      }
      PeerInfo peerInfo = new PeerInfo();
      peerInfo.address = address;
      if (observersMap.containsKey(peerInfo)) {
        call.close(Status.ALREADY_EXISTS.withDescription("Another hook detected from the same "
            + "address"), headers);
        return NOOP_LISTENER;
      }
      ctx = Context.current().withValue(Constant.USER_ID_CTX_KEY, verified.getToken())
          .withValue(Constant.JWT_CTX_KEY, jwt)
          .withValue(Constant.REMOTE_ADDRESS, address);
    } catch (Exception e) {
      System.out.println("Verification failed - Unauthenticated");
      call.close(Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e), headers);
      return NOOP_LISTENER;
    }
    return Contexts.interceptCall(ctx, call, headers, next);
  }
}
