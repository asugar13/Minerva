package businessobject;

import java.util.List;
import java.util.Set;

public interface TimetableConfiguration {
	public List<Set<CourseOffering>> getSemesterConfigurations();
	public String toJsonString();
	//(Don't worry about these yet)
	//public void addTimetable(Timetable timetable);
	//public void addTimetables(List<Timetable timetables)
}
