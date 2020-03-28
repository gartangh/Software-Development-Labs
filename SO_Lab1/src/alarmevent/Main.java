package alarmevent;

public class Main {

	public static void main(String[] args) {
		Hospital hospitalUZ = new Hospital("UZ");
		Hospital hospitalAZ = new Hospital("AZ");
		PoliceDepartment policeDepartmentGhent = new PoliceDepartment();
		FireDepartment fireDepartmentGhent = new FireDepartment();
		FireDepartment fireDepartmentAntwerp = new FireDepartment();

		EmergencyCallCenter callCenter101 = new EmergencyCallCenter("101");
		EmergencyCallCenter callCenter112 = new EmergencyCallCenter("112");

		callCenter101.incomingCall("crash","De Sterre");
		callCenter112.incomingCall("crash", "Plateaustraat");
		callCenter112.incomingCall("assault", "Veldstraat");
		callCenter112.incomingCall("fire", "Zwijnaardse Steenweg");
		callCenter112.incomingCall("fire", "GUSB");
	}	

}
