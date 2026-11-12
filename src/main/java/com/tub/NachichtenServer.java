package com.tub;


import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NachichtenServer {

    static final Logger logger = Logger.getLogger(NachichtenServer.class.getName());
    static final int PORT = 8980;
    static Server server;

    static void main() {
        try {
            server = Grpc.newServerBuilderForPort(PORT, InsecureServerCredentials.create())
                    .addService(new ChatServiceImpl())
                    .addService(new AdminServiceImpl())
                    .build()
                    .start();
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

}


