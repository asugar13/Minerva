package businessobject;

import java.util.Map;

import enums.SemesterType;

public class CourseListing {
	String courseCode;
	Map<SemesterType, Course> semesterToCourse;

	public CourseListing(String courseCode, Map<SemesterType, Course> semesterToCourse) {
		this.semesterToCourse = semesterToCourse;
		this.courseCode = courseCode;
	}
	
	public String getCourseCode(){
		return courseCode;
	}

	public Course getCourseBySemester(SemesterType semesterType){
		return semesterToCourse.get(semesterType);
	}

}