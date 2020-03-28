package eventbroker;

public interface EventListener {

	// Package local would be safer
	public void handleEvent(Event e);

}
