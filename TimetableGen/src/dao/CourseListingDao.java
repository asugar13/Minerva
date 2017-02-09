package dao;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Set;

import businessobject.CourseListing;
import businessobject.SemesterType;

public interface CourseListingDao {
	
	public Set<CourseListing> getCourseListing(String courseCode);
	
	public Set<CourseListing> getCourseBySemester(String courseCode, SemesterType semester);

	public Set<CourseListing> getCourseListings(List<String> courseCodes);
	
	public Set<CourseListing> getCourseListingsBySemester(List<SimpleEntry<String, SemesterType>> courses);
	
	public Set<CourseListing> getAllCourseListings();
	
}
