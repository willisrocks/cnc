package othello;

/** A program (or interface to a human) that can select moves. */
public interface Player {

	/** Returns a legal move (0-63 or State.PASS) from state. */
	public int move(State state);
	
}
