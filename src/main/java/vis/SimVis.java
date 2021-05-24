package vis;

import io.ConnectionListener;
import io.*;
import lombok.Data;
import lombok.SneakyThrows;
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
    public JSONProducer JSONProducer;
    {
        try {
            serverSocket = new ServerSocket(5550);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int X = 10;
    int Y = 10;
    int squareSize = 50;
    int background_color = 255;

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



        plainImage = this.loadImage("src/main/resources/plain-tex.jpg");
        obstacleImage = this.loadImage("src/main/resources/stone-tex.jpg");
        deerImage = this.loadImage("src/main/resources/deer.jpg");
        wolfImage = this.loadImage("src/main/resources/wolf.png");
        highGrassImage = this.loadImage("src/main/resources/grass.jpg");

        connectionListener = new ConnectionListener(this, serverSocket);
        connectionListener.start();


        // add "blank" game state
        stateQueue.add(new State(new int[X * Y]));

        // init cells
        for (int j = 0; j < Y; j++) {
            for (int i = 0; i < X; i++) {
                Cell cell = new Cell(this, i, j, squareSize);
                cell.updateCellState(rng.nextInt(9));
                cells.add(cell);

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

        while (stateQueue.isEmpty()) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        background(255);
        State state = stateQueue.poll();

        System.out.println("StateCount: " + state.getState().length);


        for (int i = 0; i < cells.size(); i++) {
            Cell currentCell = cells.get(i);
            currentCell.updateCellState(state.getState()[i]);

        }

        for (Cell cell : cells) {
            cell.show();
        }

    }
}
