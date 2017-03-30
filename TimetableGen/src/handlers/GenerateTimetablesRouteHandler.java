package handlers;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import businessobject.CourseSelection;
import businessobject.SemesterConfiguration;
import businessobject.TimetableConfiguration;
import dao.CourseListingDao;
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
	
	private Set<CourseSelection> generateCourseSelections(JSONObject reqJson){
		//TODO
		return null;
	}

}
