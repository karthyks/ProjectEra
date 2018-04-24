package com.github.karthyks.project.era.network.interceptors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.karthyks.project.era.network.Constant;
import com.github.karthyks.project.era.network.GrpcServer;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Grpc;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;

import java.io.UnsupportedEncodingException;

public class ServerTokenInterceptor implements ServerInterceptor {

  private static final ServerCall.Listener NOOP_LISTENER = new ServerCall.Listener() {
  };

  private final JWTVerifier verifier;

  public ServerTokenInterceptor(String secret) {
    Algorithm algorithm;
    JWTVerifier verifier;
    try {
      algorithm = Algorithm.HMAC256(secret);
      verifier = JWT.require(algorithm).withIssuer(Constant.ISSUER).build();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      verifier = JWT.require(Algorithm.none()).withIssuer(Constant.ISSUER).build();
    }
    this.verifier = verifier;
  }

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
    String token = headers.get(Constant.TOKEN_METADATA_KEY);
    if (token == null) {
      call.close(Status.UNAUTHENTICATED.withDescription("Token missing from Metadata"), headers);
      return NOOP_LISTENER;
    }
    Context ctx;
    try {
      DecodedJWT verified = verifier.verify(token);
      String address = call.getAttributes().get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR).toString();
      int port;
      try {
        address = address.substring(1);
        String add = address.split(":")[0];
        port = Integer.parseInt(address.split(":")[1]);
        address = add;
      } catch (Exception e) {
        System.out.println("Address parse error");
        call.close(Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e), headers);
        return NOOP_LISTENER;
      }
      int serverPort = -1;
      try {
        String portFromHeader = headers.get(Constant.SERVER_PORT_MD_KEY);
        serverPort = Integer.parseInt(portFromHeader);
      } catch (Exception e) {
        System.out.println("PORT ERROR " + e.getMessage());
      }
      ctx = Context.current().withValue(Constant.USER_TOKEN_CTX_KEY, verified.getToken())
          .withValue(Constant.REMOTE_ADDRESS, address)
          .withValue(Constant.REMOTE_PORT, port)
          .withValue(Constant.REMOTE_SERVER_ADDRESS, GrpcServer.PUBLIC_ADDRESS)
          .withValue(Constant.REMOTE_SERVER_PORT, serverPort);
    } catch (Exception e) {
      System.out.println("Verification failed - Unauthenticated");
      call.close(Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e), headers);
      return NOOP_LISTENER;
    }
    return Contexts.interceptCall(ctx, call, headers, next);
  }
}
