package vacuum;

/**
 * Created by chris on 9/30/16.
 */
public class RightStrategy implements IVacuumStrategy{

    public boolean hasNext() {
        return true;
    }

    public Action getNext() {
        return Action.RIGHT;
    }
}
