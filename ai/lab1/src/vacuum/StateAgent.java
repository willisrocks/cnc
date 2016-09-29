package vacuum;
import java.util.Arrays;

/** An agent for the vacuum world. */
public class StateAgent extends AbstractAgent{

    public boolean blocked = false;
    public Square NORTH, EAST, SOUTH, WEST;
    public int lastDir = -1;
    public Square CURRENT;

    public StateAgent() {
//        this.lastActions[0] = Action.LEFT;
        this.NORTH = new Square();
        this.NORTH.setDirty(true);
        this.EAST = new Square();
        this.EAST.setDirty(true);
        this.SOUTH = new Square();
        this.SOUTH.setDirty(true);
        this.WEST = new Square();
        this.WEST.setDirty(true);
        this.CURRENT = new Square();
        this.CURRENT.setDirty(false);
    }

/**
     * Returns the agent's action in response to the dirtiness state of the
     * current square.
     */
    public  Action react(boolean dirty) {
        if (dirty) {
            return Action.SUCK;
        }
//        return Action.LEFT;
        if (this.lastDir > -1) {
            this.chooseDir();
        }
        else if (this.lastDir == 0) {
            if (this.blocked) {
                this.NORTH.setObstacle(true);
            }
            else {
                this.NORTH.setDirty(false);
            }
            return Action.DOWN;
        }
        else if (this.lastDir == 1) {
            if (this.blocked) {
                this.EAST.setObstacle(true);
            }
            else {
                this.EAST.setDirty(false);
            }
            return Action.LEFT;
        }
        else if (this.lastDir == 2) {
            if (this.blocked) {
                this.SOUTH.setObstacle(true);
            }
            else {
                this.SOUTH.setDirty(false);
            }
            return Action.UP;
        }
        else if (this.lastDir == 3) {
            if (this.blocked) {
                this.WEST.setObstacle(true);
            }
            else {
                this.WEST.setDirty(false);
            }
            return Action.RIGHT;
        }
       return this.chooseDir();
    }

    public Action chooseDir() {
        if (this.NORTH.isDirty() && !this.NORTH.isObstacle()) {
            this.lastDir = 0;
            return Action.UP;
        }
        else if (this.EAST.isDirty() && !this.EAST.isObstacle()) {
            this.lastDir = 1;
            return Action.RIGHT;
        }
        else if (this.SOUTH.isDirty() && !this.SOUTH.isObstacle()) {
            this.lastDir = 2;
            return Action.DOWN;
        }
        else if (this.WEST.isDirty() && !this.WEST.isObstacle()) {
            this.lastDir = 3;
            return Action.LEFT;
        }
        else {
            this.lastDir = -1;
            return Action.LEFT;
        }
    }

    public void updateDir(Square dir) {
        if (this.blocked) {
           dir.setObstacle(true);
        }
        else {
            dir.setDirty(false);
        }
    }

//    public boolean[][] localGridSouth() {
//        boolean[][] results = { {true, true, true}, {true, true, true}, {false, false, false}};
//        return results;
//    }
//    public boolean[][] localGridNorth() {
//        boolean[][] results = { {false, false, false}, {true, true, true}, {true, true, true}};
//        return results;
//    }
//
//    public boolean[][] localGridWest() {
//        boolean[][] results = { {false, true, true}, {false, true, true}, {false, true, true} };
//        return results;
//    }
//
//    public boolean[][] localGridEast() {
//        boolean[][] results = { {true, true, false}, {true, true, false}, {true, true, false} };
//        return results;
//    }
//
    public void printDir(Square dir) {
        if (dir.isObstacle()) {
            System.out.print("X");
        }
        else if (dir.isDirty()) {
            System.out.print("?");
        }
        else {
            System.out.print("C");
        }
    }

    public void printLocalGrid() {
        System.out.print("  ");
        printDir(this.NORTH);
        System.out.println("  ");
        printDir(this.WEST);
        System.out.print(" O ");
        printDir(this.EAST);
        System.out.print("\n");
        System.out.print("  ");
        printDir(this.SOUTH);
        System.out.println("  ");
    }

    public static void main(String[] args) {
        StateAgent agent = new StateAgent();
        agent.printLocalGrid();
//        agent.printLocalGrid(agent.localGrid);
//        StateAgent agent = new StateAgent();
//        System.out.println("South");
//        agent.localGrid = agent.localGridSouth();
//        agent.printLocalGrid(agent.localGrid);
//
//        System.out.println("North");
//        agent.localGrid = agent.localGridNorth();
//        agent.printLocalGrid(agent.localGrid);
//
//        System.out.println("West");
//        agent.localGrid = agent.localGridWest();
//        agent.printLocalGrid(agent.localGrid);
//
//        System.out.println("East");
//        agent.localGrid = agent.localGridEast();
//        agent.printLocalGrid(agent.localGrid);
    }

//    public void printLastActions() {
//        for (int i=0; i < this.lastActions.length; i++) {
//            System.out.println(this.lastActions[i]);
//        }
//    }

//    public Action[] getLastActions() {
//       return lastActions;
//    }

}
