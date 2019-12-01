package com.imti;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class MetricsServer {

  public static void main(String[] args) throws InterruptedException, IOException {
    Server server = ServerBuilder.forPort(8080).addService(new GnmiServer()).build();

    server.start();

    server.awaitTermination();
  }
}
