package generation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import businessobject.ClassTime;
import businessobject.CourseListing;
import enums.ClassType;
import enums.SemesterType;

public class ClassIterator implements Iterator<ClassTime> {
	private List<ClassTime> classes;
	private int curIndex;
	private int nextIndex;
	
	public ClassIterator(CourseListing course, SemesterType semester, ClassType type) {
		classes = null;
		if (course.getCourseBySemester(semester) != null) {
			if (course.getCourseBySemester(semester).getClassTimes().containsKey(type)) {
				List<ClassTime> temp = course.getCourseBySemester(semester).getClassTimes().get(type);
				classes = new ArrayList<>();
				for (int i = 0; i < temp.size(); i++) {
					if (temp.get(i).getTimeSlots().size() != 0) {
						classes.add(temp.get(i));
					}
				}
				nextIndex = 0;
				curIndex = 0;
			}
		}
	}
	
	public boolean isNull() {
		if (classes == null) 
			return true;
		return false;
	}
	
	public ClassTime getCurClass() {
		return classes.get(curIndex);
	}
	
	public boolean hasNext() {
		if (isNull()) 
			return false;
		
		return nextIndex < classes.size();
	}
	
	public ClassTime next() {
		curIndex = nextIndex;
		nextIndex++;
		return classes.get(curIndex);
	}
}