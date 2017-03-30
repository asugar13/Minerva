package handlers;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import businessobject.CourseSelection;
import businessobject.SemesterConfiguration;
import dao.CourseListingDao;
import enums.SemesterType;
import generation.SemesterConfigurationGenerator;
import generation.TimetableConfigurationGenerator;
import generation.TimetableGenerator;
import spark.Request;
import spark.Response;
import spark.Route;

public class GenerateTimetablesRouteHandler implements Route {
	CourseListingDao listingDao;
	public GenerateTimetablesRouteHandler(CourseListingDao listingDao){
		this.listingDao = listingDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object handle(Request request, Response response) throws Exception {
		String reqJsonString = request.body();
		JSONParser parser = new JSONParser();
		JSONObject reqJson = (JSONObject) parser.parse(reqJsonString);
		Set<CourseSelection> courses = generateCourseSelections(reqJson);
		TimetableConfigurationGenerator configGen = new SemesterConfigurationGenerator(listingDao);
		Set<SemesterConfiguration> configurations = configGen.generateConfigurations(courses);
		TimetableGenerator generator = new TimetableGenerator();
		Set<SemesterConfiguration> possibleTimetableConfigurations = new HashSet<>();
		for(SemesterConfiguration configuration: configurations){
			possibleTimetableConfigurations.add(generator.generate(configuration));
		}
		JSONObject resJson = new JSONObject();
		JSONArray configurationsJson = new JSONArray();
		int i = 0;
		for(SemesterConfiguration possibleConfig : possibleTimetableConfigurations){
			JSONObject possibleConfigJson = possibleConfig.toJsonObject();
			possibleConfigJson.put("configurationNumber", i);
			configurationsJson.add(possibleConfigJson);
			i ++;
		}
		resJson.put("configurations", configurationsJson);
		return resJson.toJSONString();
	}
	
	public Set<CourseSelection> generateCourseSelections(JSONObject reqJson){
		Set<CourseSelection> result = new HashSet<>();
		
		JSONArray courses = (JSONArray) reqJson.get("courses");
		for (int i1 = 0; i1 < courses.size(); i1++) {
			String courseCode = (String) ((JSONObject) courses.get(i1)).get("courseCode");
			JSONArray semesters = (JSONArray) ((JSONObject) courses.get(i1)).get("semesters");
			
			Set<SemesterType> semesterRestrictions = new HashSet<>();
			for (int i2 = 0; i2 < semesters.size(); i2++) {
				switch ((String) semesters.get(i2)) {
				case "F":
					semesterRestrictions.add(SemesterType.FALL);
					break;
				case "S":
					semesterRestrictions.add(SemesterType.WINTER);
					break;
				case "Y":
					semesterRestrictions.add(SemesterType.FALL);
					semesterRestrictions.add(SemesterType.WINTER);
					break;
				default:
					break;
				}
			}
			
			result.add(new CourseSelection(courseCode, semesterRestrictions));
			// for testing purposes:
			System.out.println((new CourseSelection(courseCode, semesterRestrictions).toString()));
		}
		return result;
	}

	public static void main(String[] args) throws ParseException {
		JSONParser p = new JSONParser();
		JSONObject o = (JSONObject) p.parse("{\"courses\": ["
				+ "{\"courseCode\": \"CSC301H1\", \"semesters\": [\"F\"]},"
				+ "{\"courseCode\": \"CSC302H1\", \"semesters\": [\"S\"]},"
				+ "{\"courseCode\": \"CSC373H1\", \"semesters\": [\"F\", \"S\"]},"
				+ "{\"courseCode\": \"PSY100Y1\", \"semesters\": [\"Y\"]}"
				+ "]}");
		GenerateTimetablesRouteHandler g = new GenerateTimetablesRouteHandler(null);
		g.generateCourseSelections(o);
	}
}
