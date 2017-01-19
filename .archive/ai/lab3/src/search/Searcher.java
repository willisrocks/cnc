package search;

import java.util.LinkedList;
import java.util.Map;

public interface Searcher {

	/** Add node to the frontier of nodes to be explored next. */
	public void addToFrontier(Node node);

	/** Returns true if node is too deep to put on the frontier. */
	public boolean cutoff(Node node);

	/** Returns true if there are no nodes to explore. */
	public boolean frontierIsEmpty();

	/**
	 * Returns the number of nodes generated since this searcher was created or
	 * resetExpandedCount() was last called.
	 */
	public int getNodeCount();

	/** Creates the frontier data structure (which varies between subclasses). */
	public void initializeFrontier();

	/**
	 * Returns true if the state represented by node appears in map at a lower
	 * depth than node. This indicates that there is no point in adding node to
	 * the frontier or expanding it.
	 */
	public boolean inShallower(Node node, Map<String, Integer> map);

	/** Removes the next node to be explored from the frontier and returns it. */
	public Node removeFromFrontier();

	/** Resets the count of generated nodes to 0. */
	public void resetNodeCount();

	/**
	 * Returns a solution to problem. If there is no solution, may return null
	 * or run out of memory, depending on the subclass.
	 */
	public LinkedList<Character> search(Node problem);

}