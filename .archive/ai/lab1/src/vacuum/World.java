package vacuum;

import static vacuum.Action.*;
import java.util.Random;


/** The vacuum world. */
public class World {
    static Random rand = new Random();

	/**
	 * Runs an agent in the world for many steps, many times, and reports the
	 * total score.
	 */
	public static void main(String[] args) {
		int sum = 0;
		for (int i = 0; i < 100; i++) {
			World world = new World(25);
			AbstractAgent agent = new ReflexAgent();
			sum += world.simulate(agent, 10000);
		}
		System.out.println(sum / 100);
	}
	
	/** The agent's x coordinate (0-based from left). */
	private int agentX;

	/** The agent's y coordinate (0-based from bottom). */
	private int agentY;
	
	/** The squares in the world. */
	private Square[][] squares;

	public World(int width) {
		// Create the squares
		squares = new Square[width][width];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < width; y++) {
				squares[x][y] = new Square();
			}
		}
		setUp(width);
		// Place the agent
		// If the entire world is full of obstacles, this loop runs forever
		while (true) {
			agentX = rand.nextInt(width);
			agentY = rand.nextInt(width);
			if (!getSquare(agentX, agentY).isObstacle()) {
				return;
			}
		}
	}

	/** Returns the agent's x coordinate (0-based from left). */
	public int getAgentX() {
		return agentX;
	}

	/** Returns the agent's y coordinate (0-based from bottom). */
	public int getAgentY() {
		return agentY;
	}

	/** Returns the square at coordinates x, y. */
	public Square getSquare(int x, int y) {
		return squares[x][y];
	}

	/** Returns the width of the world. */
	public int getWidth() {
		return squares.length;
	}

	/** Places obstacles and dirt in this world. */
	void setUp(int width) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < width; y++) {
				Square square = squares[x][y];
				if (rand.nextFloat() < 0.5) {
					square.setDirty(true);
				}
				if (rand.nextFloat() < 0.1) {
					square.setObstacle(true);
				}
			}
		}
	}

	/**
	 * Runs the agent for the specified number of steps. Returns the agent's
	 * score: the sum, over all steps, of the number of clean squares and
	 * obstacles.
	 */
	public int simulate(AbstractAgent agent, int steps) {
		int score = 0;
		for (int t = 0; t < steps; t++) {
			step(agent);
			for (int x = 0; x < getWidth(); x++) {
				for (int y = 0; y < getWidth(); y++) {
					if (squares[x][y].isObstacle() || !squares[x][y].isDirty()) {
						score++;
					}
				}
			}
		}
		return score;
	}

	/**
	 * Performs one step of simulation: feeds the agent a percept and performs
	 * the agent's action.
	 */
	public void step(AbstractAgent agent) {
		Square square = getSquare(agentX, agentY);
		boolean percept = square.isDirty();
		Action action = agent.react(percept);
		int x = agentX; // Destination x coordinate
		int y = agentY; // Destination y coordinate
		if (action == SUCK) {
			square.setDirty(false);
		} else if (action == UP) {
			if (agentY < getWidth() - 1) {
				y++;
			}
		} else if (action == DOWN) {
			if (agentY > 0) {
				y--;
			}
		} else if (action == LEFT) {
			if (agentX > 0) {
				x--;
			}
		} else if (action == RIGHT) {
			if (agentX < getWidth() - 1) {
				x++;
			}
		}
		if (!getSquare(x, y).isObstacle()) {
			agentX = x;
			agentY = y;
		}
	}

}
