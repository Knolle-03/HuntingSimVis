package io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vis.SimVis;

import java.io.IOException;
import java.net.SocketTimeoutException;


public class JSONConverter extends Thread {

    private final SimVis simVis;
    private final Connection connection;

    public JSONConverter(SimVis simVis) {
        this.simVis = simVis;
        this.connection = simVis.getConnectionListener().connection;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                String msg = connection.getReader().readLine();
                vis.State newState = new Gson().fromJson(msg, new TypeToken<vis.State>() {}.getType());
                simVis.getStateQueue().add(newState);
                System.out.println("Added new GameState to Queue.");
            }
            catch (SocketTimeoutException ignored) {}
            catch (IOException | NullPointerException ignore) {
                //System.out.println(ANSI_RED_BACKGROUND + "Connection: " + connection + " is not available." + ANSI_RESET);
            }
        }
    }
}
