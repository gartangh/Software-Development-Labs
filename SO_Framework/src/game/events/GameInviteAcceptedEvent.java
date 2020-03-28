package game.events;

import eventbroker.Event;

@SuppressWarnings("serial")
public class GameInviteAcceptedEvent extends Event {

	public final static String type = "GAME_INVITE_ACCEPTED";

	public GameInviteAcceptedEvent() {
		super.type = type;
	}

}
