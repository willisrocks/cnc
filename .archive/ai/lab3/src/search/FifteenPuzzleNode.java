package search;

/**
 * A sliding block puzzle. Actions indicate the direction the space ('.') is to
 * be moved.
 */
public class FifteenPuzzleNode extends AbstractNode {

	/** Legal action sets for each position of the space. */
	public static final String[] ACTION_SETS = { "rd", "lrd", "lrd", "ld",
			"rud", "lrud", "lrud", "lud", "rud", "lrud", "lrud", "lud", "ru",
			"lru", "lru", "lu" };

	/** The goal state. */
	public static final String GOAL = "ABCDEFGHIJKLMNO.";

	/**
	 * Prints a scrambled puzzle state. As written, it produces a state that is
	 * VERY hard to solve.
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			Node node = new FifteenPuzzleNode().scramble(20);
			System.out.println(node);
		}
	}

	/** Index of the space ('.'). */
	private int spacePosition;

	/** Goal node. */
	public FifteenPuzzleNode() {
		this(GOAL);
	}

	/**
	 * Root node for a specific puzzle.
	 * 
	 * @see #FifteenPuzzleNode(String, Node, char, int)
	 */
	public FifteenPuzzleNode(String tiles) {
		this(tiles, null, '?', 0);
	}

	/**
	 * @param tiles
	 *            Positions of tiles, reading across the rows.
	 * @see AbstractNode#AbstractNode(String, Node, char, int)
	 */
	FifteenPuzzleNode(String tiles, Node parent,
			char action, int depth) {
		super(tiles, parent, action, depth);
		spacePosition = tiles.indexOf('.');
	}

	/** Returns the number of tiles out of place (not counting the blank). */
	@Override
	public double evaluate() {
		int result = 0;
		String tiles = getState();
		for (int i = 0; i < tiles.length(); i++) {
			if ((tiles.charAt(i) != '.') && (tiles.charAt(i) - 'A' != i)) {
				result++;
			}
		}
		return result;
	}

	@Override
	public String getAvailableActions() {
		return ACTION_SETS[spacePosition];
	}

	/** Returns the position of the space ('.'). */
	protected int getSpacePosition() {
		return spacePosition;
	}

	@Override
	public boolean isGoal() {
		return getState().equals(GOAL);
	}

	@Override
	public char opposite(char action) {
		return "udlr".charAt("durl".indexOf(action));
	}

	@Override
	public Node perform(char action) {
		int i = spacePosition;
		char[] t = getState().toCharArray();
		int j;
		if (action == 'l') {
			j = i - 1;
		} else if (action == 'r') {
			j = i + 1;
		} else if (action == 'u') {
			j = i - 4;
		} else {
			j = i + 4;
		}
		t[i] = t[j];
		t[j] = '.';
		return new FifteenPuzzleNode(new String(t), this, action,
				getDepth() + 1);
	}

}
