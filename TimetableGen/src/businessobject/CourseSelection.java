package businessobject;

import java.util.Set;

import enums.ClassType;

public class CourseSelection {
	public String courseCode;

	/**
	 * If FALL and WINTER then can be in either, if only one then can only be in
	 * one, if YEAR then must be in both
	 **/
	public Set<ClassType> semesterRestrictions;

	public CourseSelection(String courseCode, Set<ClassType> semesterRestrictions) {
		this.courseCode = courseCode;
		this.semesterRestrictions = semesterRestrictions;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public Set<ClassType> getRestrictions() {
		return semesterRestrictions;
	}
}
