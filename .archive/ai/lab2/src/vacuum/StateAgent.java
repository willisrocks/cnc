package vacuum;

import java.util.ArrayList;

/** An agent for the vacuum world. */
public class StateAgent extends AbstractAgent{
    public ArrayList<Move> history = new ArrayList<>(10000);
    private IVacuumStrategy strategy;
    private Action lastDirection = Action.LEFT;

    /**
     * Returns the agent's action in response to the dirtiness state of the
     * current square.
     */
    public  Action react(boolean dirty) {
        Action action;
        getStrategy(dirty);
        action = getAction();
        processAction(action);
        return action;
    }

    private void getStrategy(boolean dirty) {
        // what strategy should we use?
        if (dirty) {
            strategy = new SuckStrategy();
        } else if (lastMoveBlocked()) {
            strategy = new RandomStateStrategy();
        } else if (tooClean()) {
            strategy = new RandomStateStrategy();
        } else {
            if (lastDirection == Action.UP) {
                strategy = new UpStrategy();
            } else if (lastDirection == Action.RIGHT) {
                strategy = new RightStrategy();
            } else if (lastDirection == Action.DOWN) {
                strategy = new DownStrategy();
            } else {
                strategy = new LeftStrategy();
            }

        }
    }

    private Action getAction() {
        if (lastMoveBlocked()) {
            return strategy.getNext(lastDirection);
        }
        return strategy.getNext();
    }

    private void processAction(Action action) {
        Move myMove = new Move(action);
        history.add(myMove);
        if (action != Action.SUCK)  {
            lastDirection = action;
        }
        System.out.println(action);
    }

    public void sense_obstacle(boolean isObstacle) {
        if (isObstacle && history != null && !history.isEmpty()) {
            Move lastMove = lastMove();
            lastMove.setObstacle(true);
            history.set(history.size() - 1, lastMove);
            lastDirection = lastActionNotBlocked();
        }
    }

    public Move lastMove() {
        if (!history.isEmpty()) {
            return history.get(history.size() - 1);
        }
        return new Move(null);
    }

    private boolean lastMoveBlocked() {
        Move lastMove = lastMove();
        if (lastMove.blocked()) {
            return true;
        }
        return false;
    }

    private Action lastActionNotBlocked() {
        for (int i = history.size() - 1; i >= 0; i-- ) {
            Move move = history.get(i);
            if (!move.blocked()) {
                return move.getAction();
            }
        }
        return lastDirection;
    }

    private Action lastAction() {
        return lastMove().getAction();
    }

    private boolean tooClean() {
        if (history.size() > 24) {
            for (Move move : history.subList(history.size() - 24, history.size())) {
                if (move.getAction() == Action.SUCK) {
                    return false;
                }
            }
        }
        return true;
    }


}
