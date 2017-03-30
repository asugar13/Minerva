package unitTests;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import businessobject.ClassTime;
import businessobject.Course;
import businessobject.CourseOffering;
import businessobject.TimeSlot;
import businessobject.Timetable;
import generation.TimetableCompare;

import enums.CampusType;
import enums.ClassType;
import enums.Day;
import enums.SemesterType;
import enums.TimetableComparators;
import generation.TimetableCompare;

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
	
	public static Timetable createTimetable(Set <String> CourseOfferingInfo){
		
		List <CourseOffering> TimetableCourseOfferings = new LinkedList <>();
		Map <String,List <TimeSlot>> temp2 = new HashMap<>();
		
		
		for (String TimeSlotInstance : CourseOfferingInfo){
			String [] temp = TimeSlotInstance.split(":"); // day:start:duration:code
			String code = temp[3];
			
			TimeSlot CurrentTimeSlot = createTimeSlot(String2Day(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
			if (temp2.containsKey(code)){
				List <TimeSlot> SwapExistingList = temp2.get(code);
				SwapExistingList.add(CurrentTimeSlot);
				temp2.put(code, SwapExistingList);
			}else{
				List <TimeSlot> temp3 = new LinkedList<>();
				temp3.add(CurrentTimeSlot);
				temp2.put(code, temp3);
			}
		}
		
		for (Map.Entry<String, List <TimeSlot>> entry  : temp2.entrySet()){
			Map <ClassType, ClassTime> Type2Time = new HashMap<>();
			
			Type2Time.put(ClassType.LEC,new ClassTime(entry.getKey(),entry.getValue()));
			CourseOffering co = new CourseOffering (null,Type2Time);
			TimetableCourseOfferings.add(co);
		}
		return new Timetable(TimetableCourseOfferings,0);
	}
	
	public static TimetableCompare createTimetableCompare(TimetableComparators c){
		return new TimetableCompare(c);
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
