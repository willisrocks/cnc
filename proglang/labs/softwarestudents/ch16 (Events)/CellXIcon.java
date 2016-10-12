import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class CellXIcon extends CellIcon {
    public CellXIcon(int size) { super(size); }

    public void paintIcon(Component c, Graphics g, 
			  int x, int y) {
	int size = size( );
	g.setColor(Color.white);
	g.fillRect(x, y, size, size);
	g.setColor(getColor( ));
	g.drawLine(x, y, x+size, y+size);
	g.drawLine(x+size, y, x, y+size);
	g.setColor(Color.black);
    }
}
