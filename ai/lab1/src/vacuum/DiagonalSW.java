package vacuum;

/**
 * Created by chris on 9/30/16.
 */
public class DiagonalSW implements IVacuumStrategy{
    private Action[] sequence = {Action.DOWN, Action.LEFT};
    static int pos = 0;

    public boolean hasNext() {
        return true;
    }

    public Action getNext() {
        Action action = sequence[pos % 2];
        pos++;
        return action;
    }
}
