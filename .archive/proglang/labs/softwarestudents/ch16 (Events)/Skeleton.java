import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Skeleton extends JPanel {

    // Global "state" of the interaction
    private int lastX = 0;        // first click's x-y coordinates
    private int lastY = 0;
    private int clickNumber = 0;  
    private JComboBox combo;
    private String[ ] choices = {"Nothing", "Rectangle", "Message"};
    private JTextArea echoArea;  
    private JTextField typing;
    
    // Initializing the panel
    public Skeleton( ) {
        
        // Set the background color and listen for the mouse
        setBackground(Color.white);
        addMouseListener(new MouseHandler());
		
        // Create a button and add it to the Panel.
        JButton clearButton = new JButton("Clear");
        clearButton.setForeground(Color.black);
        clearButton.setBackground(Color.lightGray);
        add(clearButton);
        clearButton.addActionListener(new ClearButtonHandler());

        // Create a menu of user combos and add it
        combo = new JComboBox(choices);
        add(combo);
        combo.addActionListener(new ComboHandler());

        // Create a TextField and a TextArea and add them
        typing = new JTextField(20);
        add(typing);
        typing.addActionListener(new TextHandler());
        echoArea = new JTextArea(2, 40);
        echoArea.setEditable(false);
        add(echoArea);
    }

    // Handling events that occur in the panel

    // Called when the user clicks the mouse button
    private class MouseHandler extends MouseAdapter {
	public void mouseClicked(MouseEvent e) { 
	    int x = e.getX();
	    int y = e.getY();
	    echoArea.setText("Mouse Clicked at " + 
			     e.getX() + ", " + e.getY() + "\n");
	    Graphics g = getGraphics();
	    if (combo.getSelectedItem().equals("Rectangle")) {
		clickNumber = clickNumber + 1;
		// is it the first click?
		if (clickNumber % 2 == 1) {  
		    echoArea.append("Click to set lower right corner of the rectangle");
		    lastX = x;
		    lastY = y;
		}
		// or the second?
		else g.drawRect(lastX, lastY, Math.abs(x-lastX), Math.abs(y-lastY));
	    }
	    else if (combo.getSelectedItem().equals("Message")) 
		// for a message, display it
		g.drawString(typing.getText(), x, y);          
	} // mouseClicked
    }

    // Called when the user selects clear Button
    private class ClearButtonHandler implements ActionListener {
	public void actionPerformed (ActionEvent e) {
            echoArea.setText("Clear button selected ");
            repaint();
        }
    }
    
    // Called when the user selects TextArea
    private class TextHandler implements ActionListener {
	public void actionPerformed (ActionEvent e) {
            echoArea.setText("Text entered: " + typing.getText());
            if (combo.getSelectedItem().equals("Message"))
               echoArea.append("\nNow click to place this message");
        }
    }
    
    // Called when the user selects a Combo
    private class ComboHandler implements ActionListener {
	public void actionPerformed (ActionEvent e) {
	    String c =  (String) (combo.getSelectedItem());
	    echoArea.setText("Combo selected: " + c);
	    // prepare to handle first mouse click for this combo
	    clickNumber = 0;
	    if (c.equals("Rectangle"))
		echoArea.append("\nClick to set upper left corner of the rectangle");                  
	    else if (c.equals("Message")) 
		echoArea.append("\nEnter a message in the text area");  
	}
    }    

    public static void main(String args[])  {
	JFrame frame = new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
	Skeleton panel = new Skeleton( );

	frame.getContentPane().add(panel);
	frame.setLocation(100, 100);  // needed by some window managers
	frame.setSize(500, 500);
	//frame.pack( );
	frame.show();
    }
}
