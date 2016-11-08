package othello;

public class Node {
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

    public void setScore(int score) { this.score = score; }

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
