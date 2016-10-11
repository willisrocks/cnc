package search;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by chris on 10/10/16.
 */
public class BreadthFirstSearcherTest {
  private Node node;
  private Searcher searcher;

  @Before
  public void setUp() throws Exception {
    node = new FifteenPuzzleNode();
    searcher = new BreadthFirstSearcher();
  }

  @Test
  public void search() throws Exception {
    node = new FifteenPuzzleNode("ABCDEFGHIJKLMN.O");
    Character[] answer = {'r'};
    LinkedList<Character> solution = searcher.search(node);
    Character[] solutionArray = solution.toArray(new Character[solution.size()]);

    assertArrayEquals(answer, solutionArray);

    Searcher searcher1 = new BreadthFirstSearcher();
    Node node1 = new FifteenPuzzleNode("ABCDEFGHIJ.LMNKO");
    System.out.println("Node:");
    System.out.println(node1);
    LinkedList<Character> solution2 = searcher1.search(node1);
    System.out.println("Solution:");
    System.out.println(solution2);

    for (Character move : solution2) {
      node1 = node1.perform(move);
    }

    assertEquals("ABCDEFGHIJKLMNO.", node1.toString());
  }

}