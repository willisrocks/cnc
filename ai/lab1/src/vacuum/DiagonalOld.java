package vacuum;

import java.util.Random;

/**
 * Created by chris on 9/30/16.
 */
public class DiagonalOld implements IVacuumStrategy{
    private Action[] sequence = new Action[24];
    private int pos = 0;
    private int max = sequence.length;

    private final Action[] NW = createDiagonal(Action.UP, Action.LEFT);
    private final Action[] NE = createDiagonal(Action.UP, Action.RIGHT);
    private final Action[] SE = createDiagonal(Action.DOWN, Action.RIGHT);
    private final Action[] SW = createDiagonal(Action.DOWN, Action.LEFT);

    public DiagonalOld() {
        this.sequence = randSequence();
    }

    public boolean hasNext() {
        if (pos < max) {
            return true;
        }
        return false;
    }

    public Action getNext() {
        Action action = sequence[pos % sequence.length];
        pos++;
        return action;
    }

//    public Action[] getSequence() {
//        return sequence;
//    }

    private Action[] randSequence() {
        Random gen = new Random();
        int randInt = gen.nextInt(4);
        switch (randInt) {
            case 0: return NW;
            case 1: return NE;
            case 2: return SE;
            case 3: return SW;
        }
        return NE;
    }

    private Action randDirection() {
        Action[] dirs = {Action.UP, Action.RIGHT, Action.DOWN, Action.LEFT};
        int rnd = new Random().nextInt(dirs.length);
        return dirs[rnd];
    }

    private Action[] createDiagonal(Action dir1, Action dir2) {
        Action[] results = new Action[24];
        for (int i=0; i<results.length; i++) {
            if (i % 2 == 0) {
                results[i] = dir1;
            } else if (i % 5 == 0) {
                results[i] = randDirection();
            } else {
                results[i] = dir2;
            }
        }
        return results;
    }
}
