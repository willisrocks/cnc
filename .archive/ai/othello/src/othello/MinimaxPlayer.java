package othello;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MinimaxPlayer implements Player {
    public int depth;

    // Node comparator
    public static Comparator<Node> nodeComparator = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.getScore() - o2.getScore();
        }
    };


    public MinimaxPlayer(int depth) {
        this.depth = depth;
    }

    @Override
    public int move(State state) {
        // Priority queue of possible moves with minimax scores
        Queue<Node> movesQueue = new PriorityQueue<>(5, nodeComparator);

        for (int[] move : state.legalMovesFlips()) {
            // Create new state for the move
            State newState = state.copy();
            newState.play(move[0]);

            // Create the move node
            Node moveRoot = new Node(move[0], move[1], newState, null);

            // Get the minimax leaf node
            Node moveLeaf = minimax(moveRoot, depth, true);

            // Update the root node's score
            moveRoot.setScore(moveLeaf.getScore());

            // Add the root to the pq
            movesQueue.add(moveRoot);
        }
        // get the pos with the best score
        return movesQueue.poll().getPos();
    }

    private Node minimax(Node node, int depth, boolean maximizingPlayer) {
        if (depth == 0 || node.getState().gameOver()) {
            return node;
        }

        if (maximizingPlayer) {
            Node bestNode = new Node(-1, -999, null, null);
            for (int[] move : node.getState().legalMovesFlips()) {
                State newState = node.getState().copy();
                newState.play(move[0]);
                Node child = new Node(move[0], move[1], newState, node);
                Node v = minimax(child, depth - 1, false);
                bestNode = bestNode.max(v);
            }
            return bestNode;

        } else {
            // minimizing player
            Node bestNode = new Node(-1, 999, null, null);
            for (int[] move : node.getState().legalMovesFlips()) {
                State newState = node.getState().copy();
                newState.play(move[0]);
                Node child = new Node(move[0], move[1], newState, node);
                Node v = minimax(child, depth - 1, true);
                bestNode = bestNode.min(v);
            }
            return bestNode;
        }


    }

    public static void main(String[] args) {
        Player min = new MinimaxPlayer(3);
        State board = new State();
        System.out.println(board.legalMoves());
        int move = min.move(board);
        System.out.println(move);
        board.play(move);
        System.out.println(board.legalMoves());
    }

}
