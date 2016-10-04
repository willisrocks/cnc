package vacuum;

import java.util.Random;

/**
 * Created by chris on 9/30/16.
 */
public class RandomStrategy implements IVacuumStrategy{

    public boolean hasNext() {
        return true;
    }

    public Action getNext() {
        return getRandom();
    }

    private Action getRandom() {
        Action[] actions = {Action.UP, Action.RIGHT, Action.DOWN, Action.LEFT};
        Random gen = new Random();
        int randInt = gen.nextInt(4);
        return actions[randInt];
    }
}
