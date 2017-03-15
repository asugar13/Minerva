package generation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import businessobject.CourseListing;
import businessobject.CourseOffering;
import businessobject.CourseSelection;
import businessobject.TimetableConfiguration;
import dao.CourseListingDao;
import enums.ClassType;
import enums.SemesterType;

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
		HashSet<CourseListing> semester1 = new HashSet<>(); //set of courses in the 1st semester
		HashSet<CourseListing> semester2 = new HashSet<>(); //set of courses in the 2nd semester
		Map<String, CourseListing> allCourseListings = this.listingDao.getAllCourseListings();

		
		for (CourseSelection course : courses){
			CourseSelection currentCourse = course;
//			boolean flagFall = false;
//			boolean flagWinter = false;
//			Set<SemesterType> restrictions = course.getRestrictions();
//			for (SemesterType semestertype : restrictions){
//				if (semestertype == SemesterType.FALL) {
//					flagFall = true;
//				}
//				else if (semestertype == SemesterType.WINTER){
//					flagWinter = true;
//				}
//				else if (semestertype == SemesterType.YEAR) {
//					
//				}
//			}
			if (allCourseListings.containsKey(currentCourse.getCourseCode())){
				CourseListing courseListing = allCourseListings.get(currentCourse.getCourseCode());
				Iterator<SemesterType> offeredIn = courseListing.getSemesterToCourse().keySet().iterator();
				while (offeredIn.hasNext()){
					SemesterType term = offeredIn.next();
					if (term == SemesterType.WINTER) {
						semester1.add(courseListing);
					}
					else if (term == SemesterType.FALL){
						semester2.add(courseListing);
					}
					else if (term == SemesterType.YEAR){
						semester1.add(courseListing);
						semester2.add(courseListing);
					}
				}
			}
			
		}
		
		return null;
	}
}
