package search;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class FifteenPuzzleNodeTest {

	private Node node;

	@Before
	public void setUp() throws Exception {
		node = new FifteenPuzzleNode();
	}

	@Test
	public void testExpand() {
		assertEquals("[ABCDEFGHIJKLM.NO, ABCDEFGHIJKLMNO., ABCDEFGHIJ.LMNKO]", new FifteenPuzzleNode("ABCDEFGHIJKLMN.O").expand().toString());
	}

	@Test
	public void testEvaluate() {
		assertEquals(3, new FifteenPuzzleNode("ABCDEFGHIJO.MNLK").getHeuristicEvaluation(), 0.01);
	}

	@Test
	public void testIsGoal() {
		assertTrue(node.isGoal());
		assertFalse(new FifteenPuzzleNode("ABCDEFGHIJKLMN.O").isGoal());
	}

	@Test
	public void testPerform() {
		node = node.perform('l');
		assertEquals("ABCDEFGHIJKLMN.O", node.toString());
		node = node.perform('u');
		assertEquals("ABCDEFGHIJ.LMNKO", node.toString());
		node = node.perform('r');
		assertEquals("ABCDEFGHIJL.MNKO", node.toString());
		node = node.perform('d');
		assertEquals("ABCDEFGHIJLOMNK.", node.toString());
	}

}
