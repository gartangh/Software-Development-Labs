package game.events;

import eventbroker.Event;

@SuppressWarnings("serial")
public class GameInviteDeclinedEvent extends Event {

	public final static String type = "GAME_INVITE_DECLINED";

	public GameInviteDeclinedEvent() {
		super.type = type;
	}

}
