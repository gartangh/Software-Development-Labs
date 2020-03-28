package alarmwhiteboard;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import alarm.Alarm;
import alarm.AlarmListener;
import alarm.PoliceDepartment;
import alarm.FireDepartment;
import alarm.Hospital;

public class EmergencyCallCenter {

	private String emergencyNumber;

	public EmergencyCallCenter(String number) {
		this.emergencyNumber = number;
	}

	public void incomingCall(String type, String location) {
		System.out.println("->Incoming call on number " + emergencyNumber);
		Set<AlarmListener> als = AlarmWhiteboard.getAlarmWhiteboard().getAlarmListeners(type);
		if (als != null) {
			ArrayList<AlarmListener> fire = new ArrayList<>();
			ArrayList<AlarmListener> police = new ArrayList<>();
			ArrayList<AlarmListener> hospital = new ArrayList<>();

			for (AlarmListener al : als) {
				if (al instanceof PoliceDepartment)
					police.add(al);
				else if (al instanceof FireDepartment)
					fire.add(al);
				else if (al instanceof Hospital)
					hospital.add(al);
				else
					System.out.println("Not a correct instance!");
			}

			Alarm a = new Alarm(type, location);
			Random random = new Random();
			switch (type) {
			case "fire":
				if(!fire.isEmpty())
					fire.get(random.nextInt(fire.size())).alarm(a);
				else
					System.out.println("fire is empty!");
				break;
			case "crash":
				if(!police.isEmpty())
					police.get(random.nextInt(police.size())).alarm(a);
				else
					System.out.println("police is empty!");
				
				if(!hospital.isEmpty())
					hospital.get(random.nextInt(hospital.size())).alarm(a);
				else
					System.out.println("hospital is empty!");
				break;
			case "assault":
				if(!police.isEmpty())
					police.get(random.nextInt(police.size())).alarm(a);
				else
					System.out.println("police is empty!");
				break;
			default:
				if(!police.isEmpty())
					(police.get(random.nextInt(police.size()))).alarm(a);
				else
					System.out.println("police is empty!");
			}
		}
		else
			System.out.println("No listeners for this type");
	}

}
