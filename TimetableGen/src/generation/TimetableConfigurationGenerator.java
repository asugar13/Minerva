package generation;

import java.util.Set;

import businessobject.CourseSelection;
import businessobject.TimetableConfiguration;

public interface TimetableConfigurationGenerator {

	/**
	 * Generate all possible TimetableConfigurations given a set of courses and
	 * restrictions. Max 6 courses per semester
	 * 
	 * @param courses
	 *            Set of desired courses and restrictions
	 * @return The set of all possible timetable configurations
	 */
	public Set<TimetableConfiguration> generateConfigurations(Set<CourseSelection> courses);
}
