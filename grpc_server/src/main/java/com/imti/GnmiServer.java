package com.imti;

import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Message.Builder;
import com.google.protobuf.TextFormat;
import com.google.protobuf.TextFormat.ParseException;
import gnmi.Gnmi.Notification;
import gnmi.Gnmi.SubscribeRequest;
import gnmi.Gnmi.SubscribeResponse;
import gnmi.Gnmi.Update;
import gnmi.gNMIGrpc.gNMIImplBase;
import io.grpc.stub.StreamObserver;
import java.util.Map;
import java.util.Map.Entry;

public class GnmiServer extends gNMIImplBase {

  @Override
  public StreamObserver<SubscribeRequest> subscribe(
      StreamObserver<SubscribeResponse> responseObserver) {

    return new StreamObserver<SubscribeRequest>() {
      @Override
      public void onNext(final SubscribeRequest value) {
        //message from the client is received here

        Map<FieldDescriptor, Object> notification = value.getAllFields();
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        Update contents = FileResponse.from("subscribeResponse.gnmi");
        Notification serverNotification = Notification.newBuilder().addUpdate(contents).build();
        final SubscribeResponse.Builder builder = SubscribeResponse.newBuilder()
            .mergeUpdate(serverNotification);
        responseObserver.onNext(builder.build());
      }

      @Override
      public void onError(final Throwable t) {
        responseObserver.onError(t);
      }

      @Override
      public void onCompleted() {
        //TODO

      }
    };
  }
}

