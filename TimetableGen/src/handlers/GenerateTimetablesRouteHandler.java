package handlers;

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

	@Override
	public Object handle(Request request, Response response) throws Exception {
		TimetableConfigurationGenerator configGen = new SemesterConfigurationGenerator(listingDao);
		TimetableGenerator generator = new TimetableGenerator();
		return null;
	}

}
