package alarmevent;

import eventbroker.Event;
import eventbroker.EventBroker;
import eventbroker.EventListener;

public class FireDepartment implements EventListener {

	private FireDepartment() {
		// Empty constructor
	}

	// Factory method
	public static void createFireDepartment() {
		EventBroker.getEventBroker().addEventListener("fire", new FireDepartment());
	}

	@Override
	public void handleEvent(Event e) {
		if (e instanceof AlarmEvent) {
			AlarmEvent alarm = (AlarmEvent) e;
			System.out.println("Fire squad on the move to " + alarm.getLocation() + " for " + alarm.getType());
		}
	}

}
