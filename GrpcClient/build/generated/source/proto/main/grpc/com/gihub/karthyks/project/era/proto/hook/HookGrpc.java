package com.gihub.karthyks.project.era.proto.hook;

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
    comments = "Source: hook.proto")
public final class HookGrpc {

  private HookGrpc() {}

  public static final String SERVICE_NAME = "hookproto.Hook";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getInitiateMethod()} instead. 
  public static final io.grpc.MethodDescriptor<com.gihub.karthyks.project.era.proto.hook.HookRequest,
      com.gihub.karthyks.project.era.proto.hook.HookResponse> METHOD_INITIATE = getInitiateMethodHelper();

  private static volatile io.grpc.MethodDescriptor<com.gihub.karthyks.project.era.proto.hook.HookRequest,
      com.gihub.karthyks.project.era.proto.hook.HookResponse> getInitiateMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.gihub.karthyks.project.era.proto.hook.HookRequest,
      com.gihub.karthyks.project.era.proto.hook.HookResponse> getInitiateMethod() {
    return getInitiateMethodHelper();
  }

  private static io.grpc.MethodDescriptor<com.gihub.karthyks.project.era.proto.hook.HookRequest,
      com.gihub.karthyks.project.era.proto.hook.HookResponse> getInitiateMethodHelper() {
    io.grpc.MethodDescriptor<com.gihub.karthyks.project.era.proto.hook.HookRequest, com.gihub.karthyks.project.era.proto.hook.HookResponse> getInitiateMethod;
    if ((getInitiateMethod = HookGrpc.getInitiateMethod) == null) {
      synchronized (HookGrpc.class) {
        if ((getInitiateMethod = HookGrpc.getInitiateMethod) == null) {
          HookGrpc.getInitiateMethod = getInitiateMethod = 
              io.grpc.MethodDescriptor.<com.gihub.karthyks.project.era.proto.hook.HookRequest, com.gihub.karthyks.project.era.proto.hook.HookResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "hookproto.Hook", "initiate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gihub.karthyks.project.era.proto.hook.HookRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.gihub.karthyks.project.era.proto.hook.HookResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new HookMethodDescriptorSupplier("initiate"))
                  .build();
          }
        }
     }
     return getInitiateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HookStub newStub(io.grpc.Channel channel) {
    return new HookStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HookBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new HookBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HookFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new HookFutureStub(channel);
  }

  /**
   */
  public static abstract class HookImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<com.gihub.karthyks.project.era.proto.hook.HookRequest> initiate(
        io.grpc.stub.StreamObserver<com.gihub.karthyks.project.era.proto.hook.HookResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getInitiateMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getInitiateMethodHelper(),
            asyncClientStreamingCall(
              new MethodHandlers<
                com.gihub.karthyks.project.era.proto.hook.HookRequest,
                com.gihub.karthyks.project.era.proto.hook.HookResponse>(
                  this, METHODID_INITIATE)))
          .build();
    }
  }

  /**
   */
  public static final class HookStub extends io.grpc.stub.AbstractStub<HookStub> {
    private HookStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HookStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HookStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HookStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.gihub.karthyks.project.era.proto.hook.HookRequest> initiate(
        io.grpc.stub.StreamObserver<com.gihub.karthyks.project.era.proto.hook.HookResponse> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getInitiateMethodHelper(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class HookBlockingStub extends io.grpc.stub.AbstractStub<HookBlockingStub> {
    private HookBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HookBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HookBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HookBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class HookFutureStub extends io.grpc.stub.AbstractStub<HookFutureStub> {
    private HookFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HookFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HookFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HookFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_INITIATE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final HookImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(HookImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INITIATE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.initiate(
              (io.grpc.stub.StreamObserver<com.gihub.karthyks.project.era.proto.hook.HookResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class HookBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HookBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.gihub.karthyks.project.era.proto.hook.HookProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Hook");
    }
  }

  private static final class HookFileDescriptorSupplier
      extends HookBaseDescriptorSupplier {
    HookFileDescriptorSupplier() {}
  }

  private static final class HookMethodDescriptorSupplier
      extends HookBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    HookMethodDescriptorSupplier(String methodName) {
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
      synchronized (HookGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HookFileDescriptorSupplier())
              .addMethod(getInitiateMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
