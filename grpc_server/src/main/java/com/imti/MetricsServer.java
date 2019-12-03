package com.imti;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetricsServer {

  private static final Logger logger = Logger.getLogger(HelloWorldServer.class.getName());

  private static Server server;

  public static void main(String[] args) throws InterruptedException, IOException {

    int port = 8989;
    server = ServerBuilder.forPort(port)
        .addService(new GnmiServer())
        .build();
    server.start();

    logger.log(Level.INFO, "Server started, listening on port {0}", port);
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.err.println("*** shutting down gRPC server since JVM is shutting down");
        stopper();
        System.err.println("*** server shut down");
      }
    });

    server.awaitTermination();
  }

  private static void stopper() {
    if (Objects.nonNull(server)) {
      server.shutdown();
    }
  }
}
