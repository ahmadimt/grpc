package com.imti;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class GnmiClient {

  public static void main(String[] args) {
      ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
          .usePlaintext(true).build();

      new StreamObserver<SubscribeRequest>()
    }

  }
