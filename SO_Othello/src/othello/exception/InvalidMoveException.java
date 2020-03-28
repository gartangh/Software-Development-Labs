package othello.exception;

@SuppressWarnings("serial")
public class InvalidMoveException extends Exception {

	public InvalidMoveException() {
		super("Invalid move!");
	}

}
