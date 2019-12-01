package com.imti;

import com.imti.helloworld.GreeterGrpc;
import com.imti.helloworld.HelloRequest;
import com.imti.helloworld.HelloResponse;
import gnmi.Gnmi.SubscribeResponse;
import gnmi.gNMIGrpc;
import gnmi.gNMIGrpc.gNMIBlockingStub;
import gnmi.gNMIGrpc.gNMIStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GnmiClient {

  private static final Logger logger = Logger.getLogger(GnmiClient.class.getName());

  public static void main(String[] args) {
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8989).usePlaintext()
        .build();
    gNMIStub gNMIStub = gNMIGrpc.newStub(channel);

    final StreamObserver<SubscribeResponse> responseObserver = new StreamObserver<SubscribeResponse>() {
      @Override
      public void onNext(final SubscribeResponse value) {
        logger.info(value.toString());
      }

      @Override
      public void onError(final Throwable t) {
        logger.severe(t.toString());
      }

      @Override
      public void onCompleted() {
        logger.info("Completed");
      }
    };

    gNMIStub.subscribe(responseObserver);
    while (true);

  }

}
