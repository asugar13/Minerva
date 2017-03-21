package businessobject;

import java.util.Set;

import enums.SemesterType;

public class CourseSelection {
	public String courseCode;

	/**
	 * If FALL and WINTER then can be in either, if only one then can only be in
	 * one, if YEAR then must be in both
	 **/
	public Set<SemesterType> semesterRestrictions;

	public CourseSelection(String courseCode, Set<SemesterType> semesterRestrictions) {
		this.courseCode = courseCode;
		this.semesterRestrictions = semesterRestrictions;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public Set<SemesterType> getRestrictions() {
		return semesterRestrictions;
	}
}
