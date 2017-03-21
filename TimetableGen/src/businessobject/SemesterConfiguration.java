package businessobject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SemesterConfiguration implements TimetableConfiguration {

	/** Maximum courses for a semester is 6 **/
	private Set<CourseOffering> semester1; // set of courses in the 1st semester
	private Set<CourseOffering> semester2; // set of courses in the 2nd semester

	// private List<Timetable> possibleTimetables (don't worry about this yet,
	// timetable generator will use this)

	public SemesterConfiguration(Set<CourseOffering> semester1,
			Set<CourseOffering> semester2) {
		this.semester1 = semester1;
		this.semester2 = semester2;
	}

	@Override
	public List<Set<CourseOffering>> getSemesterConfigurations() {
		List<Set<CourseOffering>> semesterConfigurations = new ArrayList<>();
		semesterConfigurations.add(semester1);
		semesterConfigurations.add(semester2);
		return semesterConfigurations;
	}

	@Override
	public String toJsonString() {
		// TODO
		return null;
	}
}
