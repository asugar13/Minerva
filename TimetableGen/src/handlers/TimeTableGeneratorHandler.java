package handlers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import spark.Request;
import spark.Response;
import spark.Route;

import com.sun.net.httpserver.HttpServer;

import enums.SemesterType;
import businessobject.CourseListing;
import businessobject.CourseSelection;
import businessobject.SemesterConfiguration;
import businessobject.TimetableConfiguration;
import generation.TimetableConfigurationGenerator;

public class TimeTableGeneratorHandler implements Route {
	
	private TimetableConfigurationGenerator generator;
	private JSONParser parser = new JSONParser();

	
	public TimeTableGeneratorHandler(TimetableConfigurationGenerator generator1){
		this.generator = generator1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object handle(Request req, Response res) throws Exception {
		String request_body = req.body();
		res.header("Access-Control-Allow-Origin", "*");
		
		System.out.println("u");
		System.out.println(request_body);
		System.out.println("ah");
		
		JSONObject object = new JSONObject();
		object = (JSONObject) parser.parse(request_body);
		
		
		return object;
	}
	
	
}