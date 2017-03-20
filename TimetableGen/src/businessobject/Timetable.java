package businessobject;

import java.util.List;



public class Timetable {
	List<CourseOffering> courseOfferings;
	
	public Timetable(List<CourseOffering> courseOfferings){
		this.courseOfferings = courseOfferings;
	}

	public List<CourseOffering> getTimetableConfigurations(){
		return courseOfferings;
	}
	
	public String toJsonString(){
		return null;
	}
}
