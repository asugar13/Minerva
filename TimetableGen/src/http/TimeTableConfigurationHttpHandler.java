	package http;
	
	import java.io.BufferedReader;
	
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.OutputStream;
	import java.util.HashSet;
	import java.util.Set;
	
	
	import org.json.simple.JSONObject;
	import org.json.simple.parser.JSONParser;
	
	import com.sun.net.httpserver.Headers;
	import com.sun.net.httpserver.HttpExchange;
	import com.sun.net.httpserver.HttpHandler;
	
	import businessobject.Course;
	import businessobject.CourseSelection;
	import businessobject.TimetableConfiguration;
	import enums.SemesterType;
	import generation.TimetableConfigurationGenerator;
	import handlers.TimeTableGeneratorHandler;
	
	
	
	
	@SuppressWarnings("restriction")
	public class TimeTableConfigurationHttpHandler implements HttpHandler{
		
		private TimeTableGeneratorHandler handler;
		
		
		public TimeTableConfigurationHttpHandler(TimeTableGeneratorHandler handler1){
			this.handler=handler1;
		}
	
		@Override
	    @SuppressWarnings({ "unchecked", "restriction" })
		public void handle(HttpExchange t) throws IOException {
	        InputStream is = t.getRequestBody();
	        Set<CourseSelection> set = new HashSet<CourseSelection>();
	        JSONParser parser = new JSONParser();
			
	        try {
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				while (br.ready()) {
					JSONObject o = (JSONObject) parser.parse(br.readLine());
					
					String fullCourseCode = (String) o.get("courseCode");
					String courseCode = fullCourseCode.substring(0, 6);
					Set<SemesterType> semesterRestrictions = (Set<SemesterType>) o.get("semesters");
					CourseSelection course = new CourseSelection(courseCode, semesterRestrictions);
					set.add(course);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	        Set<TimetableConfiguration> configurations;
	        configurations  = handler.generateTimetables(set);
	        
			//convert configs to json?
			
	        /*String response = "This is the response";
	        Headers header =t.getResponseHeaders();
			header.add("Access-Control-Allow-Origin", "*");
			
			
	        t.sendResponseHeaders(200, response.length());
	        OutputStream os = t.getResponseBody();
	        os.write(response.getBytes());
	        os.close();*/
	    }
	    
	
	}