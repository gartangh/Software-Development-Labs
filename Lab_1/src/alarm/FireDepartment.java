package alarm;

import java.util.Random;

import alarmwhiteboard.AlarmWhiteboard;

public class FireDepartment implements AlarmListener {
	Random random = new Random();

	@Override
	public void alarm(Alarm alarm) {
		System.out.println("Fire unit " + random.nextInt(10) + " is checking out the " + alarm.getType() + " at "+alarm.getLocation());
	}

}
