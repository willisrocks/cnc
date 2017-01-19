import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *  An application that plays Tic-Tac-Toe
 *  
 * @author   Bob Noonan, College of William and Mary
 * @author   Allen Tucker, Bowdoin College
 */
public class TicTacToe {

/**
 * called automatically when run as a stand-alone application;
 * sets frame size and layout
 *
 * @param  args  ignored; command line arguments
 */
public static void main(String args[])  {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    Game game = new Game( );
    Controller con = new Controller(game);

    frame.getContentPane().add(con);
    frame.setLocation(100, 100);  // needed by some window managers
    // frame.setSize(500, 500);
    frame.pack( );
    frame.show();
} // main

} // TicTacToe

