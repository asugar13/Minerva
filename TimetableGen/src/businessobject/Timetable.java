package businessobject;

import java.util.List;

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

	public String toJsonString() {
		return null;
	}
}
