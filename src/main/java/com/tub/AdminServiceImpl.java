package com.tub;

import io.grpc.stub.StreamObserver;

public class AdminServiceImpl extends AdminServiceGrpc.AdminServiceImplBase {

    @Override
    public void loescheNachricht(DeleteRequest request,
                                 StreamObserver<DeleteResponse> responseObserver) {

        boolean success =
                ChatStorage.messages.remove(request.getId()) != null;

        DeleteResponse response =
                DeleteResponse.newBuilder()
                        .setSuccess(success)
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}