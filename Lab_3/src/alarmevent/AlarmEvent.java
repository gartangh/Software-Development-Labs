package alarmevent;

import eventbroker.Event;

@SuppressWarnings("serial")
public class AlarmEvent extends Event {

	private final String location;

	public AlarmEvent(String type, String location) {
		super(type, type + " at " + location);
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

}
