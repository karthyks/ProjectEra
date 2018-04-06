package com.github.karthyks.project.era.grpc.server.interceptors;

import com.github.karthyks.project.era.grpc.server.Constant;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Grpc;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

public class TraceIdServerInterceptor implements ServerInterceptor {
  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
    String traceId = headers.get(Constant.TRACE_ID_METADATA_KEY);
    String address = call.getAttributes().get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR).toString();
    System.out.println("Call intercepted " + address);
    Context ctx = Context.current().withValue(Constant.TRACE_ID_CTX_KEY, traceId);
    return Contexts.interceptCall(ctx, call, headers, next);
  }
}
