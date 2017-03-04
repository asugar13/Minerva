package dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import businessobject.ClassTime;
import businessobject.Course;
import businessobject.CourseListing;
import businessobject.TimeSlot;
import enums.CampusType;
import enums.ClassType;
import enums.Day;
import enums.SemesterType;

public class CourseLoader implements CourseListingDao {

	public static final String FILE_PATH = "res/courses.json";

	// HashMap<courseCode, CourseListing>.
	private Map<String, CourseListing> courseDB;

	@SuppressWarnings("resource")
	public CourseLoader(String path) {
		courseDB = new HashMap<>();
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
		String fullCourseCode = (String) o.get("code");
		String courseCode = fullCourseCode.substring(0, 6);
		SemesterType semester;
		switch (fullCourseCode.substring(6, fullCourseCode.length())) {
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

		String term = (String) o.get("term");
		Course newCourse = new Course((String) o.get("name"),
				(String) courseCode, (String) o.get("description"),
				(String) o.get("prerequisites"), (String) o.get("exclusions"),
				Integer.parseInt(term.substring(0, 4)), campus, semester,
				loadClassTimes((JSONArray) o.get("meeting_sections")),
				loadBreadths((JSONArray) o.get("breadths")));

		Map<SemesterType, Course> semesterToCourse = new HashMap<>();
		semesterToCourse.put(semester, newCourse);

		if (courseDB.containsKey(courseCode)) {
			courseDB.get(courseCode).addCourse(semester, newCourse);
		} else {
			CourseListing cl = new CourseListing(courseCode, semesterToCourse);
			courseDB.put(courseCode, cl);
		}
	}

	private Map<ClassType, List<ClassTime>> loadClassTimes(JSONArray a) {
		Map<ClassType, List<ClassTime>> result = new HashMap<>();
		
		for (int i1 = 0; i1 < a.size(); i1++) {
			JSONObject o = (JSONObject) a.get(i1);
			
			String classCode = (String) o.get("code");
			ClassType classType;
			switch (classCode.charAt(0)) {
			case 'L':
				classType = ClassType.LEC;
				break;
			case 'T':
				classType = ClassType.TUT;
				break;
			case 'P':
				classType = ClassType.PRA;
				break;
			default:
				classType = null;
				break;
			}
			
			List<TimeSlot> times = new ArrayList<>();
			
			JSONArray array = (JSONArray) o.get("times");
			for (int i2 = 0; i2 < array.size(); i2++) {
				JSONObject ob = (JSONObject) array.get(i2);
				
				Day day;
				switch ((String) ob.get("day")) {
				case "Monday":
					day = Day.MONDAY;
					break;
				case "Tuesday":
					day = Day.TUESDAY;
					break;
				case "Wednesday":
					day = Day.WEDNESDAY;
					break;
				case "Thursday":
					day = Day.THURSDAY;
					break;
				case "Friday":
					day = Day.FRIDAY;
					break;
				default:
					day = null;
					break;
				}
				
				int start = Math.toIntExact((long) ob.get("start"));
				int duration = Math.toIntExact((long) ob.get("duration"));
				
				times.add(new TimeSlot(day, start, duration));
			}

			ClassTime ct = new ClassTime(classCode, times);
			
			if (result.containsKey(classType)) {
				result.get(classType).add(ct);
			} else {
				List<ClassTime> classTimeList = new ArrayList<>();
				classTimeList.add(ct);
				result.put(classType, classTimeList);
			}
		}
		
		return result;
	}

	private List<Integer> loadBreadths(JSONArray a) {
		// Result could be empty.
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < a.size(); i++) {
			result.add(Math.toIntExact((long) a.get(i)));
		}
		
		return result;
	}

	@Override
	public Map<String, CourseListing> getCourseListing(String courseCode) throws IllegalArgumentException {
		if (!courseDB.containsKey(courseCode)) {
			throw new IllegalArgumentException("Course code: " + courseCode + " does not exist.");
		}

		Map<String, CourseListing> result = new HashMap<>();
		result.put(courseCode, courseDB.get(courseCode));
		return result;
	}

	@Override
	public Map<String, CourseListing> getCourseBySemester(String courseCode, SemesterType semester) throws IllegalArgumentException {
		if (!courseDB.containsKey(courseCode)) {
			throw new IllegalArgumentException("Course code: " + courseCode + " does not exist.");
		} else if (courseDB.get(courseCode).getCourseBySemester(semester) == null) {
			throw new IllegalArgumentException("Course code: " + courseCode + " does not exist for semester " + semester.code() + ".");
		}

		Map<SemesterType, Course> semesterToCourse = new HashMap<>();
		semesterToCourse.put(semester, courseDB.get(courseCode).getCourseBySemester(semester));
		CourseListing cl = new CourseListing(courseCode, semesterToCourse);

		Map<String, CourseListing> result = new HashMap<>();
		result.put(courseCode, cl);
		return result;
	}

	@Override
	public Map<String, CourseListing> getCourseListings(List<String> courseCodes) throws IllegalArgumentException {
		Map<String, CourseListing> result = new HashMap<>();
		for (int i = 0; i < courseCodes.size(); i++) {
			String courseCode = courseCodes.get(i);
			
			if (!courseDB.containsKey(courseCode)) {
				throw new IllegalArgumentException("Course code: " + courseCode + " does not exist.");
			}

			result.put(courseCode, courseDB.get(courseCode));
		}
		return result;
	}

	@Override
	public Map<String, CourseListing> getCourseListingsBySemester(List<SimpleEntry<String, SemesterType>> courses) {
		Map<String, CourseListing> result = new HashMap<>();
		for (int i = 0; i < courses.size(); i++) {
			String courseCode = courses.get(i).getKey();
			SemesterType semester = courses.get(i).getValue();
			
			if (!courseDB.containsKey(courseCode)) {
				throw new IllegalArgumentException("Course code: " + courseCode + " does not exist.");
			} else if (courseDB.get(courseCode).getCourseBySemester(semester) == null) {
				throw new IllegalArgumentException("Course code: " + courseCode + " does not exist for semester " + semester.code() + ".");
			}
			
			Map<SemesterType, Course> semesterToCourse = new HashMap<>();
			semesterToCourse.put(semester, courseDB.get(courseCode).getCourseBySemester(semester));
			CourseListing cl = new CourseListing(courseCode, semesterToCourse);
			result.put(courseCode, cl);
		}
		return result;
	}

	@Override
	public Map<String, CourseListing> getAllCourseListings() {
		return courseDB;
	}

	public static void main(String[] args) {
		new CourseLoader(FILE_PATH);
	}
}
