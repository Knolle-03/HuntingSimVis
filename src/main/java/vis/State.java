package vis;

import lombok.Data;

@Data
public class State {

    private int[] state;

    public State(int[] state) {
        this.state = state;
    }
}
