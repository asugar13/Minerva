package businessobjectTest;

import java.util.LinkedList;
import java.util.List;

import businessobject.ClassTime;
import businessobject.Course;
import businessobject.ClassTime;
import businessobject.CourseOffering;
import businessobject.TimeSlot;

public class TestUtil {
	private static Course createCourse(){
		//random course
		String code = "ABP100Y1Y";
		String name = "Intro to Academic Studies";
		String description = "";
		List <Integer> breadths = new LinkedList ();
		int year = 2016;
		
		return new Course();
	}
}
