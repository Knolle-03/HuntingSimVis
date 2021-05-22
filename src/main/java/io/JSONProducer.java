package io;

import vis.SimVis;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Queue;

import static utils.ThreadColors.*;

public class JSONProducer extends Thread {

    private final SimVis simVis;
    private final Connection connection;
    private final Queue<String> incomingJSON;

    public JSONProducer(SimVis simVis) {
        this.simVis = simVis;
        this.connection = simVis.getConnectionListener().connection;
        this.incomingJSON = simVis.getIncomingJSON();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                String msg = connection.getReader().readLine();
                System.out.println("Received new message: " + msg);
                incomingJSON.add(msg);
                new JSONConsumer(simVis).start();
            }
            catch (SocketTimeoutException ignored) {}
            catch (IOException | NullPointerException e) {
                System.out.println(ANSI_RED_BACKGROUND + "Connection: " + connection + " is not available." + ANSI_RESET);
            }
        }
    }
}
