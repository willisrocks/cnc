package othello;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.round;
import static othello.State.PASS;
import static othello.State.WIDTH;
import static othello.StdDraw.mousePressed;
import static othello.StdDraw.mouseX;
import static othello.StdDraw.mouseY;

/**
 * Created by chris on 10/28/16.
 */
public class MinimaxPlayer implements Player {
  private int depth = 0;

  public MinimaxPlayer(int depth) {
    this.depth = depth;
  }

  @Override
  public int move(State board) {
    return miniMax(board, depth, true);
  }

  @Override
  public String toString() {
    return "Minimax Player";
  }

  public int miniMax(State board, int d, boolean maximizingPlayer) {
    if (d == 0 || board.gameOver()) {

      return -1;
    }
    }
    if (maximizingPlayer) {
      int bestValue = -9999;

    }
  }

  public int max(State board) {
    ArrayList<Integer> positions = (ArrayList<Integer>) board.legalMoves();
    System.out.println(positions);
    HashMap<Integer, Integer> scores = new HashMap<>();
    for (int pos : positions) {
      State tempBoard = board.copy();
      tempBoard.play(pos);
      scores.put(tempBoard.score(), pos);
    }
    return Collections.max(scores.keySet());
  }

}
