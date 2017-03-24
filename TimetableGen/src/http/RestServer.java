package http;

import static spark.Spark.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RestServer {
	
	public static void main(String[] args) {
		port(8800);
		JSONParser parser = new JSONParser();
		
		JSONObject obj = new JSONObject();
		obj.put("get", "info");

		get("/course-information", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			
			// TODO: Return actual course information
			return obj.toJSONString();
		});

		post("/generate-timetable", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			
			JSONObject body = (JSONObject) parser.parse(req.body());
			System.out.println(body);

			// TODO: Generate and return timetable based on body
			return obj.toJSONString();
		});

		System.out.println("Server started");
	}

}
