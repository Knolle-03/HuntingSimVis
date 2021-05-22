package vis;

import io.Connection;
import io.ConnectionListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@EqualsAndHashCode(callSuper = false)
@Data
public class SimVis extends PApplet {

    int X = 20;
    int Y = 20;
    int squareSize = 50;
    int background_color = 255;

    ConnectionListener connectionListener;
    // storage for incoming JSON strings
    private Queue<String> incomingJSON = new LinkedList<>();
    private Queue<GameState> gameStateQueue = new LinkedList<>();
    private List<Cell> cells = new ArrayList<>();








    public static void main(String... args){
        PApplet.main("vis.SimVis");
    }


    @Override
    public void setup() {
        // add "blank" game state
        gameStateQueue.add(new GameState(X, Y, new int[X * Y]));

        // init cells
        for (int j = 0; j < Y; j++) {
            for (int i = 0; i < X; i++) {
                cells.add(new Cell(this, i, j, squareSize));
            }
        }


        frameRate(50);
        background(background_color);
    }

    public void settings(){
        size( squareSize * X, squareSize * Y);
    }

    @SneakyThrows
    public void draw(){
        background(255);

        while (gameStateQueue.isEmpty()) {
            System.out.println("Waiting for new GameState.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Cell cell : cells) {
            cell.show();
        }


    }



}
