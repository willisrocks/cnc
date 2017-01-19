package search;

import static java.lang.Math.abs;

/** Provides a better heuristic than EightPuzzleNode. */
public class ManhattanFifteenPuzzleNode extends FifteenPuzzleNode {

	/** Goal node. */
	public ManhattanFifteenPuzzleNode() {
		this(GOAL);
	}
	
	/**
	 * Root node for a specific puzzle.
	 * @see FifteenPuzzleNode#FifteenPuzzleNode(String, Node, char, int)
	 */
	public ManhattanFifteenPuzzleNode(String tiles) {
		this(tiles, null, '?', 0);
	}

	/**
	 * @param tiles Positions of tiles, reading across the rows.
	 * @see AbstractNode#AbstractNode(Node, char, int)
	 */
	ManhattanFifteenPuzzleNode(String tiles, Node parent, char action, int depth) {
		super(tiles, parent, action, depth);
	}

	@Override
	public Node perform(char action) {
		Node temp = super.perform(action);
		return new ManhattanFifteenPuzzleNode(temp.getState(), temp.getParent(), temp.getAction(), temp.getDepth());
	}
	
	/** Returns the sum of the Manhattan distances between tiles' actual and correct positions. */
	@Override
	public double evaluate() {
		int result = 0;
		String tiles = getState();
		for (int i = 0; i < tiles.length(); i++) {
			if (tiles.charAt(i) != '.') {
				// First argument is where this tile should be
				// Second argument is where it is
				result += manhattanDistance(tiles.charAt(i) - 'A', i);
			}
		}
		return result;
	}

	/** Returns the Manhattan distance between positions i and j. */
	int manhattanDistance(int i, int j) {
		return abs((i / 4) - (j / 4)) + abs((i % 4) - (j % 4));
	}

}
