package vacuum;

/**
 * Created by chris on 9/30/16.
 */
public class LeftStrategy implements IVacuumStrategy{

    public boolean hasNext() {
        return true;
    }

    public Action getNext() {
        return Action.LEFT;
    }
    public Action getNext(Action removeMe) {
        return Action.LEFT;
    }
}
