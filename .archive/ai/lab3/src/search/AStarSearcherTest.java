package search;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by chris on 10/10/16.
 */
public class AStarSearcherTest {
  private Node node;
  private Searcher searcher;

  @Before
  public void setUp() throws Exception {
    node = new FifteenPuzzleNode();
    searcher = new AStarSearcher();
  }

  @Test
  public void search() throws Exception {
    node = new FifteenPuzzleNode("ABCDEFGHIJKLMN.O");
    Character[] answer = {'r'};
    LinkedList<Character> solution = searcher.search(node);
    Character[] solutionArray = solution.toArray(new Character[solution.size()]);

    assertArrayEquals(answer, solutionArray);

    Searcher searcher1 = new AStarSearcher();
    Node node1 = new FifteenPuzzleNode("ABCDEFGHIJ.LMNKO");
    System.out.println("Node:");
    System.out.println(node1);
    LinkedList<Character> solution1 = searcher1.search(node1);
    System.out.println("Solution:");
    System.out.println(solution1);

    for (Character move : solution1) {
      node1 = node1.perform(move);
    }

    assertEquals("ABCDEFGHIJKLMNO.", node1.toString());

    Node node2 = new FifteenPuzzleNode("ABC.EFGDIJKHMNOL");
    System.out.println("Node:");
    System.out.println(node2);
    LinkedList<Character> solution2 = searcher.search(node2);
    System.out.println("Solution:");
    System.out.println(solution2);

    for (Character move : solution2) {
      node2 = node2.perform(move);
    }

    assertEquals("ABCDEFGHIJKLMNO.", node2.toString());

    // Handles unsolvable problems
    Node node3 = new FifteenPuzzleNode("ABC.EFGDIJKHMNOL");
    LinkedList<Character> solution3 = searcher.search(node3);
    assertEquals(0, solution3.size());
  }

}