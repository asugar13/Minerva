package generation;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import businessobject.CourseListing;
import businessobject.CourseOffering;
import businessobject.CourseSelection;

public class Generator {
	private Map<String, CourseListing> courseDB;
	
	public Generator(Map<String, CourseListing> db) {
		courseDB = db;
	}
	
	public Set<CourseOffering> generate(Set<CourseSelection> courses) {
		Set<List<String>> coursesToSemesters = SemesterGenerator.coursesToSemesters(courses);
		
		Set<CourseOffering> timetables = new HashSet<>();
		
		coursesToSemesters.forEach((coursesss) -> {
			timetables.addAll(TimetableGenerator.coursesToTimetables(coursesss));
		});
		
		
		return timetables;
	}
	
	
	
	
	
	
	
	
	
	private static class SemesterGenerator {
		/*
		 * return - Each set member is a list of full course names of the courses that will be in the same timetable.
		 */
		private static Set<List<String>> coursesToSemesters(Set<CourseSelection> courses) {
			return null;
		}
	}
	
	
	private static class TimetableGenerator {
		/*
		 * return - Each set member is a possible timetable. null if there isn't any.
		 */
		private static Set<CourseOffering> coursesToTimetables(List<String> courses) {
			return null;
		}
	}
}
