package sentinel.subscription.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.39.0)",
    comments = "Source: sentinel/subscription/v1/querier.proto")
public final class QueryServiceGrpc {

  private QueryServiceGrpc() {}

  public static final String SERVICE_NAME = "sentinel.subscription.v1.QueryService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QuerySubscriptionsRequest,
      sentinel.subscription.v1.Querier.QuerySubscriptionsResponse> getQuerySubscriptionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QuerySubscriptions",
      requestType = sentinel.subscription.v1.Querier.QuerySubscriptionsRequest.class,
      responseType = sentinel.subscription.v1.Querier.QuerySubscriptionsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QuerySubscriptionsRequest,
      sentinel.subscription.v1.Querier.QuerySubscriptionsResponse> getQuerySubscriptionsMethod() {
    io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QuerySubscriptionsRequest, sentinel.subscription.v1.Querier.QuerySubscriptionsResponse> getQuerySubscriptionsMethod;
    if ((getQuerySubscriptionsMethod = QueryServiceGrpc.getQuerySubscriptionsMethod) == null) {
      synchronized (QueryServiceGrpc.class) {
        if ((getQuerySubscriptionsMethod = QueryServiceGrpc.getQuerySubscriptionsMethod) == null) {
          QueryServiceGrpc.getQuerySubscriptionsMethod = getQuerySubscriptionsMethod =
              io.grpc.MethodDescriptor.<sentinel.subscription.v1.Querier.QuerySubscriptionsRequest, sentinel.subscription.v1.Querier.QuerySubscriptionsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QuerySubscriptions"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.subscription.v1.Querier.QuerySubscriptionsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.subscription.v1.Querier.QuerySubscriptionsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryServiceMethodDescriptorSupplier("QuerySubscriptions"))
              .build();
        }
      }
    }
    return getQuerySubscriptionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeRequest,
      sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeResponse> getQuerySubscriptionsForNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QuerySubscriptionsForNode",
      requestType = sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeRequest.class,
      responseType = sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeRequest,
      sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeResponse> getQuerySubscriptionsForNodeMethod() {
    io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeRequest, sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeResponse> getQuerySubscriptionsForNodeMethod;
    if ((getQuerySubscriptionsForNodeMethod = QueryServiceGrpc.getQuerySubscriptionsForNodeMethod) == null) {
      synchronized (QueryServiceGrpc.class) {
        if ((getQuerySubscriptionsForNodeMethod = QueryServiceGrpc.getQuerySubscriptionsForNodeMethod) == null) {
          QueryServiceGrpc.getQuerySubscriptionsForNodeMethod = getQuerySubscriptionsForNodeMethod =
              io.grpc.MethodDescriptor.<sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeRequest, sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QuerySubscriptionsForNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryServiceMethodDescriptorSupplier("QuerySubscriptionsForNode"))
              .build();
        }
      }
    }
    return getQuerySubscriptionsForNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanRequest,
      sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanResponse> getQuerySubscriptionsForPlanMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QuerySubscriptionsForPlan",
      requestType = sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanRequest.class,
      responseType = sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanRequest,
      sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanResponse> getQuerySubscriptionsForPlanMethod() {
    io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanRequest, sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanResponse> getQuerySubscriptionsForPlanMethod;
    if ((getQuerySubscriptionsForPlanMethod = QueryServiceGrpc.getQuerySubscriptionsForPlanMethod) == null) {
      synchronized (QueryServiceGrpc.class) {
        if ((getQuerySubscriptionsForPlanMethod = QueryServiceGrpc.getQuerySubscriptionsForPlanMethod) == null) {
          QueryServiceGrpc.getQuerySubscriptionsForPlanMethod = getQuerySubscriptionsForPlanMethod =
              io.grpc.MethodDescriptor.<sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanRequest, sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QuerySubscriptionsForPlan"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryServiceMethodDescriptorSupplier("QuerySubscriptionsForPlan"))
              .build();
        }
      }
    }
    return getQuerySubscriptionsForPlanMethod;
  }

  private static volatile io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressRequest,
      sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressResponse> getQuerySubscriptionsForAddressMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QuerySubscriptionsForAddress",
      requestType = sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressRequest.class,
      responseType = sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressRequest,
      sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressResponse> getQuerySubscriptionsForAddressMethod() {
    io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressRequest, sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressResponse> getQuerySubscriptionsForAddressMethod;
    if ((getQuerySubscriptionsForAddressMethod = QueryServiceGrpc.getQuerySubscriptionsForAddressMethod) == null) {
      synchronized (QueryServiceGrpc.class) {
        if ((getQuerySubscriptionsForAddressMethod = QueryServiceGrpc.getQuerySubscriptionsForAddressMethod) == null) {
          QueryServiceGrpc.getQuerySubscriptionsForAddressMethod = getQuerySubscriptionsForAddressMethod =
              io.grpc.MethodDescriptor.<sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressRequest, sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QuerySubscriptionsForAddress"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryServiceMethodDescriptorSupplier("QuerySubscriptionsForAddress"))
              .build();
        }
      }
    }
    return getQuerySubscriptionsForAddressMethod;
  }

  private static volatile io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QuerySubscriptionRequest,
      sentinel.subscription.v1.Querier.QuerySubscriptionResponse> getQuerySubscriptionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QuerySubscription",
      requestType = sentinel.subscription.v1.Querier.QuerySubscriptionRequest.class,
      responseType = sentinel.subscription.v1.Querier.QuerySubscriptionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QuerySubscriptionRequest,
      sentinel.subscription.v1.Querier.QuerySubscriptionResponse> getQuerySubscriptionMethod() {
    io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QuerySubscriptionRequest, sentinel.subscription.v1.Querier.QuerySubscriptionResponse> getQuerySubscriptionMethod;
    if ((getQuerySubscriptionMethod = QueryServiceGrpc.getQuerySubscriptionMethod) == null) {
      synchronized (QueryServiceGrpc.class) {
        if ((getQuerySubscriptionMethod = QueryServiceGrpc.getQuerySubscriptionMethod) == null) {
          QueryServiceGrpc.getQuerySubscriptionMethod = getQuerySubscriptionMethod =
              io.grpc.MethodDescriptor.<sentinel.subscription.v1.Querier.QuerySubscriptionRequest, sentinel.subscription.v1.Querier.QuerySubscriptionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QuerySubscription"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.subscription.v1.Querier.QuerySubscriptionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.subscription.v1.Querier.QuerySubscriptionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryServiceMethodDescriptorSupplier("QuerySubscription"))
              .build();
        }
      }
    }
    return getQuerySubscriptionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QueryQuotaRequest,
      sentinel.subscription.v1.Querier.QueryQuotaResponse> getQueryQuotaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryQuota",
      requestType = sentinel.subscription.v1.Querier.QueryQuotaRequest.class,
      responseType = sentinel.subscription.v1.Querier.QueryQuotaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QueryQuotaRequest,
      sentinel.subscription.v1.Querier.QueryQuotaResponse> getQueryQuotaMethod() {
    io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QueryQuotaRequest, sentinel.subscription.v1.Querier.QueryQuotaResponse> getQueryQuotaMethod;
    if ((getQueryQuotaMethod = QueryServiceGrpc.getQueryQuotaMethod) == null) {
      synchronized (QueryServiceGrpc.class) {
        if ((getQueryQuotaMethod = QueryServiceGrpc.getQueryQuotaMethod) == null) {
          QueryServiceGrpc.getQueryQuotaMethod = getQueryQuotaMethod =
              io.grpc.MethodDescriptor.<sentinel.subscription.v1.Querier.QueryQuotaRequest, sentinel.subscription.v1.Querier.QueryQuotaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryQuota"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.subscription.v1.Querier.QueryQuotaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.subscription.v1.Querier.QueryQuotaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryServiceMethodDescriptorSupplier("QueryQuota"))
              .build();
        }
      }
    }
    return getQueryQuotaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QueryQuotasRequest,
      sentinel.subscription.v1.Querier.QueryQuotasResponse> getQueryQuotasMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryQuotas",
      requestType = sentinel.subscription.v1.Querier.QueryQuotasRequest.class,
      responseType = sentinel.subscription.v1.Querier.QueryQuotasResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QueryQuotasRequest,
      sentinel.subscription.v1.Querier.QueryQuotasResponse> getQueryQuotasMethod() {
    io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QueryQuotasRequest, sentinel.subscription.v1.Querier.QueryQuotasResponse> getQueryQuotasMethod;
    if ((getQueryQuotasMethod = QueryServiceGrpc.getQueryQuotasMethod) == null) {
      synchronized (QueryServiceGrpc.class) {
        if ((getQueryQuotasMethod = QueryServiceGrpc.getQueryQuotasMethod) == null) {
          QueryServiceGrpc.getQueryQuotasMethod = getQueryQuotasMethod =
              io.grpc.MethodDescriptor.<sentinel.subscription.v1.Querier.QueryQuotasRequest, sentinel.subscription.v1.Querier.QueryQuotasResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryQuotas"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.subscription.v1.Querier.QueryQuotasRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.subscription.v1.Querier.QueryQuotasResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryServiceMethodDescriptorSupplier("QueryQuotas"))
              .build();
        }
      }
    }
    return getQueryQuotasMethod;
  }

  private static volatile io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QueryParamsRequest,
      sentinel.subscription.v1.Querier.QueryParamsResponse> getQueryParamsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryParams",
      requestType = sentinel.subscription.v1.Querier.QueryParamsRequest.class,
      responseType = sentinel.subscription.v1.Querier.QueryParamsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QueryParamsRequest,
      sentinel.subscription.v1.Querier.QueryParamsResponse> getQueryParamsMethod() {
    io.grpc.MethodDescriptor<sentinel.subscription.v1.Querier.QueryParamsRequest, sentinel.subscription.v1.Querier.QueryParamsResponse> getQueryParamsMethod;
    if ((getQueryParamsMethod = QueryServiceGrpc.getQueryParamsMethod) == null) {
      synchronized (QueryServiceGrpc.class) {
        if ((getQueryParamsMethod = QueryServiceGrpc.getQueryParamsMethod) == null) {
          QueryServiceGrpc.getQueryParamsMethod = getQueryParamsMethod =
              io.grpc.MethodDescriptor.<sentinel.subscription.v1.Querier.QueryParamsRequest, sentinel.subscription.v1.Querier.QueryParamsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryParams"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.subscription.v1.Querier.QueryParamsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sentinel.subscription.v1.Querier.QueryParamsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QueryServiceMethodDescriptorSupplier("QueryParams"))
              .build();
        }
      }
    }
    return getQueryParamsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static QueryServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QueryServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QueryServiceStub>() {
        @java.lang.Override
        public QueryServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QueryServiceStub(channel, callOptions);
        }
      };
    return QueryServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static QueryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QueryServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QueryServiceBlockingStub>() {
        @java.lang.Override
        public QueryServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QueryServiceBlockingStub(channel, callOptions);
        }
      };
    return QueryServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static QueryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QueryServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QueryServiceFutureStub>() {
        @java.lang.Override
        public QueryServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QueryServiceFutureStub(channel, callOptions);
        }
      };
    return QueryServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class QueryServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void querySubscriptions(sentinel.subscription.v1.Querier.QuerySubscriptionsRequest request,
        io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QuerySubscriptionsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQuerySubscriptionsMethod(), responseObserver);
    }

