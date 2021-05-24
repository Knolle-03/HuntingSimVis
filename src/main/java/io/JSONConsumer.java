package io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vis.SimVis;

public class JSONConsumer extends Thread {

    private final SimVis simVis;

    public JSONConsumer(SimVis simVis) {
        this.simVis = simVis;
    }

    @Override
    public void run() {
        String JSON = simVis.getIncomingJSON().poll();
        vis.State newState = new Gson().fromJson(JSON, new TypeToken<vis.State>() {}.getType());
        System.out.println(newState.toString());
        simVis.getStateQueue().add(newState);
        System.out.println("Added new GameState to Queue.");
    }
}

