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
	
	public ClassTime getSpecificClasstime(ClassType classtype){
		return classTimes.get(classtype);
	}
	
	public Map<ClassType, ClassTime> getClassTime(){
		return classTimes;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(course.getCourseCode() + ": " + course.getName() + "\n");
		if (classTimes.containsKey(ClassType.LEC)) {
			sb.append("Lectures: \n" + classTimes.get(ClassType.LEC).toString() + "\n");
		}
		if (classTimes.containsKey(ClassType.PRA)) {
			sb.append("Praticals: \n" + classTimes.get(ClassType.PRA).toString() + "\n");
		}
		if (classTimes.containsKey(ClassType.TUT)) {
			sb.append("Tutorials: \n" + classTimes.get(ClassType.TUT).toString() + "\n");
		}
		
		return sb.toString();
	}
}
