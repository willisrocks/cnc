import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Controller extends JPanel {
                                                 
    private TextArea echoArea;  
    private Game game;
    private JButton[ ] square;

    public Controller(Game game) {
	this.game = game;
	setLayout(new BorderLayout( ));

        // Add a new game button
        Button newGame = new Button("New Game");
        newGame.addActionListener(new NewGameHandler());
        add(newGame, BorderLayout.NORTH);

	// Place a 3x3 board to the panel
	Icon[ ] label = game.clear( );
	JPanel board = keypad(3, 3, label);
        add(board, BorderLayout.CENTER);
	
        // Add an echo area
        echoArea = new TextArea(2, 25);
        echoArea.setEditable(false);
	echoArea.setText("X goes first");
        add(echoArea, BorderLayout.SOUTH);
    } // Controller

    public JPanel keypad (int rows, int cols, Icon[ ] label) {
	JPanel panel = new JPanel( );
	panel.setLayout( new GridLayout(rows, cols, 2, 2));
	square = new JButton[label.length];
	for (int i = 0; i < label.length; i++) {
	    square[i] = new JButton(label[i]);
	    square[i].addActionListener( new ActionHandler(i) );
	    panel.add( square[i] );
	}
	return panel;
    }

    private void setButtons(boolean enabled) {
	for (int i = 0; i < square.length; i++)
	    square[i].setEnabled(enabled);
    }

    public void newGame( ) {
	Icon[ ] label = game.clear( );
	for (int i = 0; i < square.length; i++)
	    square[i].setIcon(label[i]);
	setButtons(true);
	echoArea.setText("X goes first");
    }
        
    private class ActionHandler implements ActionListener {
	private int button;

	public ActionHandler(int button) { 
	    this.button = button;
	}

	public void actionPerformed(ActionEvent e) {
	    Icon icon = game.move(button);
	    square[button].setIcon(icon);
	    square[button].setEnabled(false);
	    if (game.won( )) {
		setButtons(false);
		echoArea.setText(game.player( ) + " wins!");
	    } else
		echoArea.setText("Button clicked: " + button);
	}
    }
    
    // Called when the user selects clear Button
    private class NewGameHandler implements ActionListener {
	public void actionPerformed (ActionEvent e) {
	    echoArea.setText("New Game button selected ");
	    newGame( );
	    repaint( );
	}
    }

}
