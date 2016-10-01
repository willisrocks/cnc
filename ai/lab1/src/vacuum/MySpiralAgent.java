package vacuum;

import java.util.ArrayList;

/** An agent for the vacuum world. */
public class MySpiralAgent extends AbstractAgent{
    static IVacuumStrategy strategy = new Spiral();
    public ArrayList<Action> history = new ArrayList<>(10000);

    /**
     * Returns the agent's action in response to the dirtiness state of the
     * current square.
     */
    public Action react(boolean dirty) {
        Action action;
        if (dirty) {
            action = suck();
            return action;

        }

        action = getAction();
        return action;
    }

    private Action suck() {
        history.add(Action.SUCK);
        return Action.SUCK;
    }

    private Action getAction() {
        Action action;
        if (strategy.hasNext()) {
            action = strategy.getNext();
        } else {
            strategy = getNewStrategy();
            action = strategy.getNext();
        }
        history.add(action);
        return action;
    }

    private IVacuumStrategy getNewStrategy() {
        if (tooDirty()) {
            return new Diagonal();
        }
        return new Spiral();
    }

    private boolean tooDirty() {
        if (history.size() > 8) {
            for (Action action : history.subList(history.size() - 8, history.size())) {
                if (action == Action.SUCK) {
                    return false;
                }
            }
        }
        return true;
    }


}
