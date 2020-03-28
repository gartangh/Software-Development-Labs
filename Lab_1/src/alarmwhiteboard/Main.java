package alarmwhiteboard;

import alarm.FireDepartment;
import alarm.Hospital;
import alarm.PoliceDepartment;

import alarmwhiteboard.EmergencyCallCenter;

public class Main {

	public static void main(String[] args){
		Hospital hospitalUZ = new Hospital("UZ");
		Hospital hospitalAZ = new Hospital("AZ");
		PoliceDepartment policeDepartmentGhent = new PoliceDepartment();
		FireDepartment fireDepartmentGhent = new FireDepartment();
		FireDepartment fireDepartmentAntwerp = new FireDepartment();

		EmergencyCallCenter callCenter101 = new EmergencyCallCenter("101");
		EmergencyCallCenter callCenter112 = new EmergencyCallCenter("112");

		// Glue code (not yet complete)
		AlarmWhiteboard singleton = AlarmWhiteboard.getAlarmWhiteboard();
		singleton.addAlarmListener("fire", hospitalUZ);
		singleton.addAlarmListener("crash", hospitalUZ);
		singleton.addAlarmListener("fire", hospitalAZ);
		singleton.addAlarmListener("crash", hospitalAZ);
		singleton.addAlarmListener("all", policeDepartmentGhent);
		singleton.addAlarmListener("fire", policeDepartmentGhent);
		singleton.addAlarmListener("crash", policeDepartmentGhent);
		singleton.addAlarmListener("assault", policeDepartmentGhent);
		singleton.addAlarmListener("fire", fireDepartmentGhent);
		singleton.addAlarmListener("fire", fireDepartmentAntwerp);

		callCenter101.incomingCall("crash","De Sterre");
		callCenter112.incomingCall("crash", "Plateaustraat");
		callCenter112.incomingCall("assault", "Veldstraat");
		callCenter112.incomingCall("fire", "Zwijnaardse Steenweg");
		callCenter112.incomingCall("fire", "GUSB");
		callCenter112.incomingCall("all", "De Krook");
	}

}
