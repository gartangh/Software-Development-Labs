package alarmevent;

import java.util.Random;

import eventbroker.Event;
import eventbroker.EventBroker;
import eventbroker.EventListener;

public class FireDepartment implements EventListener {

	public FireDepartment() {
		EventBroker.getEventBroker().addEventListener("fire", this);
	}

	public void handleEvent(Event e) {
		Random random = new Random();
		if (e instanceof AlarmEvent)
			System.out.println("Fire unit " + random.nextInt(10) + " is responding to: " + e.getMessage());
		else
			System.out.println("The " + e.getType() +  "is not an AlarmEvent");
	}

}
