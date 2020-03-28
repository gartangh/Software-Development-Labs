package test;

import static org.junit.Assert.*;

import org.junit.Test;

import othello.OthelloController;
import othello.OthelloModel;
import othello.exception.BoardIndexOutOfBoundsException;
import othello.exception.InvalidBoardSizeException;
import othello.exception.InvalidMoveException;

public class OthelloControllerTest {

	@Test
	public void testIsValidMove() {
		try {
			OthelloController othelloController = new OthelloController(new OthelloModel(4));

			assertTrue(othelloController.isValidMove(2, 3));
			assertTrue(othelloController.isValidMove(1, 0));
			assertTrue(othelloController.isValidMove(0, 1));
			assertTrue(othelloController.isValidMove(3, 2));
		} catch (InvalidBoardSizeException | BoardIndexOutOfBoundsException e) {
			fail("Test ValidMove failed!");
		}
	}

	@Test
	public void testIsInvalidMove() {
		try {
			OthelloController othelloController = new OthelloController(new OthelloModel(4));

			assertFalse(othelloController.isValidMove(2, 0));
		} catch (InvalidBoardSizeException | BoardIndexOutOfBoundsException e) {
			fail("Test InvalidMove failed!");
		}
	}

	@Test
	public void testDoMove() {
		try {
			OthelloModel othelloModel1 = new OthelloModel(4);
			OthelloController othelloController1 = new OthelloController(othelloModel1);
			othelloController1.doMove(2, 3);
			if (othelloModel1.getState(2, 2) != -1)
				fail("Test Failed: DoMove(2,3) did not change state (2,2).");

			OthelloModel othelloModel2 = new OthelloModel(4);
			OthelloController othelloController2 = new OthelloController(othelloModel2);
			othelloController2.doMove(1, 0);
			if (othelloModel2.getState(1, 1) != -1)
				fail("Test Failed: DoMove(1,0) did not change state (1,1).");

			OthelloModel othelloModel3 = new OthelloModel(4);
			OthelloController othelloController3 = new OthelloController(othelloModel3);
			othelloController3.doMove(0, 1);
			if (othelloModel3.getState(1, 1) != -1)
				fail("Test Failed: DoMove(0,1) did not change state (1,1).");

			OthelloModel othelloModel4 = new OthelloModel(4);
			OthelloController othelloController4 = new OthelloController(othelloModel4);
			othelloController4.doMove(3, 2);
			if (othelloModel4.getState(2, 2) != -1)
				fail("Test Failed: DoMove(3,2) did not change state (2,2).");
		} catch (InvalidBoardSizeException | InvalidMoveException | BoardIndexOutOfBoundsException e) {
			fail("Test doMove failed!");
		}
	}

