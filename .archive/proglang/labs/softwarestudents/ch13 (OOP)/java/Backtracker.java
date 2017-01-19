import java.util.Iterator;

public abstract interface Backtracker {
    public abstract Iterator moves(int level);
    public abstract boolean valid(int level, Object move);
    public abstract void record(int level, Object move);
    public abstract boolean done(int level);
    public abstract void undo(int level, Object move);
}