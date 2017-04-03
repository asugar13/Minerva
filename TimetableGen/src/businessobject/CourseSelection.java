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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseCode == null) ? 0 : courseCode.hashCode());
		result = prime * result + ((semesterRestrictions == null) ? 0 : semesterRestrictions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseSelection other = (CourseSelection) obj;
		if (courseCode == null) {
			if (other.courseCode != null)
				return false;
		} else if (!courseCode.equals(other.courseCode))
			return false;
		if (semesterRestrictions == null) {
			if (other.semesterRestrictions != null)
				return false;
		} else if (!semesterRestrictions.equals(other.semesterRestrictions))
			return false;
		return true;
	}
}
