package handlers;

import java.util.List;
import java.util.Set;

import businessobject.CourseSelection;
import businessobject.TimetableConfiguration;

public interface TimetableGenerationHandler {

	/**
	 * Generates all possible timetables given a set of courses and restrictions
	 * 
	 * @param courseSelections
	 *            Desired courses with associated restrictions
	 * @return List of all possible TimetableConfigurations containing all
	 *         possible timetables for each configuration
	 */
	public List<TimetableConfiguration> generateTimetables(Set<CourseSelection> courseSelections);
}
