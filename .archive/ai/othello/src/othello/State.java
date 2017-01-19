package othello;

import java.util.*;

/** A state of the Othello game. */
public class State {

	/** A pass move, when there are no captures available. */
	public static final int PASS = -1;

	/** Width of the board, to avoid magic numbers in code. */
	public static final int WIDTH = 8;

	/**
	 * Returns the opposite color. opposite('X') => 'O' and vice versa. The
	 * value returned for any other input is unspecified.
	 */
	public static char opposite(char color) {
		if (color == 'X') {
			return 'O';
		}
		return 'X';
	}

	/**
	 * Colors of squares on the board, either 'X', 'O', or '.';
	 */
	private char[][] squares;

	/**
	 * The color to play next, 'X' or 'O'.
	 */
	private char colorToPlay;

	/**
	 * Pairs of row and column offsets. Used by methods such as flips().
	 */
	public static final int[][] OFFSETS = { { -1, -1 }, { -1, 0 }, { -1, 1 },
			{ 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

	public State() {
		this(new char[][] {
				"........".toCharArray(),
				"........".toCharArray(),
				"........".toCharArray(),
				"...XO...".toCharArray(),
				"...OX...".toCharArray(),
				"........".toCharArray(),
				"........".toCharArray(),
				"........".toCharArray() },
				'X');
	}

	State(char[][] squares, char colorToPlay) {
		this.squares = squares;
		this.colorToPlay = colorToPlay;
	}

	/**
	 * Returns a new State that is equals(), but not ==, to this one. (This is
	 * equivalent to Java's standard, despised clone() idiom, but without the
	 * baggage.)
	 */
	public State copy() {
		char[][] result = new char[8][];
		for (int r = 0; r < result.length; r++) {
			result[r] = Arrays.copyOf(squares[r], WIDTH);
		}
		return new State(result, colorToPlay);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (getClass() != other.getClass()) {
			return false;
		}
		State that = (State) other;
		return colorToPlay == that.colorToPlay
				&& Arrays.deepEquals(squares, that.squares);
	}

	/**
	 * Returns the number of pieces that would be flipped if the current player
	 * were to play at row, column, counting in the direction specified by the
	 * offsets. Assumes that the move is legal.
	 */
	public int flips(int row, int column, int rowOffset, int columnOffset) {
		int count = 0;
		while (true) {
			row += rowOffset;
			column += columnOffset;
			if (row < 0 || row >= WIDTH || column < 0 || column >= WIDTH) {
				return 0; // Edge of board
			}
			if (squares[row][column] == '.') {
				return 0; // Vacant space
			}
			if (squares[row][column] == colorToPlay) {
				return count; // Friendly piece, a capture if count > 0
			}
			count++; // Another enemy piece
		}
	}

	/**
	 * Returns true if neither player has a legal move other than passing.
	 */
	public boolean gameOver() {
		for (int r = 0; r < WIDTH; r++) {
			for (int c = 0; c < WIDTH; c++) {
				if (squares[r][c] == '.') {
					for (int[] offset : OFFSETS) {
						int dr = offset[0];
						int dc = offset[1];
						if (flips(r, c, dr, dc) > 0) {
							return false;
						}
						colorToPlay = opposite(colorToPlay);
						if (flips(r, c, dr, dc) > 0) {
							colorToPlay = opposite(colorToPlay);
							return false;
						}
						colorToPlay = opposite(colorToPlay);
					}
				}
			}
		}
		return true;
	}

	/** Returns the color ('X', 'O', or '.') at row, column. */
	public char get(int row, int column) {
		return squares[row][column];
	}

	/** Returns the current color to play ('X' or 'O'). */
	public char getColorToPlay() {
		return colorToPlay;
	}

	@Override
	public int hashCode() {
		return Arrays.deepHashCode(squares);
	}

	/**
	 * Returns a list of legal moves, each a position from 0-63 reading across
	 * the rows of the board. The list is in increasing order. If there are no
	 * legal moves, returns a list containing only PASS.
	 */
	public List<Integer> legalMoves() {
		List<Integer> result = new ArrayList<Integer>();
		for (int r = 0; r < WIDTH; r++) {
			for (int c = 0; c < WIDTH; c++) {
				if (squares[r][c] == '.') {
					for (int[] offset : OFFSETS) {
						int dr = offset[0];
						int dc = offset[1];
						if (flips(r, c, dr, dc) > 0) {
							result.add(r * WIDTH + c);
							break;
						}
					}
				}
			}
		}
		if (result.isEmpty()) {
			result.add(PASS);
		}
		return result;
	}

	// legals moves with number of flips
	public List<int[]> legalMovesFlips() {
		List<int[]> result = new ArrayList<>();
		for (int r = 0; r < WIDTH; r++) {
			for (int c = 0; c < WIDTH; c++) {
				if (squares[r][c] == '.') {
					for (int[] offset : OFFSETS) {
						int dr = offset[0];
						int dc = offset[1];
						if (flips(r, c, dr, dc) > 0) {
							int move = (r * WIDTH + c);
							int flips = flips(r, c, dr, dc);
							int[] packagedMove = {move, flips};
							result.add(packagedMove);
							break;
						}
					}
				}
			}
		}
		if (result.isEmpty()) {
			int[] emptyPackage = {PASS, 0};
			result.add(emptyPackage);
		}
		return result;
	}

	/**
	 * Modifies THIS State (as opposed to creating a new one) after a move by
	 * the current player at position. Assumes the move is legal.
	 * @param position
	 *            (numbered 0-63 reading across the rows) or PASS.
	 */
	public void play(int position) {
		if (position != PASS) {
			int row = position / WIDTH;
			int column = position % WIDTH;
			for (int[] offset : OFFSETS) {
				int r = row;
				int c = column;
				int dr = offset[0];
				int dc = offset[1];
				// Counting down avoids recomputing flips
				for (int i = flips(r, c, dr, dc); i > 0; i--) {
					r += dr;
					c += dc;
					squares[r][c] = colorToPlay;
				}
			}
			squares[row][column] = colorToPlay;
		}
		colorToPlay = opposite(colorToPlay);
	}

	/**
	 * Returns the relative score, with positive numbers better for X.
	 * If the game is over, returns 100 (X win), 0 (tie), or -100 (O win).
	 */
	public int score() {
		int result = 0;
		for (int r = 0; r < WIDTH; r++) {
			for (int c = 0; c < WIDTH; c++) {
				if (squares[r][c] == 'X') {
					result++;
				} else if (squares[r][c] == 'O') {
					result--;
				}
			}
		}
		if (result != 0 && gameOver()) {
			if (result > 0) {
				return 100;
			} else {
				return -100;
			}
		}
		return result;
	}

	@Override
	public String toString() {
		String result = "";
		for (int r = 0; r < WIDTH; r++) {
			for (int c = 0; c < WIDTH; c++) {
				result += squares[r][c];
			}
			result += "\n";
		}
		return result;
	}

}
