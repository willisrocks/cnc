package othello;

/**
 * Created by chris on 11/4/16.
 */
public class MinimaxPlayer implements Player {
    public int depth;

    private class Node {
        private int pos;
        private int score;
        private State state;
        private Node parent;

        public Node(int pos, int score, State state, Node parent) {
            this.pos = pos;
            this.score = score;
            this.state = state;
            this.parent = parent;
        }

        public int getPos() {
            return this.pos;
        }

        public int getScore() {
            return this.score;
        }

        public Node getParent() {
            return this.parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public State getState() {
            return this.state;
        }

        public Node max(Node b) {
            if (this.getScore() > b.getScore()) {
                return this;
            }
            return b;
        }

        public Node min(Node b) {
            if (this.getScore() < b.getScore()) {
                return this;
            }
            return b;
        }

        public int getMove() {
            Node bullshitNode = this;
            while (bullshitNode.getParent().getParent() != null) {
                bullshitNode = bullshitNode.getParent();
            }
            return bullshitNode.getPos();

        }
    }


    public MinimaxPlayer(int depth) {
        this.depth = depth;
    }

    @Override
    public int move(State state) {
        Node root = new Node(-1, 0, state, null);
        Node winningNode = minimax(root, depth, true);

        return winningNode.getMove();
    }

    private Node minimax(Node node, int depth, boolean maximizingPlayer) {
        if (depth == 0 || node.getState().gameOver()) {
//        if (depth == 0 || node.getState().legalMoves().size() == 0) {
            return node;
        }

        if (maximizingPlayer) {
            Node bestNode = new Node(-1, -999, null, null);
            for (int[] move : node.getState().legalMovesFlips()) {
                State newState = node.state.copy();
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
                State newState = node.state.copy();
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
