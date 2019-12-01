package com.imti;

import gnmi.Gnmi.SubscribeResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GnmiClient {

  private static final Logger logger = Logger.getLogger(GnmiClient.class.getName());

  public static void main(String[] args) {
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
        .usePlaintext(true).build();

    new StreamObserver<SubscribeResponse>() {

      @Override
      public void onNext(final SubscribeResponse value) {

      }

      @Override
      public void onError(final Throwable t) {
        logger.log(Level.WARNING, "error cancelled");
      }

      @Override
      public void onCompleted() {
        //TODO
      }
    };
  }

}
