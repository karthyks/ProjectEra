package com.gihub.karthyks.project.era.proto.connect;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.11.0)",
    comments = "Source: connect.proto")
public final class ConnectGrpc {

  private ConnectGrpc() {}

  public static final String SERVICE_NAME = "connectproto.Connect";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getInitiateMethod()} instead. 
  public static final io.grpc.MethodDescriptor<com.gihub.karthyks.project.era.proto.connect.ConnectRequest,
      com.gihub.karthyks.project.era.proto.connect.ConnectResponse> METHOD_INITIATE = getInitiateMethodHelper();

  private static volatile io.grpc.MethodDescriptor<com.gihub.karthyks.project.era.proto.connect.ConnectRequest,
      com.gihub.karthyks.project.era.proto.connect.ConnectResponse> getInitiateMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.gihub.karthyks.project.era.proto.connect.ConnectRequest,
      com.gihub.karthyks.project.era.proto.connect.ConnectResponse> getInitiateMethod() {
    return getInitiateMethodHelper();
  }

  private static io.grpc.MethodDescriptor<com.gihub.karthyks.project.era.proto.connect.ConnectRequest,
      com.gihub.karthyks.project.era.proto.connect.ConnectResponse> getInitiateMethodHelper() {
    io.grpc.MethodDescriptor<com.gihub.karthyks.project.era.proto.connect.ConnectRequest, com.gihub.karthyks.project.era.proto.connect.ConnectResponse> getInitiateMethod;
    if ((getInitiateMethod = ConnectGrpc.getInitiateMethod) == null) {
      synchronized (ConnectGrpc.class) {
        if ((getInitiateMethod = ConnectGrpc.getInitiateMethod) == null) {
          ConnectGrpc.getInitiateMethod = getInitiateMethod = 
              io.grpc.MethodDescriptor.<com.gihub.karthyks.project.era.proto.connect.ConnectRequest, com.gihub.karthyks.project.era.proto.connect.ConnectResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "connectproto.Connect", "initiate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gihub.karthyks.project.era.proto.connect.ConnectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gihub.karthyks.project.era.proto.connect.ConnectResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ConnectMethodDescriptorSupplier("initiate"))
                  .build();
          }
        }
     }
     return getInitiateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ConnectStub newStub(io.grpc.Channel channel) {
    return new ConnectStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ConnectBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ConnectBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ConnectFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ConnectFutureStub(channel);
  }

  /**
   */
  public static abstract class ConnectImplBase implements io.grpc.BindableService {

    /**
     */
    public void initiate(com.gihub.karthyks.project.era.proto.connect.ConnectRequest request,
        io.grpc.stub.StreamObserver<com.gihub.karthyks.project.era.proto.connect.ConnectResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getInitiateMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getInitiateMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                com.gihub.karthyks.project.era.proto.connect.ConnectRequest,
                com.gihub.karthyks.project.era.proto.connect.ConnectResponse>(
                  this, METHODID_INITIATE)))
          .build();
    }
  }

  /**
   */
  public static final class ConnectStub extends io.grpc.stub.AbstractStub<ConnectStub> {
    private ConnectStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ConnectStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConnectStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ConnectStub(channel, callOptions);
    }

    /**
     */
    public void initiate(com.gihub.karthyks.project.era.proto.connect.ConnectRequest request,
        io.grpc.stub.StreamObserver<com.gihub.karthyks.project.era.proto.connect.ConnectResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getInitiateMethodHelper(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ConnectBlockingStub extends io.grpc.stub.AbstractStub<ConnectBlockingStub> {
    private ConnectBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ConnectBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConnectBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ConnectBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.gihub.karthyks.project.era.proto.connect.ConnectResponse initiate(com.gihub.karthyks.project.era.proto.connect.ConnectRequest request) {
      return blockingUnaryCall(
          getChannel(), getInitiateMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ConnectFutureStub extends io.grpc.stub.AbstractStub<ConnectFutureStub> {
    private ConnectFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ConnectFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConnectFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ConnectFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.gihub.karthyks.project.era.proto.connect.ConnectResponse> initiate(
        com.gihub.karthyks.project.era.proto.connect.ConnectRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getInitiateMethodHelper(), getCallOptions()), request);
    }
  }

  private static final int METHODID_INITIATE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ConnectImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ConnectImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INITIATE:
          serviceImpl.initiate((com.gihub.karthyks.project.era.proto.connect.ConnectRequest) request,
              (io.grpc.stub.StreamObserver<com.gihub.karthyks.project.era.proto.connect.ConnectResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ConnectBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ConnectBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.gihub.karthyks.project.era.proto.connect.ConnectProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Connect");
    }
  }

  private static final class ConnectFileDescriptorSupplier
      extends ConnectBaseDescriptorSupplier {
    ConnectFileDescriptorSupplier() {}
  }

  private static final class ConnectMethodDescriptorSupplier
      extends ConnectBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ConnectMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ConnectGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ConnectFileDescriptorSupplier())
              .addMethod(getInitiateMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
