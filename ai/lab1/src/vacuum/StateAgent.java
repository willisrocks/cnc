package vacuum;
import java.util.*;
import java.util.stream.IntStream;

/** An agent for the	vacuum world. */
public class StateAgent extends	AbstractAgent{
    public final List<Action> ACTIONS =
            Arrays.asList(Action.UP, Action.RIGHT, Action.DOWN, Action.LEFT);
    public ArrayList<Action> lastActions = new ArrayList<>();
    public ArrayList<Action> weightedActionList = new ArrayList<>(100);
    public ArrayList<Action> cleanWeightedActionList = new ArrayList<>(100);
    public Hashtable<Action, Integer> weights = new Hashtable<>();
    public Hashtable<Action, Integer> cleanWeights = new Hashtable<>();
    public boolean lastActionClean = false;

    /**
     * Creates a new StateAgent object
     */
    public StateAgent() {
        setup();
    }

    /**
     *	Returns the	agent's action	in	response	to	the dirtiness state of the
     *	current square.
     */
    public  Action	react(boolean dirty)	{
        Action dir = Action.LEFT; // default direction

        if	(dirty) {
            // I'm dirty. Let's SUCK!
            lastActionClean = true;
            return Action.SUCK;
        }
        else if (lastActionClean) {
            // I just cleaned something, let's keep going that direction
            dir = vacuum.Utility.getRandomItem(cleanWeightedActionList);
            updateCleanWeights();
        }
        else {
            // Otherwise, let's go random
            dir = vacuum.Utility.getRandomItem(weightedActionList);
            updateWeights();
        }
        lastActions.add(dir);
        lastActionClean = false;
        return dir;
    }

    /**
     * Initial state setup
     */
    private void setup() {
        ACTIONS.forEach(action -> {
            weights.put(action, 25);
            cleanWeights.put(action, 25);
            weightedActionList.add(action);
            cleanWeightedActionList.add(action);
        });
        lastActions.add(Action.LEFT);
    }

    /**
     * Creates a weightedActionList from
     * action weights that can be used to
     * get random, weighted actions
     */
    public void createWeightedActionList(Hashtable<Action, Integer> table, ArrayList list) {
        list.clear();
        ACTIONS.forEach(action -> fillWeightedActions(action, table, list));
    }

    /**
     * Fills a weighted arraylist
     * with individual actions
     */
    public void fillWeightedActions(Action action, Hashtable<Action, Integer> table, ArrayList list) {
        IntStream.rangeClosed(1, table.get(action))
                .forEach($ -> list.add(action));
    }

    /**
     * Updates new weights
     * from updated lastActions list
     */
    public void updateWeights() {
        ACTIONS.forEach(action -> weights.put(action, getWeight(action)));
        createWeightedActionList(weights, weightedActionList);
    }

    /**
     * Calculates new weight for
     * Action from updated lastActions list
     */
    public int getWeight(Action action) {
        int freq = Collections.frequency(lastActions, action);
        int size = lastActions.size();
        double weight = ((double)freq / (double)size * 100.0);
        return (int)Math.abs(Math.round(weight) - 100);
    }

    /**
     * Update clean weights table
     */
    public void updateCleanWeights() {
        ACTIONS.forEach(action -> cleanWeights.put(action, getCleanWeight(action)));
        createWeightedActionList(cleanWeights, cleanWeightedActionList);
    }

    /**
     * Calculate clean weight
     */
    public int getCleanWeight(Action action) {
        if (action == getLastAction()) {
            return 94;
        }
        return 2;
    }

    /**
     * Get the last action
     */
    public Action getLastAction() {
        return lastActions.get(lastActions.size() - 1);
    }

}
