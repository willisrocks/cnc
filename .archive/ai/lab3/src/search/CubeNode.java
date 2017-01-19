package search;

import static java.lang.Character.*;

public class CubeNode extends AbstractNode {

	/** Lower-case letters are clockwise turns, upper-case counterclockwise. */
	public static final String ACTIONS = "ulfrbdULFRBD";

	/** The goal state. */
	public static final String GOAL = "000000000111111111222222222333333333444444444555555555";

	/**
	 * TRANSFORMATIONS[f][i] indicates which facet moves to position i when face
	 * f is turned clockwise.
	 */
	public static final int[][] TRANSFORMATIONS = {
			{ 6, 3, 0, 7, 4, 1, 8, 5, 2, 18, 19, 20, 12, 13, 14, 15, 16, 17,
					27, 28, 29, 21, 22, 23, 24, 25, 26, 36, 37, 38, 30, 31, 32,
					33, 34, 35, 9, 10, 11, 39, 40, 41, 42, 43, 44, 45, 46, 47,
					48, 49, 50, 51, 52, 53 },
			{ 44, 1, 2, 41, 4, 5, 38, 7, 8, 15, 12, 9, 16, 13, 10, 17, 14, 11,
					0, 19, 20, 3, 22, 23, 6, 25, 26, 27, 28, 29, 30, 31, 32,
					33, 34, 35, 36, 37, 51, 39, 40, 48, 42, 43, 45, 18, 46, 47,
					21, 49, 50, 24, 52, 53 },
			{ 0, 1, 2, 3, 4, 5, 17, 14, 11, 9, 10, 45, 12, 13, 46, 15, 16, 47,
					24, 21, 18, 25, 22, 19, 26, 23, 20, 6, 28, 29, 7, 31, 32,
					8, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 33, 30, 27,
					48, 49, 50, 51, 52, 53 },
			{ 0, 1, 20, 3, 4, 23, 6, 7, 26, 9, 10, 11, 12, 13, 14, 15, 16, 17,
					18, 19, 47, 21, 22, 50, 24, 25, 53, 33, 30, 27, 34, 31, 28,
					35, 32, 29, 8, 37, 38, 5, 40, 41, 2, 43, 44, 45, 46, 42,
					48, 49, 39, 51, 52, 36 },
			{ 29, 32, 35, 3, 4, 5, 6, 7, 8, 2, 10, 11, 1, 13, 14, 0, 16, 17,
					18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 53, 30, 31, 52,
					33, 34, 51, 42, 39, 36, 43, 40, 37, 44, 41, 38, 45, 46, 47,
					48, 49, 50, 9, 12, 15 },
			{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 42, 43, 44, 18,
					19, 20, 21, 22, 23, 15, 16, 17, 27, 28, 29, 30, 31, 32, 24,
					25, 26, 36, 37, 38, 39, 40, 41, 33, 34, 35, 51, 48, 45, 52,
					49, 46, 53, 50, 47 } };

	/**
	 * Prints a scrambled cube state. As written, it produces a cube that is
	 * VERY hard to solve.
	 */
	public static void main(String[] args) {
		Node node = new CubeNode().scramble(50);
		System.out.println(node);
	}

	/** Creates a goal node. */
	public CubeNode() {
		this(GOAL);
	}

	/**
	 * @param facets
	 *            Colors across the faces (u, l, f, r, b, d), left to right
	 *            across rows of each face.
	 */
	public CubeNode(String facets) {
		this(facets, null, '?', 0);
	}

	/** @see #CubeNode(String) */
	CubeNode(String facets, CubeNode parent, char action, int depth) {
		super(facets, parent, action, depth);
	}

	/**
	 * Returns the number of facets of out place, divided by 12. (One move could
	 * fix up to 12 facets.)
	 */
	public double evaluate() {
		int sum = 0;
		String facets = getState();
		for (int i = 0; i < facets.length(); i++) {
			if (facets.charAt(i) != GOAL.charAt(i)) {
				sum++;
			}
		}
		return sum / 12.0;
	}

	@Override
	public String getAvailableActions() {
		return "ulfrbdULFRBD";
	}

	@Override
	public boolean isGoal() {
		return getState().equals(GOAL);
	}

	@Override
	public char opposite(char action) {
		return "ULFRBDulfrbd".charAt(ACTIONS.indexOf(action));
	}

	@Override
	public Node perform(char action) {
		String facets = getState();
		char[] result = new char[facets.length()];
		int[] transformation = TRANSFORMATIONS[ACTIONS
				.indexOf(toLowerCase(action))];
		if (isLowerCase(action)) {
			for (int i = 0; i < result.length; i++) {
				result[i] = facets.charAt(transformation[i]);
			}
		} else {
			for (int i = 0; i < result.length; i++) {
				result[transformation[i]] = facets.charAt(i);
			}
		}
		return new CubeNode(new String(result), this, action, getDepth() + 1);
	}

}
