package dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import businessobject.ClassTime;
import businessobject.Course;
import businessobject.CourseListing;
import enums.CampusType;
import enums.ClassType;
import enums.SemesterType;

public class CourseLoader implements CourseListingDao {
	
	public static final String FILE_PATH = "res/courses.json";
	
	// HashMap<courseCode, CourseListing>.
	private Map<String, CourseListing> courseDB;
	
	public CourseLoader(String path) {
		JSONParser parser = new JSONParser();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			while (br.ready()) {
				JSONObject o = (JSONObject) parser.parse(br.readLine());
				load(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void load(JSONObject o) {
		String courseCode = (String) o.get("code");
		String term = (String) o.get("term");
		SemesterType semester;
		switch (courseCode.substring(6, courseCode.length() + 1)) {
		case "H1F":
			semester = SemesterType.FALL;
			break;
		case "H1S":
			semester = SemesterType.WINTER;
			break;
		case "Y1Y":
			semester = SemesterType.YEAR;
			break;
		default:
			semester = null;
			break;
		}
		
		CampusType campus;
		switch ((String) o.get("campus")) {
		case "UTSG":
			campus = CampusType.UTSG;
			break;
		default:
			campus = null;
			break;
		}
		
		Course newCourse = new Course(
				(String) o.get("name"),
				(String) courseCode,
				(String) o.get("description"),
				(String) o.get("prerequisites"),
				(String) o.get("exclusions"),
				Integer.parseInt(term.substring(0, 4)),
				campus,
				semester,
				loadClassTimes((JSONArray) o.get("meeting_sections")),
				loadBreadths((JSONArray) o.get("breadths")));
		
		Map<SemesterType, Course> semesterToCourse = new HashMap<>();
		semesterToCourse.put(semester, newCourse);
		CourseListing cl = new CourseListing(courseCode, semesterToCourse);
		courseDB.put(courseCode, cl);
	}
	
	private Map<ClassType, List<ClassTime>> loadClassTimes(JSONArray a) {
		
	}
	
	private List<Integer> loadBreadths(JSONArray a) {
		
	}

	@Override
	public Map<String, CourseListing> getCourseListing(String courseCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, CourseListing> getCourseBySemester(String courseCode,
			SemesterType semester) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, CourseListing> getCourseListings(List<String> courseCodes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, CourseListing> getCourseListingsBySemester(
			List<SimpleEntry<String, SemesterType>> courses) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, CourseListing> getAllCourseListings() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		new CourseLoader(FILE_PATH);
	}
}
