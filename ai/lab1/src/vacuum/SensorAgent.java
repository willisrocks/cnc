package vacuum;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/** An agent for the vacuum world. */
public class SensorAgent extends AbstractAgent{
    public ArrayList<Move> history = new ArrayList<>(10000);
    private Action currentAction = Action.LEFT;
    private IVacuumStrategy strategy = new SpiralStrategy();
    private Action lastBlockAction;


    /**
     * Returns the agent's action in response to the dirtiness state of the
     * current square.
     */
    public  Action react(boolean dirty) {
        Action action;
        strategy = getStrategy(dirty);
        action = strategy.getNext();
        processAction(action);
//        System.out.println(strategy);
        return action;
    }

    private IVacuumStrategy getStrategy(boolean dirty) {
//        if (dirty) {
//            return new SuckStrategy();
//        } else if (lastMoveBlocked()) {
//            return updateStratAfterBlock();
//        } else if (last2Blocked()) {
//            return getCornerStrategy();
//        } else if (!recentlyCleaned()) {
//            return new RandomStrategy();
//        } else {
//            return new SpiralStrategy();
//        }

        if (dirty) {
            return new SuckStrategy();
        } else if (!recentlyCleaned()) {
            return new RandomStrategy();
        } else {
            return updateStratAfterBlock();
        }
    }

    public void sense_obstacle(boolean isObstacle) {
        if (isObstacle && history != null && !history.isEmpty()) {
            Move lastMove = lastMove();
            lastMove.setObstacle(true);
            history.set(history.size() - 1, lastMove);
            lastBlockAction = lastMove.getAction();
        }
    }

    private void processAction(Action action) {
//        System.out.println(lastMove());
        Move myMove = new Move(action);
        history.add(myMove);
    }

    public Move lastMove() {
        if (!history.isEmpty()) {
            return history.get(history.size() - 1);
        }
        return new Move(null);
    }

    private Move secondToLastMove() {
        if (history.size() >= 2) {
            return history.get(history.size() - 2);
        }
        return new Move(null);
    }

