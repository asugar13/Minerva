package generation;

import java.util.Set;

import businessobject.CourseSelection;
import businessobject.TimetableConfiguration;

public interface TimetableConfigurationGenerator {
	
	/**
	 * Generate all possible TimetableConfigurations
	 * @param courses
	 * @return
	 */
	public Set<TimetableConfiguration> generateConfigurations(Set<CourseSelection> courses);
}
