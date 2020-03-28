package alarmevent;

import eventbroker.EventBroker;
import network.Network;

public class Server {

	public static void main(String[] args) {
		final int serverPort = 1024;

		// Server 1
		new Network(serverPort);

		EventBroker eventBroker = EventBroker.getEventBroker();
		eventBroker.start();

		Hospital.createHospital("UZ");
		Hospital.createHospital("AZ");
		PoliceDepartment.createPoliceDepartment();
		FireDepartment.createFireDepartment();
	}

}
