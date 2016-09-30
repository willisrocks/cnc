package vacuum;

import java.util.Collections;

/**
 * Created by chris on 9/29/16.
 */
public class MyTest {
    public static void main(String[] args) {
        StateAgent agent = new StateAgent();
        agent.lastActions.add(Action.UP);
        agent.lastActions.add(Action.DOWN);
        agent.lastActions.add(Action.RIGHT);
        agent.lastActions.add(Action.RIGHT);
        agent.lastActions.add(Action.RIGHT);
//        agent.lastActions.add(Action.UP);
//        agent.lastActions.add(Action.UP);

        System.out.println(agent.lastActions);
        System.out.println("Original Weights:");
        System.out.println(agent.weights);

        System.out.println("New Weights");
        agent.updateWeights();
        System.out.println(agent.weights);

        agent.createWeightedActionList(agent.weights, agent.weightedActionList);
        System.out.println(agent.weightedActionList);
        System.out.println(agent.weightedActionList.size());

        System.out.println(agent.cleanWeights);
        agent.updateCleanWeights();
        System.out.println(agent.cleanWeights);




    }
}
