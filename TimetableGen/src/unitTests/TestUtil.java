package unitTests;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import businessobject.ClassTime;
import businessobject.Course;
import businessobject.ClassTime;
import businessobject.CourseOffering;
import businessobject.TimeSlot;
import businessobject.Timetable;
import dao.CourseLoader;
import enums.CampusType;
import enums.ClassType;
import enums.Day;
import enums.SemesterType;

public class TestUtil {
	
	public static TimeSlot createTimeSlot(Day day, int start, int duration){
		return new TimeSlot(day, start, duration);
	}
	
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
	
	public static Timetable createTimetable(Set <String> TimeSlotInfo){
		Set <TimeSlot> timeslots;
		for (String TimeSlotInstance : TimeSlotInfo){
			String [] temp = TimeSlotInstance.split(":"); // day:start:end
			timeslots.add(createTimeSlot(String2Day(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));
		}
		
	}
	
	public static Day String2Day (String day){
		switch (day){
		case "Monday":
			return Day.MONDAY;
		case "Tuesday":
			return Day.TUESDAY;
		case "Wednesday":
			return Day.WEDNESDAY;
		case "Thursday":
			return Day.THURSDAY;
		case "Friday":
			return Day.FRIDAY;
		default:
			return Day.MONDAY;	
		}
	}
	

}
