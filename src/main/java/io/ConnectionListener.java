package io;

import vis.SimVis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;


// Handles incoming connections
public class ConnectionListener extends Thread {

    ServerSocket serverSocket;
    Connection connection;
    SimVis simVis;
    private JSONProducer gameStateJSONReader;

    public ConnectionListener(SimVis simVis, ServerSocket serverSocket) {
        this.simVis = simVis;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                System.out.println("Listening for new connection.");
                connection = new Connection(serverSocket.accept());
                gameStateJSONReader = new JSONProducer(simVis);
                gameStateJSONReader.start();
                System.out.println("New connection established.\n Connection: " + connection);
            }catch (SocketException e) {
                System.out.println("Shutting down ConnectionListener.");
                this.interrupt();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