	@Test
	public void testIsFinished() {
		System.out.println("----TESTING ISFINISHED()----");

		try {
			OthelloController othelloController1 = new OthelloController(new OthelloModel(4));

			if (othelloController1.isFinished() != 0)
				fail("Test Failed: isFinished did not return 0 when game is still in progress.");

			/*
			othelloController1.doMove(0, 1); // Black
			othelloController1.doMove(2, 0); // White
			
			othelloController1.doMove(3, 0);
			othelloController1.doMove(0, 0);
			 
			othelloController1.doMove(3, 2);
			othelloController1.doMove(3, 1);
			 
			othelloController1.doMove(1, 0);
			othelloController1.doMove(2, 3); // Not a valid move!!!
			 
			othelloController1.doMove(3, 3);
			othelloController1.doMove(0, 3);
			 
			othelloController1.doMove(1, 3);
			othelloController1.doMove(0, 2);
			 
			if (othelloController1.isFinished() != -1)
				fail("Test Failed: (case board is full) isFinished did not return -1 when the game has finished and black has won.");
			*/

			/*
			OthelloController othelloController4 = new OthelloController(new OthelloModel(4));
			
			othelloController4.doMove(0, 1); // Black
			othelloController4.doMove(2, 0); // White

			othelloController4.doMove(3, 0);
			othelloController4.doMove(0, 0);

			othelloController4.doMove(3, 2);
			othelloController4.doMove(3, 1);

			othelloController4.doMove(1, 0);
			othelloController4.doMove(2, 3); // Not a valid move!!!

			othelloController4.doMove(3, 3);
			othelloController4.doMove(0, 3);

			othelloController4.doMove(0, 2);

			if (othelloController4.isFinished() != -1)
				fail("Test Failed: (case no valid moves left) isFinished did not return -1 when the game has finished and black has won.");
			*/

			OthelloModel othelloModel6 = new OthelloModel(4);
			OthelloController othelloController6 = new OthelloController(othelloModel6);
			
			othelloModel6.setState(0, 0, 1);
			othelloModel6.setState(1, 0, 1);
			othelloModel6.setState(2, 0, 1);
			othelloModel6.setState(3, 0, 1);
			othelloModel6.setState(0, 1, 1);
			othelloModel6.setState(1, 1, 1);
			othelloModel6.setState(2, 1, 1);
			othelloModel6.setState(3, 1, 1);
			othelloModel6.setState(0, 2, 1);
			othelloModel6.setState(1, 2, 1);
			othelloModel6.setState(2, 2, 1);
			othelloModel6.setState(3, 2, 1);
			othelloModel6.setState(0, 3, 1);
			othelloModel6.setState(1, 3, 1);
			othelloModel6.setState(2, 3, 1);
			othelloModel6.setState(3, 3, 1);

			if (othelloController6.isFinished() != 1)
				fail("Test Failed: (case board is full) isFinished did not return 1 when game has finished and white has won.");

			/*
			OthelloController othelloController2 = new OthelloController(new OthelloModel(4));
			

			othelloController2.doMove(0, 1); // Black
			othelloController2.doMove(0, 0); // White
			
			othelloController2.doMove(1, 0);
			othelloController2.doMove(2, 0);
			
			othelloController2.doMove(3, 2);
			othelloController2.doMove(1, 3);
			
			othelloController2.doMove(0, 2);
			othelloController2.doMove(0, 3);
			
			othelloController2.doMove(2, 3); // Not a valid move!!!

			if (othelloController2.isFinished() != 1)
				fail("Test Failed: (case no valid moves left) isFinished did not return 1 when game has finished and white has won.");
			*/

			OthelloModel othelloModel5 = new OthelloModel(4);
			OthelloController othelloController5 = new OthelloController(othelloModel5);

			othelloModel5.setState(0, 0, -1);
			othelloModel5.setState(1, 0, -1);
			othelloModel5.setState(2, 0, -1);
			othelloModel5.setState(3, 0, -1);
			othelloModel5.setState(0, 1, -1);
			othelloModel5.setState(1, 1, -1);
			othelloModel5.setState(2, 1, -1);
			othelloModel5.setState(3, 1, -1);
			othelloModel5.setState(0, 2, 1);
			othelloModel5.setState(1, 2, 1);
			othelloModel5.setState(2, 2, 1);
			othelloModel5.setState(3, 2, 1);
			othelloModel5.setState(0, 3, 1);
			othelloModel5.setState(1, 3, 1);
			othelloModel5.setState(2, 3, 1);
			othelloModel5.setState(3, 3, 1);

			if (othelloController5.isFinished() != 2)
				fail("Test Failed: (case board is full) isFinished did not return 2 when game has finished and it's a draw.");

			OthelloModel othelloModel3 = new OthelloModel(4);
			OthelloController othelloController3 = new OthelloController(othelloModel3);

			othelloModel3.setState(0, 0, 0);
			othelloModel3.setState(1, 0, 1);
			othelloModel3.setState(2, 0, 1);
			othelloModel3.setState(3, 0, 0);
			othelloModel3.setState(0, 1, -1);
			othelloModel3.setState(1, 1, 1);
			othelloModel3.setState(2, 1, -1);
			othelloModel3.setState(3, 1, -1);
			othelloModel3.setState(0, 2, -1);
			othelloModel3.setState(1, 2, -1);
			othelloModel3.setState(2, 2, 1);
			othelloModel3.setState(3, 2, -1);
			othelloModel3.setState(0, 3, 0);
			othelloModel3.setState(1, 3, 1);
			othelloModel3.setState(2, 3, 1);
			othelloModel3.setState(3, 3, 0);

			if (othelloController3.isFinished() != 2)
				fail("Test Failed: (case no valid moves left) isFinished did not return 2 when game has finished and it's a draw.");
		} catch (InvalidBoardSizeException /*| InvalidMoveException*/ | BoardIndexOutOfBoundsException e) {
			fail("Test isFinished failed!");
		}
	}

}
