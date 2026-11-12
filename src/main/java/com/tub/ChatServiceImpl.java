package com.tub;

import io.grpc.stub.StreamObserver;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {


    @Override
    public void senden(NachrichtSenden request,
                       StreamObserver<NachirchtEmpfangen> responseObserver) {

        ChatMessage message =
                new ChatMessage(
                        request.getId(),
                        request.getUser(),
                        request.getNachricht(),
                        request.getTimestamp());

        ChatStorage.messages.put(message.getId(), message);

        NachirchtEmpfangen response =
                NachirchtEmpfangen.newBuilder()
                        .setNachricht("Nachricht gespeichert")
                        .setGelesen(true)
                        .build();

        System.out.println(
                request.getUser()
                        + " hat geschrieben: "
                        + request.getNachricht());



        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void holeNachrichten(NachrichtenRequest request,
                                StreamObserver<NachrichtenAntwort> responseObserver) {

        NachrichtenAntwort.Builder builder = NachrichtenAntwort.newBuilder();

        for (ChatMessage msg : ChatStorage.messages.values()) {

            Nachricht nachricht = Nachricht.newBuilder()
                    .setId(msg.getId())
                    .setUser(msg.getUser())
                    .setNachricht(msg.getText())
                    .setTimestamp(msg.getTimestamp())
                    .build();

            builder.addNachrichten(nachricht);
        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
