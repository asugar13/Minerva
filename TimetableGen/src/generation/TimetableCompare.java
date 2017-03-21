package generation;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import businessobject.TimeSlot;
import businessobject.CourseOffering;
import businessobject.ClassTime;
import businessobject.Timetable;
import enums.ClassType;
import enums.TimetableComparators;
import enums.Day;


public class TimetableCompare implements Comparator<Timetable>{
	private TimetableComparators currentComparator;
	private List <CourseOffering> t1Config;
	private List <CourseOffering> t2Config;
	
	public TimetableCompare (TimetableComparators currentComparator){
		this.currentComparator = currentComparator;
		this.t1Config = new LinkedList<>();
		this.t2Config = new LinkedList<>();
	}
	
	
	public TimetableComparators getCurrentComparator() {
		return currentComparator;
	}


	public void setCurrentComparator(TimetableComparators currentComparator) {
		this.currentComparator = currentComparator;
	}


	@Override
	public int compare(Timetable t1, Timetable t2) {
		int compareResult = 0;
		this.t1Config = t1.getTimetableConfigurations();
		this.t2Config = t2.getTimetableConfigurations();
		
		switch (this.currentComparator){
			case MORE_DAYS_OFF:
				return numberOfDaysComparisons();
			case LESS_DAYS_OFF:
				break;
			case MORNINGS_OFF:
				return timeOffComparisons (TimetableComparators.MORNINGS_OFF);
			case EVENINGS_OFF:
				return timeOffComparisons (TimetableComparators.EVENINGS_OFF);
			default:
				return 0;
		}
		return compareResult;
	}
	
	//Helper functions
	
	public int numberOfDaysComparisons(){
			int numDaysOfft1Sem1 = daysOff("F", this.t1Config);
			int numDaysOfft1Sem2 = daysOff("S", this.t1Config);
			int numDaysOfft2Sem1 = daysOff("F", this.t2Config);
			int numDaysOfft2Sem2 = daysOff("S", this.t2Config);
			
		
		return (numDaysOfft1Sem1 + numDaysOfft1Sem2) - (numDaysOfft2Sem1 + numDaysOfft2Sem2);
	}
	
	public int timeOffComparisons (TimetableComparators compare){
		switch (compare){
		case MORNINGS_OFF:
			break;
		case EVENINGS_OFF:
			break;
		default:
			return 0;
		}
		
		return 0;
		
	}
	
	//helpers for helpers
	
	//counts the number of days off in one week of one semester of one timetable
	public int daysOff (String ForS, List <CourseOffering> Config){
		Set <String> daysNotOff = new HashSet <>();
		
		for (CourseOffering co : Config){
			Map<ClassType, ClassTime> times = co.getClassTime();
			Iterator <ClassTime> ct = times.values().iterator();
			while (ct.hasNext()){
				ClassTime classTimeSlot = ct.next();
				String semTime = classTimeSlot.getclassCode().substring(classTimeSlot.getclassCode().length()-1);
				if (semTime.equalsIgnoreCase("Y") || semTime.equalsIgnoreCase(ForS)){
					for (TimeSlot t: classTimeSlot.getTimeSlots()){
						Day d = t.getDay();
						if (!daysNotOff.contains(Day2String(d)))
							daysNotOff.add(Day2String(d));
					}
				}
			}
		}
		
		return 5 - daysNotOff.size();
	}
	
	//changes enums to strings to store in set
	public String Day2String (Day d){
		switch (d){
		case MONDAY:
			return "Monday";
		case TUESDAY:
			return "Tuesday";
		case WEDNESDAY:
			return "Wednesday";
		case THURSDAY:
			return "Thursday";
		case FRIDAY:
			return "Friday";
		default:
			return "Monday";
		}
	}

	
}
