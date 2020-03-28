
package othello;

import othello.exception.BoardIndexOutOfBoundsException;
import othello.exception.InvalidBoardSizeException;
import othello.exception.InvalidMoveException;

public class OthelloController {

	private OthelloModel model;

	public OthelloController(OthelloModel model) {
		this.model = model;
	}

	public boolean isValidMove(int x, int y) throws BoardIndexOutOfBoundsException {
		if (!model.inBounds(x, y))
			return false;

		if (model.getState(x, y) != 0)
			return false;

		int self;
		int opponent;
		if (model.getTurn() == -1) {
			self = -1;
			opponent = 1;
		} else {
			self = 1;
			opponent = -1;
		}

		// Up
		if (x - 1 >= 0 && model.getState(x - 1, y) == opponent) {
			boolean gotSelf = false;
			for (int i = x - 2; i >= 0 && model.getState(i, y) != 0; i--)
				if (model.getState(i, y) == self) {
					gotSelf = true;
					break;
				}

			if (gotSelf)
				return true;
		}

		// Right up
		if (x - 1 >= 0 && y + 1 < model.getSize() && model.getState(x - 1, y + 1) == opponent) {
			boolean gotSelf = false;
			int j = y + 2;
			for (int i = x - 2; i >= 0 && j < model.getSize() && model.getState(i, j) != 0; i--) {
				if (model.getState(i, j) == self) {
					gotSelf = true;
					break;
				}

				j++;
			}

			if (gotSelf)
				return true;
		}

		// Right
		if (y + 1 < model.getSize() && model.getState(x, y + 1) == opponent) {
			boolean gotSelf = false;
			for (int j = y + 2; j < model.getSize() && model.getState(x, j) != 0; j++)
				if (model.getState(x, j) == self) {
					gotSelf = true;
					break;
				}

			if (gotSelf)
				return true;
		}

		// Right down
		if (x + 1 < model.getSize() && y + 1 < model.getSize() && model.getState(x + 1, y + 1) == opponent) {
			boolean gotSelf = false;
			int j = y + 2;
			for (int i = x + 2; i < model.getSize() && j < model.getSize() && model.getState(i, j) != 0; i++) {
				if (model.getState(i, j) == self) {
					gotSelf = true;
					break;
				}

				j++;
			}

			if (gotSelf)
				return true;
		}

		// Down
		if (x + 1 < model.getSize() && model.getState(x + 1, y) == opponent) {
			boolean gotSelf = false;
			for (int i = x + 2; i < model.getSize() && model.getState(i, y) != 0; i++)
				if (model.getState(i, y) == self) {
					gotSelf = true;
					break;
				}

			if (gotSelf)
				return true;
		}

		// Left down
		if (x + 1 < model.getSize() && y - 1 >= 0 && model.getState(x + 1, y - 1) == opponent) {
			boolean gotSelf = false;
			int j = y - 2;
			for (int i = x + 2; i < model.getSize() && j >= 0 && model.getState(i, j) != 0; i++) {
				if (model.getState(i, j) == self) {
					gotSelf = true;
					break;
				}

				j--;
			}

			if (gotSelf)
				return true;
		}

		// Left
		if (y - 1 >= 0 && model.getState(x, y - 1) == opponent) {
			boolean gotSelf = false;
			for (int j = y - 2; j >= 0 && model.getState(x, j) != 0; j--)
				if (model.getState(x, j) == self) {
					gotSelf = true;
					break;
				}

			if (gotSelf)
				return true;
		}

		// Left up
		if (x - 1 >= 0 && y - 1 >= 0 && model.getState(x - 1, y - 1) == opponent) {
			boolean gotSelf = false;
			int j = y - 2;
			for (int i = x - 2; i >= 0 && j >= 0 && model.getState(i, j) != 0; i--) {
				if (model.getState(i, j) == self) {
					gotSelf = true;
					break;
				}

				j--;
			}

			if (gotSelf)
				return true;
		}

		return false;
	}

