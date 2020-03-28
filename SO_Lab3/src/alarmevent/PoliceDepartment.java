package alarmevent;

import eventbroker.Event;
import eventbroker.EventBroker;
import eventbroker.EventListener;
import java.util.Random;

public class PoliceDepartment implements EventListener {

	Random r = new Random();

	private PoliceDepartment() {
		// Empty constructor
	}
	
	// Factory method
	public static void createPoliceDepartment() {
		EventBroker.getEventBroker().addEventListener(new PoliceDepartment());
	}

	@Override
	public void handleEvent(Event e) {
		if (e instanceof AlarmEvent) {
			AlarmEvent alarm = (AlarmEvent) e;
			System.out.println("Police unit " + r.nextInt(10) + " is checking out the " + alarm.getType() + " at "
					+ alarm.getLocation());
		}
	}

}
