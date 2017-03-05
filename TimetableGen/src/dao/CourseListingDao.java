package dao;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;

import businessobject.CourseListing;
import enums.SemesterType;

public interface CourseListingDao {
	
	//private Map<courseCode:String, courseListing:CourseListing> courseListings
	
	public Map<String, CourseListing> getCourseListing(String courseCode);
	
	public Map<String, CourseListing> getCourseBySemester(String courseCode, SemesterType semester);

	public Map<String, CourseListing> getCourseListings(List<String> courseCodes);
	
	public Map<String, CourseListing> getCourseListingsBySemester(List<SimpleEntry<String, SemesterType>> courses);
	
	public Map<String, CourseListing> getAllCourseListings();
	
}
