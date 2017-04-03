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
		get("/generate-timetable", new GenerateTimetablesRouteHandler(listingDao, sorter));
		System.out.println("Server started");
		
		/*
		post("/generate-timetable", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			
			JSONObject body = (JSONObject) parser.parse(req.body());
			System.out.println(body);

			// TODO: Generate and return timetable based on body
			return obj.toJSONString();
		});
		*/
	}
	
	private static void createDependencies(){
		listingDao = new CourseLoader(FILE_PATH);
		sorter = new SortingHandler();
	}

}
