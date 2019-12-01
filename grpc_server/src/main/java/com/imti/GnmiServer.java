package com.imti;

import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Message.Builder;
import com.google.protobuf.TextFormat;
import com.google.protobuf.TextFormat.ParseException;
import gnmi.Gnmi.Notification;
import gnmi.Gnmi.SubscribeRequest;
import gnmi.Gnmi.SubscribeResponse;
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
        System.out.println("onNext from server");

        Map<FieldDescriptor, Object> notification = value.getAllFields();
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        //TODO put new data
        responseObserver.onNext(SubscribeResponse.getDefaultInstance());
      }

      @Override
      public void onError(final Throwable t) {
        responseObserver.onError(t);
      }

      @Override
      public void onCompleted() {
        String contents = FileResponse.readContentFromFile("hello.proto");
        final SubscribeResponse.Builder builder = SubscribeResponse.newBuilder();
        try {
          TextFormat.merge(contents, builder);
        } catch (ParseException e) {
          e.printStackTrace();
        }
        responseObserver.onNext(builder.build());

      }
    };
  }
}

