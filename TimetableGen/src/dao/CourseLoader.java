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

	/**
	 * Constructs CourseLoader object.
	 * Reads json file in path path line by line and populates courseDB field.
	 * 
	 * @param path - path to json file.
	 */
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

	/**
	 * Translates given JSONObject and populates courseDB field.
	 * 
	 * @param o - a JSONObject that represents a course.
	 */
	private void load(JSONObject o) {
		String fullCourseCode = (String) o.get("code");
		String courseCode = fullCourseCode.substring(0, 6);
		
		SemesterType semester = figureSemesterType(fullCourseCode.charAt(fullCourseCode.length() - 1));
		CampusType campus = figureCampusType((String) o.get("campus"));

		String term = (String) o.get("term");
		Course newCourse = new Course((String) o.get("name"),
				(String) courseCode, (String) o.get("description"),
				(String) o.get("prerequisites"), (String) o.get("exclusions"),
				Integer.parseInt(term.substring(0, 4)), campus, semester,
				loadClassTimes((JSONArray) o.get("meeting_sections")),
				loadBreadths((JSONArray) o.get("breadths")));

		Map<SemesterType, Course> semesterToCourse = new HashMap<>();
		semesterToCourse.put(semester, newCourse);

		if (courseDB.containsKey(fullCourseCode)) {
			courseDB.get(fullCourseCode).addCourse(semester, newCourse);
		} else {
			CourseListing cl = new CourseListing(courseCode, semesterToCourse);
			courseDB.put(fullCourseCode, cl);
		}
	}
	
	/**
	 * Iterates through given JSONArray and returns a list of integers that represent a breadth.
	 * 
	 * @param a
	 * @return
	 */
	private List<Integer> loadBreadths(JSONArray a) {
		// Result could be empty.
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < a.size(); i++) {
			result.add(Math.toIntExact((long) a.get(i)));
		}
		return result;
	}
	
	/**
	 * Returns CampusType of given campus.
	 * 
	 * @param campus
	 * @return - CampusType
	 */
	private CampusType figureCampusType(String campus) {
		CampusType result;
		switch (campus) {
		case "UTSG":
			result = CampusType.UTSG;
			break;
		case "UTM":
			result = CampusType.UTM;
			break;
		case "UTSC":
			result = CampusType.UTSC;
			break;
		default:
			result = null;
			break;
		}
		return result;
	}
	
	/**
	 * Returns SemesterType of given semester.
	 * 
	 * @param semester
	 * @return
	 */
	private SemesterType figureSemesterType(char semester) {
		SemesterType result;
		switch (semester) {
		case 'F':
			result = SemesterType.FALL;
			break;
		case 'S':
			result = SemesterType.WINTER;
			break;
		case 'Y':
			result = SemesterType.YEAR;
			break;
		default:
			result = null;
			break;
		}
		return result;
	}
	
	/**
	 * Returns ClassType of given classCode.
	 * 
	 * @param classCode
	 * @return
	 */
	private ClassType figureClassType(String classCode) {
		ClassType result;
		switch (classCode.charAt(0)) {
		case 'L':
			result = ClassType.LEC;
			break;
		case 'T':
			result = ClassType.TUT;
			break;
		case 'P':
			result = ClassType.PRA;
			break;
		default:
			result = null;
			break;
		}
		return result;
	}
	
	/**
	 * Returns Day of given day.
	 * 
	 * @param day
	 * @return
	 */
	private Day figureDay(String day) {
		Day result;
		switch (day) {
		case "MONDAY":
			result = Day.MONDAY;
			break;
		case "TUESDAY":
			result = Day.TUESDAY;
			break;
		case "WEDNESDAY":
			result = Day.WEDNESDAY;
			break;
		case "THURSDAY":
			result = Day.THURSDAY;
			break;
		case "FRIDAY":
			result = Day.FRIDAY;
			break;
		default:
			result = null;
			break;
		}
		return result;
	}
	
	/**
	 * Iterates through given JSONArray and returns a map of ClassType and corresponding list of ClassTimes.
	 * 
	 * @param a - JSON array that represents class times.
	 * @return
	 */
	private Map<ClassType, List<ClassTime>> loadClassTimes(JSONArray a) {
		Map<ClassType, List<ClassTime>> result = new HashMap<>();
		
		for (int i1 = 0; i1 < a.size(); i1++) {
			JSONObject o = (JSONObject) a.get(i1);
			
			String classCode = (String) o.get("code");
			ClassType classType = figureClassType(classCode);			
			
			List<TimeSlot> times = new ArrayList<>();
			
			JSONArray array = (JSONArray) o.get("times");
			for (int i2 = 0; i2 < array.size(); i2++) {
				JSONObject ob = (JSONObject) array.get(i2);
				
				Day day = figureDay((String) ob.get("day"));
				
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
	
	/* ======================================================================================================================= */

	/* For testing purposes */
	public static void main(String[] args) {
		CourseLoader cl = new CourseLoader(FILE_PATH);
		Map<String, CourseListing> acl = cl.getAllCourseListings();
		
		CourseListing c1 = acl.get("CSC301H1S");
		System.out.println(c1.toString());
		
		CourseListing c2 = acl.get("ECO105Y1Y");
		System.out.println(c2.toString());
		
		CourseListing c3 = acl.get("CSC207H1S");
		System.out.println(c3.toString());
	}
}