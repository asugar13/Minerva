package businessobject;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Timetable {

	List<CourseOffering> courseOfferings;
	private boolean hasConflicts;
	
	public Timetable(List<CourseOffering> courseOfferings, boolean hasConflicts){
		this.courseOfferings = courseOfferings;
		this.hasConflicts = hasConflicts;
	}

	public List<CourseOffering> getTimetableConfigurations(){
		return courseOfferings;
	}
	
	public List<CourseOffering> getCourseOfferings() {
		return courseOfferings;
	}
	
	public boolean hasConflicts() {
		return hasConflicts;
	}
	
	public JSONObject toJsonObject(){
		JSONArray courseOfferingsJson = new JSONArray();
		for(CourseOffering offering : courseOfferings){
			courseOfferingsJson.add(offering.toJsonObject());
		}
		JSONObject timetableJson = new JSONObject();
		timetableJson.put("courses", courseOfferingsJson);
		return new JSONObject();
	}

	public String toJsonString() {
		return null;
	}
}
