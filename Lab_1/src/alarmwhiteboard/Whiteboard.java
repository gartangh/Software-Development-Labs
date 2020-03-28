package alarmwhiteboard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import alarm.AlarmListener;

/*
 *
 * De naam van de klasse is aangepast.
 * Dit bestand moest daarna terug van naam veranderen om te kunnen indienen!
 *
 */

public class AlarmWhiteboard {

	private static AlarmWhiteboard singleton = new AlarmWhiteboard();

	private static Map<String, Set<AlarmListener>> listeners = new HashMap<>();

	public static AlarmWhiteboard getAlarmWhiteboard() {
		return singleton;
	}

	public void addAlarmListener(String type, AlarmListener s) {
		if (listeners.get(type) == null) {
			Set<AlarmListener> newSet = new HashSet<>();
			listeners.put(type, newSet);
		}

		listeners.get(type).add(s);
	}

	public void removeAlarmListener(AlarmListener s) {
		for (Map.Entry<String, Set<AlarmListener>> entry : listeners.entrySet()) {
			if (entry.getValue() == s)
				listeners.get(entry.getKey()).remove(s);
		}
	}
	
	public Set<AlarmListener> getAlarmListeners(String t) {
		for (Map.Entry<String, Set<AlarmListener>> entry : listeners.entrySet()) {
			if (entry.getKey().equals(t)) {
				return entry.getValue();
			}
		}

		return null;
	}
}
