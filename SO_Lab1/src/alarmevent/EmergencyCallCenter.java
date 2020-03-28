package alarmevent;

import eventbroker.EventPublisher;

public class EmergencyCallCenter extends EventPublisher {
	private String emergencyNumber;

	public EmergencyCallCenter(String emergencyNumber) {
		this.emergencyNumber = emergencyNumber;
	}

	public void incomingCall(String type, String location) {
		System.out.println("->Incoming call on number " + emergencyNumber);
		AlarmEvent ae = new AlarmEvent(type, location);
		publishEvent(ae);
	}

}
