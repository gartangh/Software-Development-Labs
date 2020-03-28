package alarmevent;

import eventbroker.Event;
import eventbroker.EventBroker;
import eventbroker.EventListener;

public class Hospital implements EventListener {

	private String name;

	private Hospital(String name) {
		this.name = name;
	}

	public static void createHospital(String name) {
		Hospital hospital = new Hospital(name);
		EventBroker.getEventBroker().addEventListener("fire", hospital);
		EventBroker.getEventBroker().addEventListener("crash", hospital);
	}

	public String getName() {
		return name;
	}

	@Override
	public void handleEvent(Event e) {
		if (e instanceof AlarmEvent) {
			AlarmEvent alarm = (AlarmEvent) e;
			System.out.println(name + " sends an ambulance to " + alarm.getLocation() + " for " + alarm.getType());
		}
	}

}
