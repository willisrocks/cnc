package vacuum;

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
        int[][] array = new int[12][12];
        MyTest.fillL(array);
        MyTest.printArray(array);







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
