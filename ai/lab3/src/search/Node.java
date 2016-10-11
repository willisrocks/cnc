package search;

import java.util.LinkedList;

public interface Node extends Comparable<Node> {

	/**
	 * Compares nodes based on A* value (depth + heuristic), breaking ties by
	 * comparing state Strings.
	 */
	public int compareTo(Node that);

	/**
	 * Returns the estimated number of remaining moves to reach the goal.
	 * Subclasses providing heuristics should override this.
	 */
	public double evaluate();

	/** Returns a list of nodes that can be reached in one action from this one. */
	public LinkedList<Node> expand();

	/** Returns a String of the actions available from this node. */
	public String getAvailableActions();

	/** Returns the number of actions from the root node to this one. */
	public int getDepth();

	/**
	 * Returns a heuristic evaluation of this node. Lower numbers correspond to
	 * more promising nodes; a goal node should evaluate to 0. The heuristic
	 * should be admissible.
	 */
	public double getHeuristicEvaluation();

	/** Returns the state associated with this node. */
	public String getState();

	/** Returns true if this is a goal node. */
	public boolean isGoal();

	/**
	 * Returns the opposite of action a, that is, the action that undoes a. It
	 * is an error to call this method with an argument that is not an action
	 * for this type of node.
	 */
	public char opposite(char a);

	/**
	 * Returns a sequence of moves leading from the start node to this node.
	 */
	public LinkedList<Character> path();

	/** Returns a node resulting from performing action on the state in this node. */
	public Node perform(char action);

	/**
	 * Performs n random actions, starting from this node. Moves that would
	 * directly cancel the preceding move are excluded. Returns the resulting
	 * node.
	 */
	public Node scramble(int n);

	public String toString();

	/** Returns the parent of this node. */
	public Node getParent();

	/** Returns the action taken to reach this node. */
	public char getAction();

}