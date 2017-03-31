package http;

import static spark.Spark.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import dao.CourseListingDao;
import dao.CourseLoader;
import handlers.CourseInformationRouteHandler;
import handlers.TestRoute;

public class RestServer {
	
	private static String FILE_PATH = "res/courses.json";
	private static CourseListingDao listingDao;
	
	public static void main(String[] args) {
		createDependencies();
		port(8800);
		JSONParser parser = new JSONParser();
		
		JSONObject obj = new JSONObject();
		obj.put("get", "info");
		get("/course-information",  new CourseInformationRouteHandler(listingDao));
		get("/test",  new TestRoute());
		

		post("/generate-timetable", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			
			JSONObject body = (JSONObject) parser.parse(req.body());
			System.out.println(body);

			// TODO: Generate and return timetable based on body
			return obj.toJSONString();
		});

		System.out.println("Server started");
	}
	
	private static void createDependencies(){
		listingDao = new CourseLoader(FILE_PATH);
	}

}
