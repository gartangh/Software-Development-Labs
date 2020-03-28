package alarmevent;

import java.util.Random;

import eventbroker.Event;
import eventbroker.EventBroker;
import eventbroker.EventListener;

public class PoliceDepartment implements EventListener {

	public PoliceDepartment() {
		EventBroker.getEventBroker().addEventListener("assault", this);
		EventBroker.getEventBroker().addEventListener("fire", this);
		EventBroker.getEventBroker().addEventListener("crash", this);
		EventBroker.getEventBroker().addEventListener("all", this);
	}

	public void handleEvent(Event e) {
		Random random = new Random();
		if (e instanceof AlarmEvent)
			System.out.println("Police unit " + random.nextInt(10) + " is responding to: " + e.getMessage());
		else
			System.out.println("The" + e.getType() + "is not an AlarmEvent");
	}

}
