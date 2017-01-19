import java.awt.*;

public class Ball {

    Color color = Color.red;
    int x;
    int y;
    int diameter = 10;
    int dx = 3;
    int dy = 6;

    public Ball (int ix, int iy) {
	super( );
	x = ix;
	y = iy;
	color = new Color(x % 256, y % 256, (x+y) % 256);
	dx = x % 10 + 1;
	dy = y % 10 + 1;
    }

    public void move  () {
	if (x < 0 || x >= BouncingBalls.width)
	    dx = - dx;
	if (y < 0 || y >= BouncingBalls.height)
	    dy = - dy;
	x += dx;
	y += dy;
    }

    public void paint (Graphics g) {
	g.setColor(color);
        g.fillOval(x, y, diameter, diameter);
    }

} // Ball
