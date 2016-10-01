package vacuum;
import java.util.Random;

/**
 * Created by chris on 9/30/16.
 */
public class Spiral {
    private Action[] sequence = new Action[8];
    private final Action[] NW = {
            Action.UP, Action.RIGHT, Action.DOWN, Action.DOWN, Action.LEFT, Action.LEFT, Action.UP, Action.UP
    };
    private final Action[] NE = {
            Action.RIGHT, Action.DOWN, Action.LEFT, Action.LEFT, Action.UP, Action.UP, Action.RIGHT, Action.RIGHT
    };
    private final Action[] SE = {
            Action.DOWN, Action.LEFT, Action.UP, Action.UP, Action.RIGHT, Action.RIGHT, Action.DOWN, Action.DOWN
    };
    private final Action[] SW = {
            Action.LEFT, Action.UP, Action.RIGHT, Action.RIGHT, Action.DOWN, Action.DOWN, Action.LEFT, Action.LEFT
    };


    public Spiral() {
        this.sequence = randSequence();
    }

    public Action[] getSequence() {
        return sequence;
    }

    private Action[] randSequence() {
        Random gen = new Random();
        int randInt = gen.nextInt(4);
        switch (randInt) {
            case 0: return NW;
            case 1: return NE;
            case 2: return SE;
            case 3: return SW;
        }
        return NE;
    }


}