    /**
     */
    public void querySubscriptionsForNode(sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeRequest request,
        io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQuerySubscriptionsForNodeMethod(), responseObserver);
    }

    /**
     */
    public void querySubscriptionsForPlan(sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanRequest request,
        io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQuerySubscriptionsForPlanMethod(), responseObserver);
    }

    /**
     */
    public void querySubscriptionsForAddress(sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressRequest request,
        io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQuerySubscriptionsForAddressMethod(), responseObserver);
    }

    /**
     */
    public void querySubscription(sentinel.subscription.v1.Querier.QuerySubscriptionRequest request,
        io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QuerySubscriptionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQuerySubscriptionMethod(), responseObserver);
    }

    /**
     */
    public void queryQuota(sentinel.subscription.v1.Querier.QueryQuotaRequest request,
        io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QueryQuotaResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryQuotaMethod(), responseObserver);
    }

    /**
     */
    public void queryQuotas(sentinel.subscription.v1.Querier.QueryQuotasRequest request,
        io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QueryQuotasResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryQuotasMethod(), responseObserver);
    }

    /**
     */
    public void queryParams(sentinel.subscription.v1.Querier.QueryParamsRequest request,
        io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QueryParamsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryParamsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getQuerySubscriptionsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                sentinel.subscription.v1.Querier.QuerySubscriptionsRequest,
                sentinel.subscription.v1.Querier.QuerySubscriptionsResponse>(
                  this, METHODID_QUERY_SUBSCRIPTIONS)))
          .addMethod(
            getQuerySubscriptionsForNodeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeRequest,
                sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeResponse>(
                  this, METHODID_QUERY_SUBSCRIPTIONS_FOR_NODE)))
          .addMethod(
            getQuerySubscriptionsForPlanMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanRequest,
                sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanResponse>(
                  this, METHODID_QUERY_SUBSCRIPTIONS_FOR_PLAN)))
          .addMethod(
            getQuerySubscriptionsForAddressMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressRequest,
                sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressResponse>(
                  this, METHODID_QUERY_SUBSCRIPTIONS_FOR_ADDRESS)))
          .addMethod(
            getQuerySubscriptionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                sentinel.subscription.v1.Querier.QuerySubscriptionRequest,
                sentinel.subscription.v1.Querier.QuerySubscriptionResponse>(
                  this, METHODID_QUERY_SUBSCRIPTION)))
          .addMethod(
            getQueryQuotaMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                sentinel.subscription.v1.Querier.QueryQuotaRequest,
                sentinel.subscription.v1.Querier.QueryQuotaResponse>(
                  this, METHODID_QUERY_QUOTA)))
          .addMethod(
            getQueryQuotasMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                sentinel.subscription.v1.Querier.QueryQuotasRequest,
                sentinel.subscription.v1.Querier.QueryQuotasResponse>(
                  this, METHODID_QUERY_QUOTAS)))
          .addMethod(
            getQueryParamsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                sentinel.subscription.v1.Querier.QueryParamsRequest,
                sentinel.subscription.v1.Querier.QueryParamsResponse>(
                  this, METHODID_QUERY_PARAMS)))
          .build();
    }
  }

  /**
   */
  public static final class QueryServiceStub extends io.grpc.stub.AbstractAsyncStub<QueryServiceStub> {
    private QueryServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueryServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QueryServiceStub(channel, callOptions);
    }

    /**
     */
    public void querySubscriptions(sentinel.subscription.v1.Querier.QuerySubscriptionsRequest request,
        io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QuerySubscriptionsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQuerySubscriptionsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void querySubscriptionsForNode(sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeRequest request,
        io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQuerySubscriptionsForNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void querySubscriptionsForPlan(sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanRequest request,
        io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQuerySubscriptionsForPlanMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void querySubscriptionsForAddress(sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressRequest request,
        io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQuerySubscriptionsForAddressMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void querySubscription(sentinel.subscription.v1.Querier.QuerySubscriptionRequest request,
        io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QuerySubscriptionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQuerySubscriptionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void queryQuota(sentinel.subscription.v1.Querier.QueryQuotaRequest request,
        io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QueryQuotaResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryQuotaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void queryQuotas(sentinel.subscription.v1.Querier.QueryQuotasRequest request,
        io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QueryQuotasResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryQuotasMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void queryParams(sentinel.subscription.v1.Querier.QueryParamsRequest request,
        io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QueryParamsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryParamsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class QueryServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<QueryServiceBlockingStub> {
    private QueryServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueryServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QueryServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public sentinel.subscription.v1.Querier.QuerySubscriptionsResponse querySubscriptions(sentinel.subscription.v1.Querier.QuerySubscriptionsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQuerySubscriptionsMethod(), getCallOptions(), request);
    }

    /**
     */
    public sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeResponse querySubscriptionsForNode(sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQuerySubscriptionsForNodeMethod(), getCallOptions(), request);
    }

    /**
     */
    public sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanResponse querySubscriptionsForPlan(sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQuerySubscriptionsForPlanMethod(), getCallOptions(), request);
    }

    /**
     */
    public sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressResponse querySubscriptionsForAddress(sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQuerySubscriptionsForAddressMethod(), getCallOptions(), request);
    }

    /**
     */
    public sentinel.subscription.v1.Querier.QuerySubscriptionResponse querySubscription(sentinel.subscription.v1.Querier.QuerySubscriptionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQuerySubscriptionMethod(), getCallOptions(), request);
    }

    /**
     */
    public sentinel.subscription.v1.Querier.QueryQuotaResponse queryQuota(sentinel.subscription.v1.Querier.QueryQuotaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryQuotaMethod(), getCallOptions(), request);
    }

    /**
     */
    public sentinel.subscription.v1.Querier.QueryQuotasResponse queryQuotas(sentinel.subscription.v1.Querier.QueryQuotasRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryQuotasMethod(), getCallOptions(), request);
    }

    /**
     */
    public sentinel.subscription.v1.Querier.QueryParamsResponse queryParams(sentinel.subscription.v1.Querier.QueryParamsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryParamsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class QueryServiceFutureStub extends io.grpc.stub.AbstractFutureStub<QueryServiceFutureStub> {
    private QueryServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QueryServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QueryServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sentinel.subscription.v1.Querier.QuerySubscriptionsResponse> querySubscriptions(
        sentinel.subscription.v1.Querier.QuerySubscriptionsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQuerySubscriptionsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeResponse> querySubscriptionsForNode(
        sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQuerySubscriptionsForNodeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanResponse> querySubscriptionsForPlan(
        sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQuerySubscriptionsForPlanMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressResponse> querySubscriptionsForAddress(
        sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQuerySubscriptionsForAddressMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sentinel.subscription.v1.Querier.QuerySubscriptionResponse> querySubscription(
        sentinel.subscription.v1.Querier.QuerySubscriptionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQuerySubscriptionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sentinel.subscription.v1.Querier.QueryQuotaResponse> queryQuota(
        sentinel.subscription.v1.Querier.QueryQuotaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryQuotaMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sentinel.subscription.v1.Querier.QueryQuotasResponse> queryQuotas(
        sentinel.subscription.v1.Querier.QueryQuotasRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryQuotasMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sentinel.subscription.v1.Querier.QueryParamsResponse> queryParams(
        sentinel.subscription.v1.Querier.QueryParamsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryParamsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_QUERY_SUBSCRIPTIONS = 0;
  private static final int METHODID_QUERY_SUBSCRIPTIONS_FOR_NODE = 1;
  private static final int METHODID_QUERY_SUBSCRIPTIONS_FOR_PLAN = 2;
  private static final int METHODID_QUERY_SUBSCRIPTIONS_FOR_ADDRESS = 3;
  private static final int METHODID_QUERY_SUBSCRIPTION = 4;
  private static final int METHODID_QUERY_QUOTA = 5;
  private static final int METHODID_QUERY_QUOTAS = 6;
  private static final int METHODID_QUERY_PARAMS = 7;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final QueryServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(QueryServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_QUERY_SUBSCRIPTIONS:
          serviceImpl.querySubscriptions((sentinel.subscription.v1.Querier.QuerySubscriptionsRequest) request,
              (io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QuerySubscriptionsResponse>) responseObserver);
          break;
        case METHODID_QUERY_SUBSCRIPTIONS_FOR_NODE:
          serviceImpl.querySubscriptionsForNode((sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeRequest) request,
              (io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QuerySubscriptionsForNodeResponse>) responseObserver);
          break;
        case METHODID_QUERY_SUBSCRIPTIONS_FOR_PLAN:
          serviceImpl.querySubscriptionsForPlan((sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanRequest) request,
              (io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QuerySubscriptionsForPlanResponse>) responseObserver);
          break;
        case METHODID_QUERY_SUBSCRIPTIONS_FOR_ADDRESS:
          serviceImpl.querySubscriptionsForAddress((sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressRequest) request,
              (io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QuerySubscriptionsForAddressResponse>) responseObserver);
          break;
        case METHODID_QUERY_SUBSCRIPTION:
          serviceImpl.querySubscription((sentinel.subscription.v1.Querier.QuerySubscriptionRequest) request,
              (io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QuerySubscriptionResponse>) responseObserver);
          break;
        case METHODID_QUERY_QUOTA:
          serviceImpl.queryQuota((sentinel.subscription.v1.Querier.QueryQuotaRequest) request,
              (io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QueryQuotaResponse>) responseObserver);
          break;
        case METHODID_QUERY_QUOTAS:
          serviceImpl.queryQuotas((sentinel.subscription.v1.Querier.QueryQuotasRequest) request,
              (io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QueryQuotasResponse>) responseObserver);
          break;
        case METHODID_QUERY_PARAMS:
          serviceImpl.queryParams((sentinel.subscription.v1.Querier.QueryParamsRequest) request,
              (io.grpc.stub.StreamObserver<sentinel.subscription.v1.Querier.QueryParamsResponse>) responseObserver);
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

  private static abstract class QueryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    QueryServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return sentinel.subscription.v1.Querier.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("QueryService");
    }
  }

  private static final class QueryServiceFileDescriptorSupplier
      extends QueryServiceBaseDescriptorSupplier {
    QueryServiceFileDescriptorSupplier() {}
  }

  private static final class QueryServiceMethodDescriptorSupplier
      extends QueryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    QueryServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (QueryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new QueryServiceFileDescriptorSupplier())
              .addMethod(getQuerySubscriptionsMethod())
              .addMethod(getQuerySubscriptionsForNodeMethod())
              .addMethod(getQuerySubscriptionsForPlanMethod())
              .addMethod(getQuerySubscriptionsForAddressMethod())
              .addMethod(getQuerySubscriptionMethod())
              .addMethod(getQueryQuotaMethod())
              .addMethod(getQueryQuotasMethod())
              .addMethod(getQueryParamsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
