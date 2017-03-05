package businessobjectTest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import businessobject.ClassTime;
import businessobject.Course;
import businessobject.ClassTime;
import businessobject.CourseOffering;
import businessobject.TimeSlot;

import enums.CampusType;
import enums.ClassType;
import enums.Day;
import enums.SemesterType;

public class TestUtil {
	
	public static Course createCourse(){
		//first course in json
		String code = "ABP100Y1Y";
		String name = "Intro to Academic Studies";
		String description = "";
		List <Integer> breadths = new LinkedList<Integer> ();
		List <TimeSlot> timeSlots = new LinkedList<TimeSlot> ();
		List <ClassTime> cTimes = new LinkedList<ClassTime> ();
		timeSlots.add(new TimeSlot(Day.TUESDAY,36000,10800));
		cTimes.add(new ClassTime("L0101", timeSlots));
		
		int year = 2016;
		String prerequisite="";
		String exclusions = "";
		Map <ClassType, List<ClassTime>> classTimes= new HashMap<ClassType, List<ClassTime>>();
		classTimes.put(ClassType.LEC,cTimes);
		
		CampusType campus = CampusType.UTSG;
		SemesterType sem = SemesterType.YEAR;
		return new Course(name,code,description,prerequisite,exclusions,year,campus,sem,classTimes,breadths);
	}
	
	
}
