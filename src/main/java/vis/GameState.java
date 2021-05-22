package vis;

import lombok.Data;

import java.util.List;
@Data
public class GameState {

    private int WIDTH;
    private int HEIGHT;
    private int[] stateElements;

    public GameState(int WIDTH, int HEIGHT, int[] stateElements) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.stateElements = stateElements;
    }
}
