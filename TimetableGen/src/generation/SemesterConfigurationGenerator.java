package generation;

import java.util.Set;

import businessobject.CourseSelection;
import businessobject.TimetableConfiguration;
import dao.CourseListingDao;

public class SemesterConfigurationGenerator implements TimetableConfigurationGenerator {
	private CourseListingDao listingDao;

	public SemesterConfigurationGenerator(CourseListingDao listingDao) {
		this.listingDao = listingDao;
	}

	@Override
	public Set<TimetableConfiguration> generateConfigurations(Set<CourseSelection> courses) {
		// TODO Uses listingDao to get CourseOffering for each course, creates
		// all possible TimetableConfigurations for 2 semesters given the
		// restrictions in CourseSelection and a maximum of 6 courses per
		// semester
		return null;
	}
}
