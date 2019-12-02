package com.imti;

import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.TextFormat;
import com.google.protobuf.TextFormat.ParseException;
import gnmi.Gnmi;
import gnmi.Gnmi.Path;
import gnmi.Gnmi.PathElem;
import gnmi.Gnmi.SubscribeRequest;
import gnmi.Gnmi.SubscribeResponse;
import gnmi.Gnmi.Subscription;
import gnmi.Gnmi.SubscriptionList;
import gnmi.Gnmi.SubscriptionList.Builder;
import gnmi.Gnmi.SubscriptionList.Mode;
import gnmi.Gnmi.SubscriptionMode;
import gnmi.Gnmi.Update;
import gnmi.gNMIGrpc;
import gnmi.gNMIGrpc.gNMIStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.Random;
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
            //Push from Server to Client is received here
            response.newBuilderForType();
            logger.info(response.toString());


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
      SubscribeRequest.Builder builder = SubscribeRequest.newBuilder()
          .setSubscribe(newSubBuilder());
      subscribeRequest.onNext(builder.build());
      Thread.sleep(random.nextInt(1000) + 500);
    }
  }

  public static SubscriptionList newSubBuilder() {
    Builder subscriptionListBuilder = Gnmi.SubscriptionList.newBuilder();
    subscriptionListBuilder.setMode(Mode.STREAM);
    subscriptionListBuilder.setQos(Gnmi.QOSMarking.newBuilder().setMarking(0));
    subscriptionListBuilder.setAllowAggregation(false);
    subscriptionListBuilder.setUpdatesOnly(false);
    Gnmi.Subscription.Builder subscriptionBuilder = Gnmi.Subscription.newBuilder();
    Path.Builder builder = Path.newBuilder()
        .addElem(PathElem.newBuilder().setName("ashish").putKey("first", "value").build());
    subscriptionBuilder.setPath(builder.build());
    subscriptionBuilder.setMode(SubscriptionMode.SAMPLE);
    //subscriptionBuilder.setMode(SubscriptionMode.ON_CHANGE);
    subscriptionBuilder.setSampleInterval(10);
    subscriptionBuilder.setSuppressRedundant(false);
    subscriptionBuilder.setHeartbeatInterval(5);
    subscriptionListBuilder.addSubscription(subscriptionBuilder.build());
    return subscriptionListBuilder.build();
  }
}
