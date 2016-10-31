package othello;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by chris on 10/31/16.
 */
public class StateTest {
  public static void main(String[] args) {
    State board = new State();

//    MinimaxPlayer player = new MinimaxPlayer(1);
//    player.max(board);

//    State thisBoard = board.copy();
//    ArrayList<Integer> positions = (ArrayList<Integer>) thisBoard.legalMoves();
//    ArrayList<Integer> scores = new ArrayList<>(positions.size());
//    for (int move : positions) {
//      State tempBoard = thisBoard.copy();
//      tempBoard.play(move);
//      int score = tempBoard.score();
//      scores.add(positions.indexOf(move), score);
//    }
//    System.out.println(scores);
//    System.out.println("Max: " + Collections.max(scores));
//    State thisBoard = board.copy();
//    ArrayList<Integer> positions = (ArrayList<Integer>) thisBoard.legalMoves();
//    HashMap<Integer, Integer> scores = new HashMap<>();
//    scores.put(24, 0);
//    scores.put(10, 1);
//    scores.put(30, 2);
//    System.out.println(scores);
//    int max = Collections.max(scores.keySet());
//    System.out.println(scores.get(max));
    ArrayList<Integer> positions = (ArrayList<Integer>) board.legalMoves();
    System.out.println(positions);
    HashMap<Integer, Integer> scores = new HashMap<>();
//    scores.put(24, 0);
    for (int pos : positions) {
      State tempBoard = board.copy();
      tempBoard.play(pos);
      scores.put(tempBoard.score(), pos);
    }
    System.out.println(Collections.max(scores.keySet()));



//    System.out.println(board.legalMoves());
//    board.play(20);
//    System.out.println(board.score());
//    System.out.println(board.legalMoves());
//    System.out.println(board.legalMoves());
//    board.play(19);
//    System.out.println(board.legalMoves());
//    board.play(10);
//    System.out.println(board.legalMoves());
//    board.play(11);
//    System.out.println(board.legalMoves());
//    board.play(2);
//    System.out.println(board.legalMoves());
//    board.play(1);
//    System.out.println(board.legalMoves());
//    board.play(0);
//    System.out.println(board.legalMoves());
//    while (board.legalMoves().size() > 1) {
//      board.play(board.legalMoves().get(0));
//      System.out.println("Moves: " + board.legalMoves());
//      System.out.println("Score: " + board.score());
//    }


    System.out.println(board);

  }

}
