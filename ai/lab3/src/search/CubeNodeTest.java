//package search;
//
//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.Test;
//
//public class CubeNodeTest {
//
//	private Node node;
//
//	@Before
//	public void setUp() throws Exception {
//		node = new CubeNode();
//	}
//
//
//	@Test
//	public void testPerform() {
//		assertEquals("002002002111111111225225225333333333044044044554554554", node.perform('r').getState());
//	}
//
//	@Test
//	public void testPerformMultiple() {
//		node = node.perform('u');
//		node = node.perform('l');
//		node = node.perform('f');
//		node = node.perform('R');
//		node = node.perform('B');
//		node = node.perform('D');
//		assertEquals("111404221213515222000220104434430554555144315033353320", node.toString());
//	}
//
//}
