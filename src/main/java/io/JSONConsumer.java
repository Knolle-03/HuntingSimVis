package io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vis.GameState;
import vis.SimVis;

import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class JSONConsumer extends Thread {

    private final SimVis simVis;

    public JSONConsumer(SimVis simVis) {
        this.simVis = simVis;
    }

    @Override
    public void run() {
        String JSON = simVis.getIncomingJSON().poll();
        GameState newGameState = new Gson().fromJson(JSON, new TypeToken<GameState>() {}.getType());
        simVis.getGameStateQueue().add(newGameState);
        System.out.println("Added new GameState to Queue.");
    }
}

