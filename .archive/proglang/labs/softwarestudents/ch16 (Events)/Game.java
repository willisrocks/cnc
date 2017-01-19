public class Game {

    private final int ICON_SIZE = 80;
    private final CellIcon off = new CellEmptyIcon(ICON_SIZE);
    private final CellIcon ex = new CellXIcon(ICON_SIZE);
    private final CellIcon oh = new CellOIcon(ICON_SIZE);
    private CellIcon[ ] initial = { off, off, off, 
				    off, off, off, 
				    off, off, off 
    };
    private final CellIcon[ ] cell = {ex, oh};

    private int player = -1;   // Current player: EX, or OH
    private int[ ] board = new int[9];

    public CellIcon[ ] clear( ) {
	for (int i = 0; i < board.length; i++)
	    board[i] = -1;
	player = -1;
	return initial;
    };

    public CellIcon move(int square) {
	player = (player + 1) % 2;
	board[square] = player;
	return cell[player];
    }

    private int[ ][ ] lines = {
	{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, //across
	{0, 3, 6}, {1, 4, 7}, {2, 5, 8}, //down
	{0, 4, 8}, {2, 4, 6}             //diagonal
    };

    public boolean won( ) {
	for (int i = 0; i < lines.length; i++)
	    if (board[lines[i][0]] == player 
		&& board[lines[i][1]] == player 
		&& board[lines[i][2]] == player)
		return true;
	return false;
    } // won

    public String player( ) {
	return player == 0? "X" : "O";
    }
} 
