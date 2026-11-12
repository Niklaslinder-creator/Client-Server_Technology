package com.tub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

public class NachrichtenClient {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8980)
                .usePlaintext()
                .build();

        ChatServiceGrpc.ChatServiceBlockingStub stub =
                ChatServiceGrpc.newBlockingStub(channel);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Benutzername: ");
        String user = scanner.nextLine();

        System.out.print("Nachricht: ");
        String text = scanner.nextLine();

        NachrichtSenden request = NachrichtSenden.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setUser(user)
                .setNachricht(text)
                .setTimestamp(LocalDateTime.now().toString())
                .build();

        NachirchtEmpfangen response = stub.senden(request);

        System.out.println(response.getNachricht());

        NachrichtenAntwort antwort =
                stub.holeNachrichten(
                        NachrichtenRequest.newBuilder().build());

        System.out.println("\n----- Chat -----");

        for (Nachricht n : antwort.getNachrichtenList()) {

            System.out.println(
                    n.getId()
                            + " | "
                            + n.getUser()
                            + " | "
                            + n.getNachricht()
                            + " | "
                            + n.getTimestamp());

        }

        channel.shutdown();
    }
}