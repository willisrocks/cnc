package othello;

import java.util.*;

public class Tournament {

	/**
	 * Number of games with each particular black-white pairing. With strictly
	 * deterministic players liked MinimaxPlayer and AlphaBetaPlayer, there is
	 * no reason to turn this above 1.
	 */
	public static final int GAMES_PER_CONDITION = 1;

	/**
	 * Maximum time (in nanoseconds) allowed for all moves played by one player in a game.
	 */
	public static final long TIME_LIMIT = 60 * 1000000000L;

	public static void main(String[] args) {
		new Tournament().run();
	}

	/** Players in the tournament. */
	private List<Player> players;

	public Tournament() {
		players = new ArrayList<Player>();
		for (int depth = 1; depth <= 3; depth++) {
			players.add(new MinimaxPlayer(depth));
		}
	}

	/** Plays one game between two players. Returns 1 if black wins, 0 if white wins, 0.5 in case of a tie. */
	double playGame(Player black, Player white) {
		System.out.println(black + " vs " + white);
		long[] elapsedTime = new long[2];
		State board = new State();
		while (!board.gameOver()) {
			int move;
			long before = System.nanoTime();
			if (board.getColorToPlay() == 'X') {
				move = black.move(board);
				elapsedTime[0] += System.nanoTime() - before;
				if (elapsedTime[0] > TIME_LIMIT) {
					System.out.println("BLACK FORFEITS ON TIME");
					return 0;
				}
			} else {
				move = white.move(board);
				elapsedTime[1] += System.nanoTime() - before;
				if (elapsedTime[1] > TIME_LIMIT) {
					System.out.println("WHITE FORFEITS ON TIME");
					return 1;
				}
			}
			if (board.legalMoves().contains(move)) {
				board.play(move);
			} else {
				System.out.println("Illegal move!");
			}
		}
		System.out.println("Elapsed time (nanoseconds):");
		System.out.println("Black: " + elapsedTime[0]);
		System.out.println("White: " + elapsedTime[1]);
		if (board.score() > 0) {
			return 1.0;
		} else if (board.score() < 0) {
			return 0.0;
		} else {
			return 0.5;
		}
	}

	/** Runs the tournament. */
	public void run() {
		int n = players.size();
		double[][] wins = new double[n][n];
		for (int b = 0; b < players.size(); b++) {
			for (int w = 0; w < players.size(); w++) {
				if (b != w) {
					for (int game = 0; game < GAMES_PER_CONDITION; game++) {
						double result = playGame(players.get(b), players.get(w));
						wins[b][w] += result;
						wins[w][b] += 1.0 - result;
					}
				}
			}
		}
		for (int b = 0; b < players.size(); b++) {
			System.out.print(players.get(b) + "\t");
			double sum = 0.0;
			for (int w = 0; w < n; w++) {
				System.out.print(wins[b][w] + "\t");
				sum += wins[b][w];
			}
			System.out.println("Total: " + sum);
		}
		System.out.println("Elapsed time (nanoseconds):");
	}

}
