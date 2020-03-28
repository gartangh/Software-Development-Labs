package test;

import static org.junit.Assert.*;

import org.junit.Test;

import othello.OthelloController;
import othello.OthelloModel;
import othello.exception.BoardIndexOutOfBoundsException;
import othello.exception.InvalidBoardSizeException;

public class OthelloModelTest {

	@Test
	public void testOthelloModel() {
		int size = 4;
		try {
			OthelloModel othelloModel = new OthelloModel(size);
			assertNotNull(othelloModel);
			assertSame(othelloModel.getSize(), size);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSetValidState() throws BoardIndexOutOfBoundsException {
		try {
			OthelloModel othelloModel = new OthelloModel(4);
			
			othelloModel.setState(3, 3, 0);
			
			assertSame(othelloModel.getState(3, 3), 0);
		} catch (InvalidBoardSizeException e) {
			fail("Failed valid state");
		}
	}
	
	@Test(expected = BoardIndexOutOfBoundsException.class)
	public void testSetInvalidState() throws BoardIndexOutOfBoundsException {
		try {
			OthelloModel othelloModel = new OthelloModel(4);
			
			othelloModel.setState(4, 4, 0);
		} catch (InvalidBoardSizeException e) {
			fail("Failed invalid state");
		}
	}

	@Test(expected = InvalidBoardSizeException.class)
	public void testOthelloModelOddSize() throws InvalidBoardSizeException {
		new OthelloModel(5);
	}

	@Test(expected = InvalidBoardSizeException.class)
	public void testOthelloModelNegativeSize() throws InvalidBoardSizeException {
		new OthelloModel(-4);
	}

	@Test
	public void testInBounds() {
		try {
			OthelloModel othelloModel = new OthelloModel(4);

			assertTrue(othelloModel.inBounds(0, 0));
			assertTrue(othelloModel.inBounds(0, 3));
			assertTrue(othelloModel.inBounds(3, 0));
			assertTrue(othelloModel.inBounds(3, 3));
		} catch (InvalidBoardSizeException e) {
			fail("Failed");
		}
	}

	@Test
	public void testOutOfBounds() {
		try {
			OthelloModel othelloModel = new OthelloModel(4);

			assertFalse(othelloModel.inBounds(-1, 0));
			assertFalse(othelloModel.inBounds(0, -1));
			assertFalse(othelloModel.inBounds(-1, -1));

			assertFalse(othelloModel.inBounds(-1, 3));
			assertFalse(othelloModel.inBounds(0, 4));
			assertFalse(othelloModel.inBounds(-1, 4));

			assertFalse(othelloModel.inBounds(4, 0));
			assertFalse(othelloModel.inBounds(3, -1));
			assertFalse(othelloModel.inBounds(4, -1));

			assertFalse(othelloModel.inBounds(3, 4));
			assertFalse(othelloModel.inBounds(4, 3));
			assertFalse(othelloModel.inBounds(4, 4));
		} catch (InvalidBoardSizeException e) {
			fail("Failed");
		}
	}

	@Test
	public void testGetTurn() {
		try {
			OthelloModel othelloModel = new OthelloModel(4);
			
			othelloModel.setTurn(1);
			assertSame(othelloModel.getTurn(), 1);
			
			othelloModel.setTurn(0);
			assertSame(othelloModel.getTurn(), 0);
			
			othelloModel.setTurn(-1);
			assertSame(othelloModel.getTurn(), -1);
		} catch (InvalidBoardSizeException e) {
			fail("Failed");
		}
	}

	@Test
	public void testEqualsObject() {
		try {
			OthelloModel othelloModel = new OthelloModel(4);
			
			// Argument is null
			assertFalse(othelloModel.equals(null));
			
			// Argument has a different type
			assertFalse(othelloModel.equals(new OthelloController(othelloModel)));
			
			// Argument has a different size
			assertFalse(othelloModel.equals(new OthelloModel(6)));
			
			// Argument has a different state
			OthelloModel othelloModel2 = new OthelloModel(4);
			othelloModel.setState(0, 0, 1);
			othelloModel2.setState(0, 0, -1);
			assertFalse(othelloModel.equals(othelloModel2));
			
			// Argument has a different turn
			othelloModel2.setState(0, 0, 1);
			othelloModel.setTurn(1);
			othelloModel2.setTurn(0);
			assertFalse(othelloModel.equals(othelloModel2));
		} catch (InvalidBoardSizeException | BoardIndexOutOfBoundsException e) {
			fail("Failed");
		}
	}

}
