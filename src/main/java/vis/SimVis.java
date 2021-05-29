package vis;

import io.ConnectionListener;
import io.*;
import lombok.Data;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;


@Data
public class SimVis extends PApplet {

    public PImage plainImage;
    public PImage obstacleImage;
    public PImage deerImage;
    public PImage wolfImage;
    public PImage highGrassImage;

    public ConnectionListener connectionListener;
    public ServerSocket serverSocket;
    public JSONConverter JSONConverter;
    {
        try {
            serverSocket = new ServerSocket(5550);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int X = 20;
    public static int Y = 20;
    int squareSize = 50;
    int background_color = 255;

    int tickCounter = 0;

    boolean debug = false;

    // TODO:: replace
    Random rng = new Random();

    // storage for incoming JSON strings
    private Queue<String> incomingJSON = new LinkedList<>();
    private Queue<State> stateQueue = new LinkedList<>();
    private List<Cell> cells = new ArrayList<>();


    public static void main(String... args){
        PApplet.main("vis.SimVis");
    }


    @Override
    public void setup() {
        plainImage = this.loadImage("src/main/resources/plain.jpg");
        obstacleImage = this.loadImage("src/main/resources/stone.jpg");
        deerImage = this.loadImage("src/main/resources/deer.jpg");
        wolfImage = this.loadImage("src/main/resources/wolf.jpg");
        highGrassImage = this.loadImage("src/main/resources/grass.jpg");

        connectionListener = new ConnectionListener(this, serverSocket);
        connectionListener.start();

        // add "blank" game state
        stateQueue.add(new State(new int[X * Y]));
        // init cells
        // row
        for (int j = Y - 1; j >= 0 ; j--) {
            //col
            for (int i = 0; i < X; i++) {
                Cell cell = new Cell(this, i, j, squareSize);
                cell.updateCellState(rng.nextInt(9));
                cells.add(cell);
            }
        }

        frameRate(30);
        background(background_color);



    }

    public void settings(){
        size( squareSize * X, squareSize * Y + squareSize);
    }

    public void draw(){



        if (!stateQueue.isEmpty()) {
            System.out.println("Queue size: " + stateQueue.size());
            background(255);
            State state = stateQueue.poll();
            List<Cell> predators = new ArrayList<>();
            List<Cell> prey = new ArrayList<>();
            for (int i = 0; i < cells.size(); i++) {
                Cell currentCell = cells.get(i);
                int currentCellState = state.getState()[i];
                if (currentCellState == 4 || currentCellState == 6 || currentCellState == 7 || currentCellState == 8) {
                    predators.add(currentCell);
                }
                if (currentCellState == 2 || currentCellState == 5 || currentCellState == 7 || currentCellState == 8) {
                    prey.add(currentCell);
                }
                currentCell.updateCellState(state.getState()[i]);
            }
            for (Cell cell : cells) {
                if (predators.contains(cell)) continue;
                if (prey.contains(cell)) continue;
                cell.show(debug);
            }
            for (Cell cell : predators) {
                cell.show(debug);
            }
            for (Cell cell : prey) {
                cell.show(debug);
            }
            tickCounter++;

            fill(0);
            stroke(255, 0, 0);
            textSize(20);
            text("tick: " + tickCounter, squareSize / 2, squareSize * Y + (squareSize / 2));
        }

    }
}
