package businessobject;

import java.util.List;

public class Timetable {
	List<TimetableConfiguration> timetableConfigurations;

	public Timetable(List<TimetableConfiguration> timetableConfigurations) {
		this.timetableConfigurations = timetableConfigurations;
	}

	public List<TimetableConfiguration> getTimetableConfigurations() {
		return timetableConfigurations;
	}

	public String toJsonString() {
		return null;
	}
}
