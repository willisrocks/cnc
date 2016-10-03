package vacuum;

import java.util.Random;


/** The vacuum world. */
public class TestRun {
    static Random rand = new Random();

    /**
     * Runs an agent in the world for many steps, many times, and reports the
     * total score.
     */
    public static void main(String[] args) {
        StateAgent agent = new StateAgent();
        System.out.println(StateAgent.strategy);
        agent.react(false);
        agent.react(false);
        agent.react(false);
        agent.react(false);
        agent.react(true);




    }
}
