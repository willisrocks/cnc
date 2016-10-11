/**
Does a breadth-first search (FIFO)
new nodes are appended to a linked list 
and removed from the beginning, uses add() and remove()

 **/


package search;

import java.util.LinkedList;
import java.util.Map;

public class BreadthFirstSearcher extends AbstractSearcher {
    LinkedList<Node> queue;

	/** Add node to the frontier of nodes to be explored next. */
    public void addToFrontier(Node node) {
	queue.add(node);
    }

	/** Returns true if node is too deep to put on the frontier. */
    public boolean cutoff(Node node) {
	return false;
    }

	/** Returns true if there are no nodes to explore. */
    public boolean frontierIsEmpty() {
	return (queue.size() == 0);
    }

	/**
	 * Returns the number of nodes generated since this searcher was created or
	 * resetExpandedCount() was last called.
	 */
    public int getNodeCount() {
	return queue.size();
    }

	/** Creates the frontier data structure (which varies between subclasses). */
    public void initializeFrontier() {
	queue = new LinkedList<Node>();
    }

	/**
	 * Returns true if the state represented by node appears in map at a lower
	 * depth than node. This indicates that there is no point in adding node to
	 * the frontier or expanding it.
	 */
    public boolean inShallower(Node node, Map<String, Integer> map) {
	return true;
    }

	/** Removes the next node to be explored from the frontier and returns it. */
    public Node removeFromFrontier(){
	return queue.remove();
    }

	/** Resets the count of generated nodes to 0. */
    public void resetNodeCount() {}

	/**
	 * Returns a solution to problem. If there is no solution, may return null
	 * or run out of memory, depending on the subclass.
	 */
    public LinkedList<Character> search(Node problem) {
		// Add root to queue
		// Pop queue
		// is item == goal
		// add children to queue
		return null;
    }

}
