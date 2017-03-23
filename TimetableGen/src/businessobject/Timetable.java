package businessobject;

import java.util.List;

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
		return numConflicts == 0;
	}
	
	public int getNumConflicts() {
		return numConflicts;
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
