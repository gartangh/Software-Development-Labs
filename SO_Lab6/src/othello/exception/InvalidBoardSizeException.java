package othello.exception;

@SuppressWarnings("serial")
public class InvalidBoardSizeException extends Exception {

	public InvalidBoardSizeException() {
		super("Invalid board size!");
	}
	
}
