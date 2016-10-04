package vacuum;

/**
 * Move class provides a record with
 * and Action action and a boolean obstacle.
 * Used to keep a history of actions and obstacles.
 */

public class Move {
    private Action action;
    private boolean obstacle;

    public Move(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public boolean blocked() {
        return obstacle;
    }

    public void setObstacle(boolean blocked) {
        this.obstacle = blocked;
    }

    public String toString() {
        return "{Action: " + this.getAction() + ", Blocked: " + this.blocked() + "}";
    }
}
