package alarmevent;

import java.net.InetAddress;
import java.net.UnknownHostException;

import eventbroker.EventBroker;
import network.Network;

public class Client {

	public static void main(String[] args) {
		final int serverPort = 1024;

		// Client 1
		Network network = new Network();

		try {
			// Client 2
			network.connect(InetAddress.getLocalHost(), serverPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();

			return;
		}

		EventBroker eventBroker = EventBroker.getEventBroker();
		eventBroker.addEventListener(network);
		eventBroker.start();

		EmergencyCallCenter callCenter101 = new EmergencyCallCenter("101");
		EmergencyCallCenter callCenter112 = new EmergencyCallCenter("112");

		callCenter101.incomingCall("crash", "Plateaustraat");
		callCenter101.incomingCall("assault", "Veldstraat");
		callCenter112.incomingCall("fire", "Zwijnaardse Steenweg");
		callCenter112.incomingCall("assault", "Overpoortstraat");

		synchronized (eventBroker) {
			eventBroker.setProceed(true);
			eventBroker.notifyAll();
		}

		synchronized (eventBroker) {
			eventBroker.stop(network);
		}
	}

}
