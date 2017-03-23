package http;

import static spark.Spark.*;

import handlers.CourseInformationRouteHandler;

public class RestServer {

	public static void main(String[] args) {
		port(8800);
		get("/hello", (req, res) -> "Hello World");
		get("/hello", (req, res) -> {
			return "";
		});
		// path("/generate-timetables");
	}

	private static void createDependencies() {

	}

}
