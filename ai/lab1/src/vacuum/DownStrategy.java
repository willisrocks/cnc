package vacuum;

/**
 * Created by chris on 9/30/16.
 */
public class DownStrategy implements IVacuumStrategy{

    public boolean hasNext() {
        return true;
    }

    public Action getNext() {
        return Action.DOWN;
    }
}
