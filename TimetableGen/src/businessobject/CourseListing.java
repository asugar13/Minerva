package businessobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import enums.SemesterType;

public class CourseListing {
	String courseCode;
	Map<SemesterType, Course> semesterToCourse;

	public CourseListing(String courseCode, Map<SemesterType, Course> semesterToCourse) {
		this.semesterToCourse = semesterToCourse;
		this.courseCode = courseCode;
	}

	public void addCourse(SemesterType semester, Course course) {
		this.semesterToCourse.put(semester, course);
	}

	public String getCourseCode() {
		return courseCode;
	}

	public Course getCourseBySemester(SemesterType semesterType) {
		return semesterToCourse.get(semesterType);
	}

	public Map<SemesterType, Course> getSemesterToCourse() {
		return semesterToCourse;
	}

	public JSONObject toJsonObject() {
		JSONObject courseListingJson = null;
		JSONArray semesters = new JSONArray();
		for (Entry<SemesterType, Course> courseEntry : semesterToCourse.entrySet()) {
			if (courseListingJson == null) {
				courseListingJson = courseEntry.getValue().toJsonObject();
			}
			switch (courseEntry.getKey()) {
			case FALL:
				semesters.add("F");
				break;
			case WINTER:
				semesters.add("S");
				break;
			default:
				semesters.add("Y");
				break;
			}
		}
		courseListingJson.put("semesters", semesters);
		return courseListingJson;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (semesterToCourse.containsKey(SemesterType.FALL)) {
			Course c = semesterToCourse.get(SemesterType.FALL);
			sb.append(c.toString() + "\n");
		}

		if (semesterToCourse.containsKey(SemesterType.WINTER)) {
			Course c = semesterToCourse.get(SemesterType.WINTER);
			sb.append(c.toString() + "\n");
		}

		if (semesterToCourse.containsKey(SemesterType.YEAR)) {
			Course c = semesterToCourse.get(SemesterType.YEAR);
			sb.append(c.toString() + "\n");
		}

		return sb.toString();
	}
}
