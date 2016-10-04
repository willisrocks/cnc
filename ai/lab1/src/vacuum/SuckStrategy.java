package vacuum;

import java.util.Random;

/**
 * Created by chris on 9/30/16.
 */
public class SuckStrategy implements IVacuumStrategy{

    public boolean hasNext() {
        return true;
    }

    public Action getNext() {
        return Action.SUCK;
    }
}
