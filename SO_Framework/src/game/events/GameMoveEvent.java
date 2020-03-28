package game.events;

import eventbroker.Event;

@SuppressWarnings("serial")
public class GameMoveEvent extends Event {

	public final static String type = "GAME_MOVE";
	private int x;
	private int y;

	public GameMoveEvent(int x, int y) {
		super.type = type;
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
