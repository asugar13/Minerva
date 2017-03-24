package businessobject;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Timetable {

	List<CourseOffering> courseOfferings;
	private int numConflicts;
	
	public Timetable(List<CourseOffering> courseOfferings, int numConflicts){
		this.courseOfferings = courseOfferings;
		this.numConflicts = numConflicts;
	}

	public List<CourseOffering> getTimetableConfigurations(){
		return courseOfferings;
	}
	
	public List<CourseOffering> getCourseOfferings() {
		return courseOfferings;
	}
	
	public boolean hasConflicts() {
		return !(numConflicts == 0);
	}
	
	public int getNumConflicts() {
		return numConflicts;
	}
	
	public JSONObject toJsonObject(){
		JSONArray courseOfferingsJson = new JSONArray();
		for(CourseOffering offering : courseOfferings){
			courseOfferingsJson.add(offering.toJsonObject());
		}
		JSONObject timetableJson = new JSONObject();
		timetableJson.put("timetable", courseOfferingsJson);
		return new JSONObject();
	}

	public String toJsonString() {
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		courseOfferings.forEach((c) -> {
			sb.append(c.toString());
		}); 
		return sb.toString();
	}
}
