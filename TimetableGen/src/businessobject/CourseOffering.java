package businessobject;

import java.util.Map;

import enums.ClassType;

public class CourseOffering {
	private CourseListing listing;
	private Map<ClassType, ClassTime> classTime;
	
	public CourseOffering(CourseListing listing, Map<ClassType, ClassTime> classTime){
		this.listing = listing;
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
	
	public CourseListing getListing(){
		return listing;
	}
	
	public Map<ClassType, ClassTime> getClassTime(){
		return classTime;
	}
}
