package alarmevent;

import eventbroker.Event;
import eventbroker.EventBroker;
import eventbroker.EventListener;

public class Hospital implements EventListener {

	private String name;

	public String getName(){
		return name;
	}

	public Hospital(String name) {
		this.name = name;
		EventBroker.getEventBroker().addEventListener("fire", this);
		EventBroker.getEventBroker().addEventListener("crash", this);
	}

	public void handleEvent(Event e) {
		if (e instanceof AlarmEvent)
			System.out.println(name + " sends an ambulance for: "  + e.getMessage());
		else
			System.out.println("The" + e.getType() + "is not an AlarmEvent");
	}

}
