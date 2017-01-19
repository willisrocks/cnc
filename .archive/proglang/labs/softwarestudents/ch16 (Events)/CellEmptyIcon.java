import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class CellEmptyIcon extends CellIcon {
    public CellEmptyIcon(int size) { super(size); }

    public void paintIcon(Component c, Graphics g, 
			  int x, int y) {
	int size = size( );
	g.setColor(Color.white);
	g.fillRect(x, y, size, size);
	g.setColor(getColor( ));
	g.setColor(Color.black);
    }
}
