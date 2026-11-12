import com.tub.*;
import io.grpc.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatserviceTest {

    private static Server server;

    @BeforeAll
    static void startServer() throws IOException {

        server = Grpc.newServerBuilderForPort(
                        8980,
                        InsecureServerCredentials.create())
                .addService(new ChatServiceImpl())
                .addService(new AdminServiceImpl())
                .build()
                .start();
    }

    @AfterAll
    static void stopServer(){
        server.shutdown();

    }

    @BeforeEach
    void clearStorage(){
        ChatStorage.messages.clear();
    }

    @Test
    void concurrentClients() throws InterruptedException{

        int clientCount= 10;

        Thread[]threads=new Thread[clientCount];

        for (int i = 0; i <clientCount ; i++) {
            threads[i]=new Thread(() ->{
                ManagedChannel channel = ManagedChannelBuilder
                        .forAddress("localhost", 8980)
                        .usePlaintext()
                        .build();

                ChatServiceGrpc.ChatServiceBlockingStub stub =
                        ChatServiceGrpc.newBlockingStub(channel);

                NachrichtSenden request = NachrichtSenden.newBuilder()
                        .setId(UUID.randomUUID().toString())
                        .setUser("Test")
                        .setNachricht("Hallo")
                        .setTimestamp(LocalDateTime.now().toString())
                        .build();

                stub.senden(request);

                channel.shutdown();

            });

            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        assertEquals(clientCount, ChatStorage.messages.size());

    }
            }


