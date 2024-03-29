package com.imti;

import com.imti.helloworld.GreeterGrpc;
import com.imti.helloworld.HelloRequest;
import com.imti.helloworld.HelloResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * created by imteyaza-1lm on 29/11/19
 **/
public class HelloWorldClient {

  private static final Logger logger = Logger.getLogger(HelloWorldClient.class.getName());

  private final ManagedChannel channel;
  private final GreeterGrpc.GreeterBlockingStub blockingStub;

  public HelloWorldClient(String host, int port) {
    this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
  }

  public HelloWorldClient(ManagedChannel channel) {
    this.channel = channel;
    this.blockingStub = GreeterGrpc.newBlockingStub(channel);
  }

  public static void main(String[] args) throws InterruptedException {
    HelloWorldClient client = new HelloWorldClient("localhost", 50051);

    try {
      String name = "World";
      if (args.length > 0) {
        name = args[0];
      }
      client.greet(name);

    } finally {
      client.shutdown();
    }
  }


  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  public void greet(String name) {
    logger.log(Level.INFO, "Will try to greet {0} .... ", name);
    HelloRequest request = HelloRequest.newBuilder().setName(name).build();
    HelloResponse response;
    try {
      response = blockingStub.sayHello(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
      return;
    }
    logger.log(Level.INFO, "Greeting: {0} ", response.getMessage());
  }
}
