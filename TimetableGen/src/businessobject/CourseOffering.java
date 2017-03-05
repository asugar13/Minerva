package businessobject;

import java.util.Map;

import enums.ClassType;

public class CourseOffering {
	private Course course;
	private Map<ClassType, ClassTime> classTimes;
	
	public CourseOffering(Course course, Map<ClassType, ClassTime> classTimes){
		this.course = course;
		this.classTimes = classTimes;
	}

	public boolean conflictsWith(CourseOffering otherCourse){
		for (ClassTime classTime : classTimes.values()) {
			for (ClassTime otherClassTime : otherCourse.getClassTime().values()) {
				if( classTime.conflictsWith(otherClassTime)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public Course getListing(){
		return course;
	}
	
	public Map<ClassType, ClassTime> getClassTime(){
		return classTimes;
	}
}
