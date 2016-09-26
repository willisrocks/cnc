package vacuum;

/** An agent for the vacuum world. */
public class ReflexAgent extends AbstractAgent{

	/**
	 * Returns the agent's action in response to the dirtiness state of the
	 * current square.
	 */
    public  Action react(boolean dirty) {
	if (dirty) {
	    return Action.SUCK;
	}
	return Action.LEFT;
  }

}
