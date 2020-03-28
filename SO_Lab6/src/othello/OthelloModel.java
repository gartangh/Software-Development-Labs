
package othello;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import othello.exception.BoardIndexOutOfBoundsException;
import othello.exception.InvalidBoardSizeException;

public class OthelloModel {

	// The game board
	private int[][] board;
	// Who's turn it is (-1 / 1)
	private int turn;

	// UNUSED!
	private SimpleBooleanProperty valid = new SimpleBooleanProperty();

	// Constructor
	public OthelloModel(int size) throws InvalidBoardSizeException {
		if (size % 2 != 0 || size < 4)
			throw (new InvalidBoardSizeException());

		/*
		 * Starting field
		 * 
		 * White Black Black White
		 */
		this.board = new int[size][size];
		this.board[size / 2 - 1][size / 2 - 1] = 1;
		this.board[size / 2 - 1][size / 2] = -1;
		this.board[size / 2][size / 2 - 1] = -1;
		this.board[size / 2][size / 2] = 1;

		this.turn = -1; // Black begins
	}

	// Getters and setters
	public int getState(int x, int y) {
		return board[x][y];
	}

	public void setState(int x, int y, int state) throws BoardIndexOutOfBoundsException {
		if (!inBounds(x, y))
			throw (new BoardIndexOutOfBoundsException());

		board[x][y] = state;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getSize() {
		return board.length;
	}

	// Methods
	public boolean inBounds(int x, int y) {
		return x >= 0 && y >= 0 && x < board.length && y < board.length;
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++)
				s += "\t" + board[i][j];
			s += "\n";
		}

		return s;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (o.getClass() != this.getClass())
			return false;

		OthelloModel othelloModel = (OthelloModel) o;
		if (othelloModel.getSize() != board.length)
			return false;

		if (!othelloModel.toString().equals(toString()))
			return false;

		if (othelloModel.getTurn() != turn)
			return false;

		return true;
	}

	// UNUSED!
	public BooleanProperty validProperty() {
		return valid;
	}

	// UNUSED!
	public void setValid(boolean valid) {
		synchronized (this) {
			this.valid.setValue(valid);
		}
	}

	// UNUSED!
	public boolean isValid() {
		synchronized (this) {
			return valid.getValue();
		}
	}

}
