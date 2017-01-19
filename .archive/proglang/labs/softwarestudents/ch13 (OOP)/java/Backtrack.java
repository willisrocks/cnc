public class Backtrack {

    Backtracker b;

public Backtrack (Backtracker b) { this.b = b; }

public boolean attempt (int level) {
    boolean successful = false;
    Iterator i = b.moves(level);
    while (!successful && i.hasNext()) {
        Object move = i.next();
        if (b.valid(level, move)) {
            b.record(level, move);
            if (b.done(level)) 
                successful= true;
            else {
                successful = attempt(level+1);
                if (!successful)
                    b.undo(level, move);
            } // if done
        } // if valid
    } // while
    return successful;
} // attempt
}
