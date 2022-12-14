package sentinel.session.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.39.0)",
    comments = "Source: sentinel/session/v1/msg.proto")
public final class MsgServiceGrpc {

  private MsgServiceGrpc() {}

  public static final String SERVICE_NAME = "sentinel.session.v1.MsgService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<sentinel.session.v1.Msg.MsgStartRequest,
      sentinel.session.v1.Msg.MsgStartResponse> getMsgStartMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MsgStart",
      requestType = sentinel.session.v1.Msg.MsgStartRequest.class,
      responseType = sentinel.session.v1.Msg.MsgStartResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<sentinel.session.v1.Msg.MsgStartRequest,
      sentinel.session.v1.Msg.MsgStartResponse> getMsgStartMethod() {
    io.grpc.MethodDescriptor<sentinel.session.v1.Msg.MsgStartRequest, sentinel.session.v1.Msg.MsgStartResponse> getMsgStartMethod;
    if ((getMsgStartMethod = MsgServiceGrpc.getMsgStartMethod) == null) {
      synchronized (MsgServiceGrpc.class) {
        if ((getMsgStartMethod = MsgServiceGrpc.getMsgStartMethod) == null) {
          MsgServiceGrpc.getMsgStartMethod = getMsgStartMethod =
              io.grpc.MethodDescriptor.<sentinel.session.v1.Msg.MsgStartRequest, sentinel.session.v1.Msg.MsgStartResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "MsgStart"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.session.v1.Msg.MsgStartRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.session.v1.Msg.MsgStartResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgServiceMethodDescriptorSupplier("MsgStart"))
              .build();
        }
      }
    }
    return getMsgStartMethod;
  }

  private static volatile io.grpc.MethodDescriptor<sentinel.session.v1.Msg.MsgUpdateRequest,
      sentinel.session.v1.Msg.MsgUpdateResponse> getMsgUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MsgUpdate",
      requestType = sentinel.session.v1.Msg.MsgUpdateRequest.class,
      responseType = sentinel.session.v1.Msg.MsgUpdateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<sentinel.session.v1.Msg.MsgUpdateRequest,
      sentinel.session.v1.Msg.MsgUpdateResponse> getMsgUpdateMethod() {
    io.grpc.MethodDescriptor<sentinel.session.v1.Msg.MsgUpdateRequest, sentinel.session.v1.Msg.MsgUpdateResponse> getMsgUpdateMethod;
    if ((getMsgUpdateMethod = MsgServiceGrpc.getMsgUpdateMethod) == null) {
      synchronized (MsgServiceGrpc.class) {
        if ((getMsgUpdateMethod = MsgServiceGrpc.getMsgUpdateMethod) == null) {
          MsgServiceGrpc.getMsgUpdateMethod = getMsgUpdateMethod =
              io.grpc.MethodDescriptor.<sentinel.session.v1.Msg.MsgUpdateRequest, sentinel.session.v1.Msg.MsgUpdateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "MsgUpdate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.session.v1.Msg.MsgUpdateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.session.v1.Msg.MsgUpdateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgServiceMethodDescriptorSupplier("MsgUpdate"))
              .build();
        }
      }
    }
    return getMsgUpdateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<sentinel.session.v1.Msg.MsgEndRequest,
      sentinel.session.v1.Msg.MsgEndResponse> getMsgEndMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MsgEnd",
      requestType = sentinel.session.v1.Msg.MsgEndRequest.class,
      responseType = sentinel.session.v1.Msg.MsgEndResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<sentinel.session.v1.Msg.MsgEndRequest,
      sentinel.session.v1.Msg.MsgEndResponse> getMsgEndMethod() {
    io.grpc.MethodDescriptor<sentinel.session.v1.Msg.MsgEndRequest, sentinel.session.v1.Msg.MsgEndResponse> getMsgEndMethod;
    if ((getMsgEndMethod = MsgServiceGrpc.getMsgEndMethod) == null) {
      synchronized (MsgServiceGrpc.class) {
        if ((getMsgEndMethod = MsgServiceGrpc.getMsgEndMethod) == null) {
          MsgServiceGrpc.getMsgEndMethod = getMsgEndMethod =
              io.grpc.MethodDescriptor.<sentinel.session.v1.Msg.MsgEndRequest, sentinel.session.v1.Msg.MsgEndResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "MsgEnd"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.session.v1.Msg.MsgEndRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.session.v1.Msg.MsgEndResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MsgServiceMethodDescriptorSupplier("MsgEnd"))
              .build();
        }
      }
    }
    return getMsgEndMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MsgServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MsgServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MsgServiceStub>() {
        @java.lang.Override
        public MsgServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MsgServiceStub(channel, callOptions);
        }
      };
    return MsgServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MsgServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MsgServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MsgServiceBlockingStub>() {
        @java.lang.Override
        public MsgServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MsgServiceBlockingStub(channel, callOptions);
        }
      };
    return MsgServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MsgServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MsgServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MsgServiceFutureStub>() {
        @java.lang.Override
        public MsgServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MsgServiceFutureStub(channel, callOptions);
        }
      };
    return MsgServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class MsgServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void msgStart(sentinel.session.v1.Msg.MsgStartRequest request,
        io.grpc.stub.StreamObserver<sentinel.session.v1.Msg.MsgStartResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getMsgStartMethod(), responseObserver);
    }

    /**
     */
    public void msgUpdate(sentinel.session.v1.Msg.MsgUpdateRequest request,
        io.grpc.stub.StreamObserver<sentinel.session.v1.Msg.MsgUpdateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getMsgUpdateMethod(), responseObserver);
    }

    /**
     */
    public void msgEnd(sentinel.session.v1.Msg.MsgEndRequest request,
        io.grpc.stub.StreamObserver<sentinel.session.v1.Msg.MsgEndResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getMsgEndMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getMsgStartMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                sentinel.session.v1.Msg.MsgStartRequest,
                sentinel.session.v1.Msg.MsgStartResponse>(
                  this, METHODID_MSG_START)))
          .addMethod(
            getMsgUpdateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                sentinel.session.v1.Msg.MsgUpdateRequest,
                sentinel.session.v1.Msg.MsgUpdateResponse>(
                  this, METHODID_MSG_UPDATE)))
          .addMethod(
            getMsgEndMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                sentinel.session.v1.Msg.MsgEndRequest,
                sentinel.session.v1.Msg.MsgEndResponse>(
                  this, METHODID_MSG_END)))
          .build();
    }
  }

  /**
   */
  public static final class MsgServiceStub extends io.grpc.stub.AbstractAsyncStub<MsgServiceStub> {
    private MsgServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MsgServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MsgServiceStub(channel, callOptions);
    }

    /**
     */
    public void msgStart(sentinel.session.v1.Msg.MsgStartRequest request,
        io.grpc.stub.StreamObserver<sentinel.session.v1.Msg.MsgStartResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getMsgStartMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void msgUpdate(sentinel.session.v1.Msg.MsgUpdateRequest request,
        io.grpc.stub.StreamObserver<sentinel.session.v1.Msg.MsgUpdateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getMsgUpdateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void msgEnd(sentinel.session.v1.Msg.MsgEndRequest request,
        io.grpc.stub.StreamObserver<sentinel.session.v1.Msg.MsgEndResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getMsgEndMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MsgServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<MsgServiceBlockingStub> {
    private MsgServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MsgServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MsgServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public sentinel.session.v1.Msg.MsgStartResponse msgStart(sentinel.session.v1.Msg.MsgStartRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getMsgStartMethod(), getCallOptions(), request);
    }

    /**
     */
    public sentinel.session.v1.Msg.MsgUpdateResponse msgUpdate(sentinel.session.v1.Msg.MsgUpdateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getMsgUpdateMethod(), getCallOptions(), request);
    }

    /**
     */
    public sentinel.session.v1.Msg.MsgEndResponse msgEnd(sentinel.session.v1.Msg.MsgEndRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getMsgEndMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MsgServiceFutureStub extends io.grpc.stub.AbstractFutureStub<MsgServiceFutureStub> {
    private MsgServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MsgServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MsgServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sentinel.session.v1.Msg.MsgStartResponse> msgStart(
        sentinel.session.v1.Msg.MsgStartRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getMsgStartMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sentinel.session.v1.Msg.MsgUpdateResponse> msgUpdate(
        sentinel.session.v1.Msg.MsgUpdateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getMsgUpdateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sentinel.session.v1.Msg.MsgEndResponse> msgEnd(
        sentinel.session.v1.Msg.MsgEndRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getMsgEndMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_MSG_START = 0;
  private static final int METHODID_MSG_UPDATE = 1;
  private static final int METHODID_MSG_END = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MsgServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MsgServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_MSG_START:
          serviceImpl.msgStart((sentinel.session.v1.Msg.MsgStartRequest) request,
              (io.grpc.stub.StreamObserver<sentinel.session.v1.Msg.MsgStartResponse>) responseObserver);
          break;
        case METHODID_MSG_UPDATE:
          serviceImpl.msgUpdate((sentinel.session.v1.Msg.MsgUpdateRequest) request,
              (io.grpc.stub.StreamObserver<sentinel.session.v1.Msg.MsgUpdateResponse>) responseObserver);
          break;
        case METHODID_MSG_END:
          serviceImpl.msgEnd((sentinel.session.v1.Msg.MsgEndRequest) request,
              (io.grpc.stub.StreamObserver<sentinel.session.v1.Msg.MsgEndResponse>) responseObserver);
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

  private static abstract class MsgServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MsgServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return sentinel.session.v1.Msg.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MsgService");
    }
  }

  private static final class MsgServiceFileDescriptorSupplier
      extends MsgServiceBaseDescriptorSupplier {
    MsgServiceFileDescriptorSupplier() {}
  }

  private static final class MsgServiceMethodDescriptorSupplier
      extends MsgServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MsgServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (MsgServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MsgServiceFileDescriptorSupplier())
              .addMethod(getMsgStartMethod())
              .addMethod(getMsgUpdateMethod())
              .addMethod(getMsgEndMethod())
              .build();
        }
      }
    }
    return result;
  }
}
