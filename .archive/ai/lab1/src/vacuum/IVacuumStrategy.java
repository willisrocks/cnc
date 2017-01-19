package vacuum;

/**
 * Created by chris on 10/1/16.
 */
public interface IVacuumStrategy {
    public boolean hasNext();
    public Action getNext();
}
