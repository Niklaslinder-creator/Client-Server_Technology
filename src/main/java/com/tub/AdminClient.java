package com.tub;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class AdminClient {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8980)
                .usePlaintext()
                .build();

        AdminServiceGrpc.AdminServiceBlockingStub stub =
                AdminServiceGrpc.newBlockingStub(channel);

        Scanner scanner= new Scanner(System.in);
        System.out.println("ID der Nachricht um zu löschen");
        String id =scanner.nextLine();
        DeleteRequest request = DeleteRequest.newBuilder()
                .setId(id)
                .build();

        DeleteResponse response = stub.loescheNachricht(request);

        if(response.getSuccess()){
            System.out.println("Nachricht erfolgreich gelöscht.");
        }else{
            System.out.println("Keine Nachricht mit dieser ID gefunden.");
        }
        channel.shutdown();
    }
}
