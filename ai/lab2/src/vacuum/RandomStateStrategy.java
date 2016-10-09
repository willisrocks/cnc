package vacuum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Returns a random action. When given a removeAction,
 * returns a random action from the list of actions minus
 * the removeAction. Exaple, getNext(Action.LEFT) returns
 * a random action from the list {UP, DOWN, RIGHT}.
 */
public class RandomStateStrategy implements IVacuumStrategy{
    private ArrayList<Action> actions;
    private Action[] allActions = {Action.UP, Action.RIGHT, Action.DOWN, Action.LEFT};

    public RandomStateStrategy() {
        actions = new ArrayList<>(Arrays.asList(allActions));
    }

    public boolean hasNext() {
        return true;
    }

    public Action getNext() {
        return getRandom(actions);
    }

    public Action getNext(Action removeAction) {
        return getRandom(actionsMinus1(removeAction));
    }

    private Action getRandom(ArrayList<Action> actions) {
        Random gen = new Random();
        int randInt = gen.nextInt(actions.size());
        return actions.get(randInt);
    }

    private ArrayList<Action> actionsMinus1(Action removeAction) {
        actions.remove(removeAction);
        return actions;
    }
}
