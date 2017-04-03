package handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import businessobject.CourseSelection;
import businessobject.SemesterConfiguration;
import dao.CourseListingDao;
import enums.SemesterType;
import enums.TimetableComparators;
import generation.SemesterConfigurationGenerator;
import generation.TimetableConfigurationGenerator;
import generation.TimetableGenerator;
import spark.Request;
import spark.Response;
import spark.Route;

public class GenerateTimetablesRouteHandler implements Route {
	CourseListingDao listingDao;
	SortingHandler sorter;
	Map<Integer, Set<SemesterConfiguration>> cache = new HashMap<>();

	public GenerateTimetablesRouteHandler(CourseListingDao listingDao, SortingHandler sorter) {
		this.listingDao = listingDao;
		this.sorter = sorter;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object handle(Request request, Response response) throws Exception {
		String reqJsonString = request.body();
		JSONParser parser = new JSONParser();
		JSONObject reqJson = (JSONObject) parser.parse(reqJsonString);
		Set<CourseSelection> courses = generateCourseSelections(reqJson);
		TimetableConfigurationGenerator configGen = new SemesterConfigurationGenerator(listingDao);

		// Get configurations from cache if null then generate
		// TODO ensure hash works
		Set<SemesterConfiguration> configurations = cache.get(courses.hashCode()); 
		Set<SemesterConfiguration> possibleTimetableConfigurations = null;
		if (configurations == null) {
			configurations = configGen.generateConfigurations(courses);
			TimetableGenerator generator = new TimetableGenerator();
			possibleTimetableConfigurations = new HashSet<>();
			for (SemesterConfiguration configuration : configurations) {
				possibleTimetableConfigurations.add(generator.generate(configuration));
			}
			
			//Put generated timetables into the cache
			cache.put(courses.hashCode(), configurations);
		} else{
			
			//Copy cached set
			possibleTimetableConfigurations = new HashSet<>(configurations);
		}
		

		// Sort if sorting is specified
		List<TimetableComparators> comparators = createTimetableComparators(reqJson);
		Set<SemesterConfiguration> sortedConfigurations = null;
		if (comparators.size() > 0) {
			sortedConfigurations = new HashSet<>();
			for (SemesterConfiguration configuration : possibleTimetableConfigurations) {
				sortedConfigurations.add(sorter.TimetableSort(new ArrayList<>(), comparators, configuration));
			}
		} else {
			sortedConfigurations = possibleTimetableConfigurations;
		}

		// Convert to JSON
		JSONObject resJson = new JSONObject();
		JSONArray configurationsJson = new JSONArray();
		int i = 0;
		for (SemesterConfiguration possibleConfig : sortedConfigurations) {
			JSONObject possibleConfigJson = possibleConfig.toJsonObject();
			possibleConfigJson.put("configurationNumber", i);
			configurationsJson.add(possibleConfigJson);
			i++;
		}
		resJson.put("configurations", configurationsJson);
		return resJson.toJSONString();
	}

	private List<TimetableComparators> createTimetableComparators(JSONObject reqJson) {
		JSONArray filters = (JSONArray) reqJson.get("filters");
		List<TimetableComparators> comparators = new ArrayList<>();
		for (int i = 0; i < filters.size(); i++) {
			comparators.add(TimetableComparators.valueOf(filters.get(i).toString()));
		}
		return comparators;
	}

	private Set<CourseSelection> generateCourseSelections(JSONObject reqJson) {
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
		JSONObject o = (JSONObject) p
				.parse("{\"courses\": [" + "{\"courseCode\": \"CSC301H1\", \"semesters\": [\"F\"]},"
						+ "{\"courseCode\": \"CSC302H1\", \"semesters\": [\"S\"]},"
						+ "{\"courseCode\": \"CSC373H1\", \"semesters\": [\"F\", \"S\"]},"
						+ "{\"courseCode\": \"PSY100Y1\", \"semesters\": [\"Y\"]}" + "]}");
		GenerateTimetablesRouteHandler g = new GenerateTimetablesRouteHandler(null, null);
		g.generateCourseSelections(o);
	}
}
