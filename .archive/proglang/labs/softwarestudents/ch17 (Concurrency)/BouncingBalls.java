import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class BouncingBalls extends JPanel {
    public final static int width = 500;
    public final static int height = 400;
    private Ball ball = new Ball(128, 127);
    private Vector<Ball> list = new Vector();
    
    public BouncingBalls ( ) {
        setPreferredSize(new Dimension(width, height));
	list.add(ball);
	addMouseListener(new MouseHandler());
        BallThread bt = new BallThread();
        bt.start( );
    }

    private class MouseHandler extends MouseAdapter  {
	public void mousePressed(MouseEvent e) {
	    Ball b = new Ball(e.getX(), e.getY());
	    list.add(b);
	}  // mousePressed
    } // MouseHandler

    private class BallThread extends Thread {
        public boolean cont;
	public void run( ) {
            cont = true;
	    while (cont) {
		for (Ball b : list) {
		    b.move();
		}
		repaint( );
		try { Thread.sleep(50);
		} catch (InterruptedException exc) { }
	    }
	} // run
    } // BallThread

    public synchronized void paintChildren(Graphics g) {
	for (Ball b : list) {
	    b.paint(g);
	}
    }

     public static void main(String[] args) {
         JFrame frame = new JFrame("Bouncing Balls");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         frame.getContentPane().add(new BouncingBalls( ));
         frame.setLocation(50, 50);
         frame.pack();
         frame.setVisible(true);
     }
} // BouncingBalls
