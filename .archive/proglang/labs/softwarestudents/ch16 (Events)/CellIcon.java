import java.awt.*;
import javax.swing.*;

public abstract class CellIcon implements Icon {

    private Color color = Color.black;
    private int size;

    public CellIcon(int size) { this.size = size; }

    public int size( ) { return size; }
    public Color getColor( ) { return color; }
    public void setColor(Color c) { color = c; }

    public int getIconWidth( ) { return size; }
    public int getIconHeight( ) { return size; }
}
