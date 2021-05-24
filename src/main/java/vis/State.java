package vis;

import lombok.Data;

import java.util.List;
@Data
public class State {

    private int[] state;

    public State(int[] state) {
        this.state = state;
    }
}
