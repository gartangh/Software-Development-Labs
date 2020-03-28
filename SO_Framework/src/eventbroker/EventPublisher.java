package eventbroker;

public class EventPublisher {

	public void publishEvent(Event e) {
		// 1.1.1
		EventBroker.getEventBroker().addEvent(this, e);
	}

}
