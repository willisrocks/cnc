import java.io.*;
import java.util.Iterator;

public class Queen implements Backtracker {

int MAXROW = 8;       // board size
boolean swDiag[];
boolean seDiag[];
boolean row[];      
int rowPos[];       

public Queen(int n) {
    MAXROW = n;
    swDiag = new boolean[2*MAXROW-1];
    seDiag = new boolean[2*MAXROW+1];
    row = new boolean[MAXROW];
    rowPos = new int[MAXROW];
}

private void setPosition(int rowNo, int colNo, 
        boolean occupied) {
    row[rowNo] = occupied;
    swDiag[rowNo+colNo] = occupied;
    seDiag[rowNo-colNo+MAXROW-1] = occupied;
}

public boolean valid(int colNo, Object move) {
    int rowNo = ((Integer)move).intValue();
    System.out.println("Try: " + rowNo + ", " + colNo);
    return (!row[rowNo]) 
        && (!swDiag[rowNo+colNo])
        && (!seDiag[rowNo-colNo+MAXROW-1]);
}

public boolean done(int colNo) {
    return colNo + 1 >= MAXROW;
}

public void record(int colNo, Object move) {
    int rowNo = ((Integer)move).intValue();
    System.out.println("Record: " + rowNo + ", " + colNo);
    rowPos[colNo] = rowNo;
    setPosition(rowNo, colNo, true);
}

public void undo(int colNo, Object move) {
    int rowNo = ((Integer)move).intValue();
    System.out.println("Undo: " + rowNo + ", " + colNo);
    rowPos[colNo] = 0;
    setPosition(rowNo, colNo, false);
}

public void display( ) {
    for (int i = 0; i < MAXROW; i++) {
        for (int j = 0; j < MAXROW; j++)
            if (rowPos[j] == i)
               System.out.print("Q");
            else
                System.out.print(" ");
        System.out.println();
    }
} // display

public Iterator moves(int level) {
    return new QueenIterator( );
}

private class QueenIterator 
        implements Iterator {
    int  cursor = 0;

    public boolean hasNext( ) {
        return cursor < MAXROW;
    }

    public Object next( ) {
        return new Integer(cursor++);
    }
    public void remove() { }
} // QueenIterator

} //class Queen
