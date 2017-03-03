package businessobject;

import java.util.Map;

import enums.ClassType;

public class CourseOffering {
	private Course course;
	private Map<ClassType, ClassTime> classTime;
	
	public CourseOffering(Course course, Map<ClassType, ClassTime> classTime){
		this.course = course;
		this.classTime = classTime;
	}

	public boolean conflictsWith(CourseOffering otherCourse){
		for(ClassTime classTime : classTime.values()){
			for(ClassTime otherClassTime : otherCourse.getClassTime().values()){
				if(classTime.conflictsWith(otherClassTime)){
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
		return classTime;
	}
}