	public void doMove(int x, int y) throws InvalidMoveException, BoardIndexOutOfBoundsException {
		if (!model.inBounds(x, y))
			throw (new BoardIndexOutOfBoundsException());

		if (!isValidMove(x, y))
			throw (new InvalidMoveException());

		// Lay new piece
		model.setState(x, y, model.getTurn());

		// Switch other pieces
		int self;
		int opponent;
		if (model.getTurn() == -1) {
			self = -1;
			opponent = 1;
		} else {
			self = 1;
			opponent = -1;
		}

		// Up
		if (x - 1 >= 0 && model.getState(x - 1, y) == opponent) {
			for (int i = x - 2; i >= 0 && model.getState(i, y) != 0; i--)
				if (model.getState(i, y) == self) {
					for (int u = i; u < x; u++)
						model.setState(u, y, self);

					break;
				}
		}

		// Right up
		if (x - 1 >= 0 && y + 1 < model.getSize() && model.getState(x - 1, y + 1) == opponent) {
			int j = y + 2;
			for (int i = x - 2; i >= 0 && j < model.getSize() && model.getState(i, j) != 0; i--) {
				if (model.getState(i, j) == self) {
					int v = j;
					for (int u = i; u < x && v > y; u++) {
						model.setState(u, v, self);

						v--;
					}

					break;
				}

				j++;
			}
		}

		// Right
		if (y + 1 < model.getSize() && model.getState(x, y + 1) == opponent) {
			for (int j = y + 2; j < model.getSize() && model.getState(x, j) != 0; j++)
				if (model.getState(x, j) == self) {
					for (int v = j; v > y; v--)
						model.setState(x, v, self);

					break;
				}
		}

		// Right down
		if (x + 1 < model.getSize() && y + 1 < model.getSize() && model.getState(x + 1, y + 1) == opponent) {

			int j = y + 2;
			for (int i = x + 2; i < model.getSize() && j < model.getSize() && model.getState(i, j) != 0; i++) {
				if (model.getState(i, j) == self) {
					int v = j;
					for (int u = i; u > x && v > y; u--) {
						model.setState(u, v, self);

						v--;
					}

					break;
				}

				j++;
			}
		}

		// Down
		if (x + 1 < model.getSize() && model.getState(x + 1, y) == opponent) {

			for (int i = x + 2; i < model.getSize() && model.getState(i, y) != 0; i++)
				if (model.getState(i, y) == self) {
					for (int u = i; u > x; u--)
						model.setState(u, y, self);

					break;
				}
		}

		// Left down
		if (x + 1 < model.getSize() && y - 1 >= 0 && model.getState(x + 1, y - 1) == opponent) {
			int j = y - 2;
			for (int i = x + 2; i < model.getSize() && j >= 0 && model.getState(i, j) != 0; i++) {
				if (model.getState(i, j) == self) {
					int v = j;
					for (int u = i; u > x && v < y; u--) {
						model.setState(u, v, self);

						v++;
					}

					break;
				}

				j--;
			}
		}

		// Left
		if (y - 1 >= 0 && model.getState(x, y - 1) == opponent) {
			for (int j = y - 2; j >= 0 && model.getState(x, j) != 0; j--)
				if (model.getState(x, j) == self) {
					for (int v = j; v < y; v++)
						model.setState(x, v, self);

					break;
				}
		}

		// Left up
		if (x - 1 >= 0 && y - 1 >= 0 && model.getState(x - 1, y - 1) == opponent) {
			int j = y - 2;
			for (int i = x - 2; i >= 0 && j >= 0 && model.getState(i, j) != 0; i--) {
				if (model.getState(i, j) == self) {
					int v = j;
					for (int u = i; u < x && v < y; u++) {
						model.setState(u, v, self);

						v++;
					}

					break;
				}

				j--;
			}
		}

		// Switch turns
		if (model.getTurn() == -1)
			model.setTurn(1);
		else
			model.setTurn(-1);

		System.out.println(model.toString());
	}

	public int isFinished() {
		// Test if all fields are occupied
		// Count all black and all white pieces
		int black = 0;
		int white = 0;
		boolean free = false;
		for (int i = 0; i < model.getSize(); i++)
			for (int j = 0; j < model.getSize(); j++)
				if (model.getState(i, j) == -1)
					black++;
				else if (model.getState(i, j) == 1)
					white++;
				else
					free = true;

		// All fields are occupied
		if (!free)
			if (black > white)
				return -1;
			else if (black < white)
				return 1;
			else
				return 2;

		// Test if no more valid moves are possible
		try {
			// Copy the board
			OthelloModel othelloModel = new OthelloModel(model.getSize());
			for (int i = 0; i < model.getSize(); i++)
				for (int j = 0; j < model.getSize(); j++)
					othelloModel.setState(i, j, model.getState(i, j));

			boolean impossible = true;

			// Test if black can make a valid move
			othelloModel.setTurn(-1);
			for (int i = 0; i < othelloModel.getSize(); i++)
				for (int j = 0; j < othelloModel.getSize(); j++)
					if (othelloModel.getState(i, j) == 0)
						try {
							if (isValidMove(i, j))
								impossible = false;
						} catch (BoardIndexOutOfBoundsException e) {
							e.printStackTrace();
						}

			// Test if white can make a valid move
			othelloModel.setTurn(1);
			for (int i = 0; i < othelloModel.getSize(); i++)
				for (int j = 0; j < othelloModel.getSize(); j++)
					if (othelloModel.getState(i, j) == 0)
						try {
							if (isValidMove(i, j))
								impossible = false;
						} catch (BoardIndexOutOfBoundsException e) {
							e.printStackTrace();
						}

			// There are no more valid moves
			if (impossible)
				if (black > white)
					return -1;
				else if (black < white)
					return 1;
				else
					return 2;
		} catch (InvalidBoardSizeException | BoardIndexOutOfBoundsException e) {
			e.printStackTrace();
		}

		// There are free fields and there are possible moves left
		return 0;
	}

}
