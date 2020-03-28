package game.events;

import eventbroker.Event;

@SuppressWarnings("serial")
public class GameInvitationEvent extends Event {
	
	public final static String type = "GAME_INVITATION";
	
	public GameInvitationEvent() {
		super.type = type;
	}

}
