package othello;

import static othello.State.*;
import static othello.StdDraw.*;
import static java.lang.Math.*;

/** Accepts move choices from a human via Gui. */
public class HumanPlayer implements Player {

	@Override
	public int move(State state) {
		while (!mousePressed()) {
			// Wait for a mouse click
		}
		double x = mouseX();
		double y = mouseY();
		int r = (int)round((0.95 - y) / 0.1);
		int c = (int)round((x - 0.15) / 0.1);
		int result = PASS;
		if (r >= 0 && r < WIDTH && c >= 0 && c < WIDTH) {
			result = r * WIDTH + c;
		}
		while (mousePressed()) {
			// Wait for mouse release
		}
		return result;
	}

	@Override
	public String toString() {
		return "Human";
	}

}
