package http;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import businessobject.CourseListing;
import dao.CourseListingDao;
import dao.CourseLoader;
import handlers.CourseInformationRouteHandler;
import spark.Route;

public class RestServer {

	public static void main(String[] args) throws Exception {
		port(8801);
		get("/hello", (req, res) -> "Hello World");
		String FILE_PATH = "res/courses.json";
		CourseListingDao listingDao = new CourseLoader(FILE_PATH);
		get("/course-information", new CourseInformationRouteHandler(listingDao));
		System.out.println("Server Ready");
		// path("/generate-timetables");
	}

	private static void createDependencies() {

	}

}
