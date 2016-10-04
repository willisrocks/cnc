package vacuum;

import java.util.ArrayList;

/** An agent for the vacuum world. */
public class StateAgent extends AbstractAgent{
    static IVacuumStrategy strategy = new SpiralOld();
    private ArrayList<Action> history = new ArrayList<>(10000);

    /**
     * Returns the agent's action in response to the dirtiness state of the
     * current square.
     */
    public Action react(boolean dirty) {
        if (dirty) {
            return suck();
        }
        return getAction();
    }

    /**
     * Returns the SUCK action and adds action to history
     */
    private Action suck() {
        history.add(Action.SUCK);
        return Action.SUCK;
    }

    /**
     * Returns an action.
     * If the current strategy doesn't have actions,
     * gets the new strategy and returns its action.
     */
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

    /**
     * Returns a strategy
     */
    private IVacuumStrategy getNewStrategy() {
        if (tooClean()) {
            return new DiagonalOld();
        }
        return new SpiralOld();
    }

    /**
     * Returns true if the board is too clean
     */
    private boolean tooClean() {

        if (history.size() > 8) {
            for (Action action : history.subList(history.size() - 8, history.size())) {
                if (action == Action.SUCK) {
                    return false;
                }
            }
        }
        return true;
    }

    public void sense_obstacle(boolean isObstacle) {

    }

}
