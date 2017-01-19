package search;

import java.util.LinkedList;
import java.util.Random;

/**
 * A node in a search problem.
 */
public abstract class AbstractNode implements Node {

	/** The action taken to reach this node. */
	private final char action;

	/** The number of actions from the root node to this one. */
	private int depth;

	/** Admissible estimate of the number of remaining moves to reach the goal. */
	private double heuristicEvaluation;

	/** The node from which this node was reached. */
	private Node parent;

	/** The problem state. */
	private final String state;

    private Random rand = new Random();

	/** Creates a node with the specified field values. */
	public AbstractNode(String state, Node parent, char action,
			int depth) {
		this.state = state;
		this.parent = parent;
		this.action = action;
		this.depth = depth;
		// Indicate that this node has not yet been evaluated
		this.heuristicEvaluation = -1;
	}

	@Override
	public int compareTo(Node that) {
		double d = (getDepth() + getHeuristicEvaluation())
				- (that.getDepth() + that.getHeuristicEvaluation());
		if (d < 0) {
			return -1;
		} else if (d > 0) {
			return 1;
		} else {
			return state.compareTo(that.getState());
		}
	}

	@Override
	public double evaluate() {
		return 0;
	}

	@Override
	public LinkedList<Node> expand() {
		LinkedList<Node> children = new LinkedList<Node>();
		String actions = getAvailableActions();
		for (int i = 0; i < actions.length(); i++) {
			char a = actions.charAt(i);
			children.add(perform(a));
		}
		return children;
	}
	
	@Override
	public char getAction() {
		return action;
	}
	
	@Override
	public abstract String getAvailableActions();


	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public double getHeuristicEvaluation() {
		// If this is the first time this method has been called on this node,
		// evaluate it and store the result
		if (heuristicEvaluation == -1) {
			heuristicEvaluation = evaluate();
		}
		return heuristicEvaluation;
	}

	@Override
	public Node getParent() {
		return parent;
	}

	@Override
	public String getState() {
		return state;
	}

	@Override
	public abstract boolean isGoal();

	@Override
	public abstract char opposite(char a);

	@Override
	public LinkedList<Character> path() {
		LinkedList<Character> path = new LinkedList<Character>();
		for (Node n = this; n.getParent() != null; n = n.getParent()) {
			path.addFirst(n.getAction());
		}
		return path;
	}

	@Override
	public abstract Node perform(char action);

	@Override
	public AbstractNode scramble(int n) {
		AbstractNode node = this;
		for (int i = 0; i < n; i++) {
			LinkedList<Node> children = node.expand();
			Node temp;
			do {
				temp = children.get(rand.nextInt(children.size()));
			} while (opposite(temp.getAction()) == action);
			node = (AbstractNode)temp;
		}
		node.parent = null;
		node.depth = 0;
		return node;
	}

	@Override
	public String toString() {
		return state;
	}

}
