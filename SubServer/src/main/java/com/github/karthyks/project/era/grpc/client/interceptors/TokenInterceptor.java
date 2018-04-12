package com.github.karthyks.project.era.grpc.client.interceptors;


import com.github.karthyks.project.era.network.Constant;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;

public class TokenInterceptor implements ServerInterceptor {
  private static final ServerCall.Listener NOOP_LISTENER = new ServerCall.Listener() {
  };

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT>
  interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
                ServerCallHandler<ReqT, RespT> next) {
    String token = headers.get(Constant.TOKEN_METADATA_KEY);
    if (token == null || token.isEmpty()) {
      call.close(Status.UNAUTHENTICATED.withDescription("Token missing from Metadata"), headers);
      return NOOP_LISTENER;
    }

    /*
      TODO Verify Token and pass the verified token.
     */
    Context ctx = Context.current().withValue(Constant.USER_ID_CTX_KEY, token);
    return Contexts.interceptCall(ctx, call, headers, next);
  }
}
