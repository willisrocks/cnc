package vacuum;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by chris on 9/29/16.
 */
public class MyTest {
    public static void main(String[] args) {
////        StateAgent agent = new StateAgent();
//        agent.lastActions.add(Action.UP);
//        agent.lastActions.add(Action.DOWN);
//        agent.lastActions.add(Action.RIGHT);
//        agent.lastActions.add(Action.RIGHT);
//        agent.lastActions.add(Action.RIGHT);
////        agent.lastActions.add(Action.UP);
////        agent.lastActions.add(Action.UP);
//
//        System.out.println(agent.lastActions);
//        System.out.println("Original Weights:");
//        System.out.println(agent.weights);
//
//        System.out.println("New Weights");
//        agent.updateWeights();
//        System.out.println(agent.weights);
//
//        agent.createWeightedActionList(agent.weights, agent.weightedActionList);
//        System.out.println(agent.weightedActionList);
//        System.out.println(agent.weightedActionList.size());
//
//        System.out.println(agent.cleanWeights);
//        agent.updateCleanWeights();
//        System.out.println(agent.cleanWeights);
//        int[][] array = {{1,2,3},{4,5,6},{7,8,9}};
//        int[][] array = new int[12][12];
//        MyTest.fillL(array);
//        MyTest.printArray(array);
        SensorAgent agent = new SensorAgent();
//        System.out.println(agent.lastMove());
//        agent.sense_obstacle(true);
        Action action = agent.react(false);
        System.out.println(action);
//        agent.sense_obstacle(true);
//        agent.react(false);
//        agent.react(false);
//        agent.react(false);
//        agent.react(false);
//        System.out.println(agent.history);
//        System.out.println(agent.lastMove());
//        ArrayList<Action> actions = new ArrayList<>(4);
//        actions.add(Action.LEFT);
//        actions.add(Action.RIGHT);
//        actions.add(Action.UP);
//        actions.add(Action.DOWN);
//
//        ArrayList<Action> updatedActions = MyTest.cloneActions(actions);
//        updatedActions.remove(Action.LEFT);
//        System.out.println("Original Actions:");
//        System.out.println(actions);
//        System.out.println("Updated Actions:");
//        System.out.println(agent.updatedActions(Action.LEFT));
//        ArrayList<Action> list = agent.updatedActions(Action.LEFT);
//        System.out.println(list.get(0));



//        System.out.println(agent.history.get(agent.history.size() - 1));

//        Move move = new Move(null);
//        System.out.println(move);








    }

    public static ArrayList<Action> cloneActions(ArrayList<Action> actions) {
        ArrayList<Action> clone = new ArrayList<>(actions.size());
        for (Action action : actions ) clone.add(action);
        return clone;
    }

    public static void fillL(int[][] array) {
        int cnt = 1;
        for (int x = 1; x < array.length - 1; x++) {
            for (int y = 1; y < array.length - 1; y++) {
                if ((x % 2 != 0) && (y % 3 == 0)) {
                    array[x-1][y] = 1;
                    array[x][y] = 1;
                    array[x][y+1] = 1;
                }
            }
        }
    }

    public static void printArray(int[][] array) {
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array.length; y++) {
                System.out.print(array[x][y]);
            }
            System.out.print("\n");
        }
    }
}
