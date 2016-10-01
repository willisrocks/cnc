package vacuum;
import sun.util.locale.provider.FallbackLocaleProviderAdapter;

import java.util.*;

/** An agent for the vacuum world. */
public class SpiralAgent extends AbstractAgent{
    private int moves = 0;
    static Spiral spiral = new Spiral();
    static Diagonal diagonal = new Diagonal();
    private Action[] sequence;
    private boolean bailMode = false;
    private int bailMoves = 0;
    private Queue<Action> lastActions = new LinkedList<>();

    /**
     * Returns the agent's action in response to the dirtiness state of the
     * current square.
     */
    public  Action react(boolean dirty) {
        Action action;

        // Check to see if we need bail mode
        if (bailMoves == 24) {
            bailMode = false;
        } else {
            bailMode = startBailMode();
        }

        // Choose action
        if (dirty) {
            lastActions.add(Action.SUCK);
            return Action.SUCK;
        } else if (bailMode) {
            if (bailMoves % 24 == 0) {
                sequence = diagonal.getSequence();
                bailMoves = 0;
                action = Utility.rand();
                addAction(action);
                return action;
            }
            action = sequence[bailMoves];
            addAction(action);
            bailMoves += 1;
            return action;
        }
        if (moves % 8 == 0) {
            sequence = spiral.getSequence();
            moves = 0;
            action = Utility.rand();
            addAction(action);
            return action;
        }
        action = sequence[moves];
        moves += 1;
        addAction(action);
        return action;
    }

    private void addAction(Action action) {
        lastActions.add(action);
        if (lastActions.size() > 10) {
            lastActions.remove();
        }
    }

    private boolean startBailMode() {
        if (lastActions.size() == 10) {
            for (Action action : lastActions) {
                if (action == Action.SUCK) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
