package search;

/** Experiment to test the effectiveness of various search algorithms. */
public class Experiment {

	public static void main(String[] args) {
		for (Node problem : new Node[] { new FifteenPuzzleNode(),
				new ManhattanFifteenPuzzleNode(), 
				new CubeNode()
		}) {
			System.out.println(problem.getClass().getSimpleName());
			Node[][] instances = new Node[21][100];
			for (int depth = 1; depth < instances.length; depth++) {
				for (int i = 0; i < instances[0].length; i++) {
					instances[depth][i] = problem.scramble(depth);
				}
			}
			for (Searcher searcher : new Searcher[]{
					new BreadthFirstSearcher()}) {

//					new BreadthFirstSearcher(),
//					new AStarSearcher() }) {
				try {
					System.out.println(searcher.getClass().getSimpleName());
					for (int depth = 1; depth < instances.length; depth++) {
						int nodeSum = 0;
						long timeSum = 0;
						int lengthSum = 0;
						for (int i = 0; i < instances[0].length; i++) {
							searcher.resetNodeCount();
							long before = System.currentTimeMillis();
							java.util.LinkedList<Character> result = searcher
									.search(instances[depth][i]);
							long after = System.currentTimeMillis();
							lengthSum += result.size();
							timeSum += after - before;
							nodeSum += searcher.getNodeCount();
						}
						System.out
								.printf("Depth %2d: %9.2f nodes\t%9.2f msec\t%9.2f length\n",
										depth, ((double) nodeSum)
												/ instances[0].length,
										((double) timeSum)
												/ instances[0].length,
										((double) lengthSum)
												/ instances[0].length);
					}
				} catch (OutOfMemoryError e) {
					// This searcher can't do problems this difficult; move on
					// to the next searcher
					continue;
				}
			}
			System.out.println();
		}
	}
}
