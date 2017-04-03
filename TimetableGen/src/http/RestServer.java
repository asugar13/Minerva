package http;

import static spark.Spark.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import dao.CourseListingDao;
import dao.CourseLoader;
import handlers.CourseInformationRouteHandler;
import handlers.GenerateTimetablesRouteHandler;
import handlers.SortingHandler;
import handlers.TestRoute;
import handlers.TimeTableGeneratorHandler;

public class RestServer {
	
	private static String FILE_PATH = "res/courses.json";
	private static CourseListingDao listingDao;
	private static SortingHandler sorter;
	
	public static void main(String[] args) {
		createDependencies();
		port(8800);
		

		//Routes
		get("/course-information",  new CourseInformationRouteHandler(listingDao));
		get("/test",  new TestRoute());
		post("/generate-timetable", new GenerateTimetablesRouteHandler(listingDao, sorter));
		System.out.println("Server started");
		

		//post("/generate-timetable", new TimeTableGeneratorHandler(null));
	}
	
	private static void createDependencies(){
		listingDao = new CourseLoader(FILE_PATH);
		sorter = new SortingHandler();
	}

}
