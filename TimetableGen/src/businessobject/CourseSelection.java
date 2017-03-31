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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Course Code: " + courseCode + "\n");
		sb.append("Semester Rescrictions: \n");
		semesterRestrictions.forEach((s) -> {
			switch (s) {
			case FALL:
				sb.append("\tFall\n");
				break;
			case WINTER:
				sb.append("\tWinter\n");
				break;
			case YEAR:
				sb.append("\tYear\n");
				break;
			default:
				break;
			}
		});

		return sb.toString();
	}
}
