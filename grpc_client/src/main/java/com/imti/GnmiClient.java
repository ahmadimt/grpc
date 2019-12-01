package com.imti;

import com.imti.helloworld.GreeterGrpc;
import com.imti.helloworld.HelloRequest;
import com.imti.helloworld.HelloResponse;
import gnmi.Gnmi.SubscribeRequest;
import gnmi.Gnmi.SubscribeResponse;
import gnmi.gNMIGrpc;
import gnmi.gNMIGrpc.gNMIBlockingStub;
import gnmi.gNMIGrpc.gNMIStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GnmiClient {

  private static final Logger logger = Logger.getLogger(GnmiClient.class.getName());


  public static void main(String[] args) throws InterruptedException {
    Random random = new Random();
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8989).usePlaintext()
        .build();
    gNMIStub gNMIStub = gNMIGrpc.newStub(channel);
    StreamObserver<SubscribeRequest> subscribeRequest = gNMIStub
        .subscribe(new StreamObserver<SubscribeResponse>() {
          @Override
          public void onNext(final SubscribeResponse response) {
            response.newBuilderForType();
            logger.info(response.toString());
            //TODO push new value
          }

          @Override
          public void onError(final Throwable t) {
            logger.severe(t.toString());
          }

          @Override
          public void onCompleted() {
            logger.info("Completed");
          }
        });

    for (int i = 0; i < 1000; i++) {
      int index = random.nextInt(2345);
      SubscribeRequest newObj = SubscribeRequest.getDefaultInstance();
      subscribeRequest.onNext(newObj);
      Thread.sleep(random.nextInt(1000) + 500);
    }
  }
}
