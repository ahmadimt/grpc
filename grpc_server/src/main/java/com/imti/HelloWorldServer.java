package com.imti;

import com.imti.helloworld.GreeterGrpc.GreeterImplBase;
import com.imti.helloworld.HelloRequest;
import com.imti.helloworld.HelloResponse;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * created by imteyaza-1lm on 29/11/19
 **/
public class HelloWorldServer {

  private static final Logger logger = Logger.getLogger(HelloWorldServer.class.getName());

  private Server server;

  public static void main(String[] args) throws IOException, InterruptedException {
    final HelloWorldServer helloWorldServer = new HelloWorldServer();
    helloWorldServer.start();
    helloWorldServer.blockUntilShutdown();
  }


  public void start() throws IOException {
    int port = 50051;
    server = ServerBuilder.forPort(port)
        .addService(new GreeterImpl())
        .build().start();
    logger.log(Level.INFO, "Server started, listening on port {0}", port);
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.err.println("*** shutting down gRPC server since JVM is shutting down");
        HelloWorldServer.this.stop();
        System.err.println("*** server shut down");
      }
    });
  }

  private void stop() {
    if (Objects.nonNull(server)) {
      server.shutdown();
    }
  }

  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }


  static class GreeterImpl extends GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request,
        StreamObserver<HelloResponse> responseObserver) {
      HelloResponse response = HelloResponse.newBuilder()
          .setMessage("Server says hello " + request.getName())
          .build();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }
  }
}
