/**
 * A* algorithm implementation used in finding a solution
 * to puzzle that implements the Node interface.
 * Currently doesn't return a valid path for
 * a unsolvable problem.
 */

package search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class AStarSearcher extends AbstractSearcher {
  private ArrayList<Node> openList;
  private ArrayList<Node> closedList;

  /**
   * Creates a comparator object for a node class to
   * used in sorting a list by f-cost.
   */
  class NodeCompararator implements Comparator<Node> {
    public int compare(Node n1, Node n2) {
      return n1.compareTo(n2);
    }
  }

  public AStarSearcher() {
    initializeFrontier();
  }

  @Override
  public void addToFrontier(Node node) {
    openList.add(node);
  }

  @Override
  public boolean frontierIsEmpty() {
    return openList.isEmpty();
  }

  @Override
  public void initializeFrontier() {
    openList = new ArrayList<>();
    closedList = new ArrayList<>();
  }

  @Override
  public Node removeFromFrontier() {
    return openList.remove(0);
  }

  private void addToClosed(Node node) {
    closedList.add(node);
  }

  private void sortFrontier() {
    openList.sort(new NodeCompararator());
  }

  private boolean frontierContains(Node node) {
    return openList.contains(node);
  }

  private boolean closedListContains(Node node) {
    return closedList.contains(node);
  }

  /**
   * Finds and returns the path from the root of a
   * problem to the goal. Uses the node's compareTo()
   * method to sort the frontier by f-cost.
   */
  @Override
  public LinkedList<Character> search(Node problem) {
    addToFrontier(problem);

    while (!frontierIsEmpty()) {
      sortFrontier(); // sort by f-cost
      Node current = removeFromFrontier();
      addToClosed(current);
      if (current.isGoal()) return current.path();

      for (Node n : current.expand()) {
        if (closedListContains(n)) continue;
        if (!frontierContains(n)) addToFrontier(n);
      }
    }

    return null; // no solution found
  }
}
