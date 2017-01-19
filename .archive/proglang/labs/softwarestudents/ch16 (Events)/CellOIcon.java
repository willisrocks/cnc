import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class CellOIcon extends CellIcon {
    public CellOIcon(int size) { super(size); }

    public void paintIcon(Component c, Graphics g, 
			  int x, int y) {
	int size = size( );
	g.setColor(Color.white);
	g.fillRect(x, y, size, size);
	g.setColor(getColor( ));
	g.drawOval(x+1, y+1, size-2, size-2);
	g.setColor(Color.black);
    }
}
