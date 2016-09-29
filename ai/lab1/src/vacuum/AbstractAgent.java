package vacuum;

/** An agent for the vacuum world. */
public abstract class AbstractAgent {

	/**
	 * Returns the agent's action in response to the dirtiness state of the
	 * current square.
	 */
	public abstract Action react(boolean dirty);

}
