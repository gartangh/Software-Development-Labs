package eventbroker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

final public class EventBroker {

	protected Map<String, ArrayList<EventListener>> listeners = new HashMap<>();

	protected final static EventBroker broker = new EventBroker();

	private EventBroker() {
		// Empty constructor
	}

	public static EventBroker getEventBroker() {
		return broker;
	}

	public void addEventListener(EventListener el) {
		if (listeners.get("all") == null) {
			ArrayList<EventListener> al = new ArrayList<>();
			listeners.put("all", al);
		}

		listeners.get("all").add(el);
	}

	public void addEventListener(String type, EventListener el) {
		if (listeners.get(type) == null) {
			ArrayList<EventListener> al = new ArrayList<>();
			listeners.put(type, al);
		}

		listeners.get(type).add(el);
	}

	public void removeEventListener(EventListener el) {
		for (Map.Entry<String, ArrayList<EventListener>> entry : listeners.entrySet()) {
			if(entry.getValue() == el)
				listeners.get(entry.getKey()).remove(el);
		}
	}

	void addEvent(EventPublisher source, Event e) {
		process(source, e);
	}

	private void process(EventPublisher source, Event e) {
		for (Map.Entry<String, ArrayList<EventListener>> entry : listeners.entrySet()) {
			if(entry.getKey().equals(e.type)) {
				for(EventListener el : entry.getValue()) {
					el.handleEvent(e);
				}
			}
		}
	}

}
