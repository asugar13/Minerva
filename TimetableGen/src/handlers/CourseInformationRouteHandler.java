package handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import businessobject.Course;
import businessobject.CourseListing;
import dao.CourseListingDao;
import enums.SemesterType;
import spark.Request;
import spark.Response;
import spark.Route;

public class CourseInformationRouteHandler implements Route {

	private CourseListingDao listingDao;

	public CourseInformationRouteHandler(CourseListingDao listingDao) {
		this.listingDao = listingDao;
	}

	@Override
	public Object handle(Request arg0, Response arg1) throws Exception {
		listingDao.getAllCourseListings();
		List<CourseListing> courseListings = new ArrayList<>(listingDao.getAllCourseListings().values());
		JSONObject coursesJson = new JSONObject();
		JSONArray courses = new JSONArray();
		Map<String, List<SemesterType>> courseSemesters = new HashMap<>();
		Map<String, JSONObject> courseJsons = new HashMap<>();
		for (CourseListing listing : courseListings) {
			String courseCode = listing.getCourseCode();
			if (courseCode.length() > 8) {
				courseCode = courseCode.substring(0, 8);
			}
			List<SemesterType> courseSemester = courseSemesters.get(courseCode);
			if (courseSemester == null) {
				courseSemester = new ArrayList<>();
			}
			for (SemesterType semester : listing.getSemesterToCourse().keySet()){
				courseSemester.add(semester);
			}
			courseSemesters.put(courseCode, courseSemester);
			courseJsons.put(courseCode, listing.toJsonObject());
		}
		for (Entry<String, JSONObject> courseJsonEntry : courseJsons.entrySet()) {
			JSONArray semestersJson = new JSONArray();
			for(SemesterType semesterType : courseSemesters.get(courseJsonEntry.getKey())){
				switch(semesterType){
				case FALL:
					semestersJson.add("F");
					break;
				case WINTER:
					semestersJson.add("S");
					break;
				default:
					semestersJson.add("Y");
					break;
				}
			}
			JSONObject courseJson = courseJsonEntry.getValue();
			courseJson.put("semesters", semestersJson);
			courses.add(courseJson);
		}
		
		coursesJson.put("courses", courses);
		return coursesJson.toJSONString();
	}

}