    private boolean recentlyCleaned() {
        if (history.size() >= 20) {
            List<Move> last20 = history.subList(history.size() - 20, history.size());
            for (Move move : last20) {
                if (move.getAction() == Action.SUCK) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private boolean lastMoveBlocked() {
        Move lastMove = lastMove();
        if (lastMove.blocked()) {
            return true;
        }
        return false;
    }

    private IVacuumStrategy updateStratAfterBlock() {
//        Action last = lastMove().getAction();
        Action last = lastBlockAction;
        if (last == Action.LEFT) {
            return new RightStrategy();
        } else if (last == Action.RIGHT) {
            return new UpStrategy();
        } else if (last == Action.UP) {
            return new DownStrategy();
        } else {
            return new LeftStrategy();
        }

    }

    private Action lastAction() {
        return lastMove().getAction();
    }

    private IVacuumStrategy getDirectionStrategy() {
        Action lastAction = lastAction();
        switch (lastAction) {
            case LEFT:
                return new LeftStrategy();
            case RIGHT:
                return new RightStrategy();
            case UP:
                return new DownStrategy();
            case DOWN:
                return new UpStrategy();
        }
        return new LeftStrategy();
    }

    private boolean last2Blocked() {
        Move last = lastMove();
        Move secondToLast = secondToLastMove();
        if (last.blocked() && secondToLast.blocked()) {
            return true;
        }
        return false;
    }

    private IVacuumStrategy getCornerStrategy() {
        if (blockedSW()) {
            return new DiagonalNE();
        } else if (blockedNW()) {
            return new DiagonalSE();
        } else if (blockedNE()) {
            return new DiagonalSW();
        } else if (blockedSE()) {
            return new DiagonalNW();
        } else if (blockedN()) {
            return new DownStrategy();
        } else if (blockedS()) {
            return new UpStrategy();
        } else if (blockedW()) {
            return new RightStrategy();
        } else {
            return new LeftStrategy();
        }
    }

    private IVacuumStrategy getDiagonalStrategy() {
        Random gen = new Random();
        int randInt = gen.nextInt(4);
        switch (randInt) {
            case (0):
                return new DiagonalSE();
            case (1):
                return new DiagonalNE();
            case (2):
                return new DiagonalNW();
            case (3):
                return new DiagonalSW();
        }
        return new DiagonalNW();
    }

    private boolean blockedN() {
        Action last = lastMove().getAction();
        Action nextLast = secondToLastMove().getAction();
        if (last == Action.UP && nextLast == Action.UP) {
            return true;
        } else {
            return false;
        }
    }

    private boolean blockedS() {
        Action last = lastMove().getAction();
        Action nextLast = secondToLastMove().getAction();
        if (last == Action.DOWN && nextLast == Action.DOWN) {
            return true;
        } else {
            return false;
        }
    }

    private boolean blockedW() {
        Action last = lastMove().getAction();
        Action nextLast = secondToLastMove().getAction();
        if (last == Action.LEFT && nextLast == Action.LEFT) {
            return true;
        } else {
            return false;
        }
    }

    private boolean blockedE() {
        Action last = lastMove().getAction();
        Action nextLast = secondToLastMove().getAction();
        if (last == Action.RIGHT && nextLast == Action.RIGHT) {
            return true;
        } else {
            return false;
        }
    }

    private boolean blockedSE() {
        Action last = lastMove().getAction();
        Action nextLast = secondToLastMove().getAction();
        if (last == Action.LEFT && nextLast == Action.DOWN) {
            return true;
        } else if (last == Action.DOWN && nextLast == Action.LEFT) {
            return true;
        } else {
            return false;
        }
    }

    private boolean blockedSW() {
        Action last = lastMove().getAction();
        Action nextLast = secondToLastMove().getAction();
        if (last == Action.RIGHT && nextLast == Action.DOWN) {
            return true;
        } else if (last == Action.DOWN && nextLast == Action.RIGHT) {
            return true;
        } else {
            return false;
        }
    }

    private boolean blockedNE() {
        Action last = lastMove().getAction();
        Action nextLast = secondToLastMove().getAction();
        if (last == Action.UP && nextLast == Action.RIGHT) {
            return true;
        } else if (last == Action.RIGHT && nextLast == Action.UP) {
            return true;
        } else {
            return false;
        }
    }

    private boolean blockedNW() {
        Action last = lastMove().getAction();
        Action nextLast = secondToLastMove().getAction();
        if (last == Action.LEFT && nextLast == Action.UP) {
            return true;
        } else if (last == Action.UP && nextLast == Action.LEFT) {
            return true;
        } else {
            return false;
        }
    }



    private Action getRandom(ArrayList<Action> actions) {
        Random gen = new Random();
        int randInt = gen.nextInt(actions.size());
        return actions.get(randInt);
    }

    public ArrayList<Action> actionsMinus1(Action removeAction) {
        ArrayList<Action> actions = new ArrayList<>(4);
        actions.add(Action.LEFT);
        actions.add(Action.RIGHT);
        actions.add(Action.UP);
        actions.add(Action.DOWN);
        actions.remove(removeAction);
        return actions;
    }

    public ArrayList<Action> actionsMinus2(Action removeAction1, Action removeAction2) {
        ArrayList<Action> actions = new ArrayList<>(4);
        actions.add(Action.LEFT);
        actions.add(Action.RIGHT);
        actions.add(Action.UP);
        actions.add(Action.DOWN);
        actions.remove(removeAction1);
        actions.remove(removeAction2);
        return actions;
    }

    public ArrayList<Action> actions() {
        ArrayList<Action> actions = new ArrayList<>(4);
        actions.add(Action.LEFT);
        actions.add(Action.RIGHT);
        actions.add(Action.UP);
        actions.add(Action.DOWN);
        return actions;
    }


}
