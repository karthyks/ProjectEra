package com.github.karthyks.project.era.grpc.server.interceptors;

import com.github.karthyks.project.era.grpc.server.Constant;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

public class TraceIdClientInterceptor implements ClientInterceptor {

  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
                                                             CallOptions callOptions,
                                                             Channel next) {
    return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method,
        callOptions)) {
      @Override
      public void start(Listener<RespT> responseListener, Metadata headers) {
        if (Constant.TRACE_ID_CTX_KEY.get() != null) {
          headers.put(Constant.TRACE_ID_METADATA_KEY, Constant.TRACE_ID_CTX_KEY.get());
        }
        super.start(responseListener, headers);
      }
    };
  }
}
