package generation;

import java.util.Comparator;
import java.util.HashSet;
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
	
	private final int noonInSeconds = 43200;  //seconds from midnight to 12pm
	private final int eveningInSeconds = 64800; //seconds from midnight to 6pm
	
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

		this.t1Config = t1.getTimetableConfigurations();
		this.t2Config = t2.getTimetableConfigurations();
		
		switch (this.currentComparator){
			case MORE_DAYS_OFF:
				return numberOfDaysComparisons();
			case LESS_DAYS_OFF:
				return -numberOfDaysComparisons();
			case MORE_MORNINGS_OFF:
				return timeOffComparisons (TimetableComparators.MORE_MORNINGS_OFF);
			case MORE_EVENINGS_OFF:
				return timeOffComparisons (TimetableComparators.MORE_EVENINGS_OFF);
			default:
				return 0;
		}
	}
	
	//Helper functions
	
	public int numberOfDaysComparisons(){
			int numDaysOfft1Sem1 = NumOfdaysOff("F", this.t1Config);
			int numDaysOfft1Sem2 = NumOfdaysOff("S", this.t1Config);
			int numDaysOfft2Sem1 = NumOfdaysOff("F", this.t2Config);
			int numDaysOfft2Sem2 = NumOfdaysOff("S", this.t2Config);
			
		
		return (numDaysOfft1Sem1 + numDaysOfft1Sem2) - (numDaysOfft2Sem1 + numDaysOfft2Sem2);
	}
	
	public int timeOffComparisons(TimetableComparators compare){
		int timeOfft1Sem1 = NumOfDaysWithTimeOff(compare, t1Config, "F");
		int timeOfft1Sem2 = NumOfDaysWithTimeOff(compare, t1Config, "S");
		int timeOfft2Sem1 = NumOfDaysWithTimeOff(compare, t1Config, "F");
		int timeOfft2Sem2 = NumOfDaysWithTimeOff(compare, t1Config, "S");
		
		return (timeOfft1Sem1 + timeOfft1Sem2) - (timeOfft2Sem1 + timeOfft2Sem2);
		
	}
	
	public int NumOfDaysWithTimeOff (TimetableComparators compare, List <CourseOffering> Config, String ForS){ 
			
			Set <String> timeNotOff = new HashSet<>();
			
			for (CourseOffering courseOff : Config){
				Map<ClassType, ClassTime> times = courseOff.getClassTime();
				for (ClassTime classTimeSlot :times.values()){
					String semTime = classTimeSlot.getclassCode();
					if (semTime.endsWith("Y") || semTime.endsWith(ForS)){
					for (TimeSlot t: classTimeSlot.getTimeSlots()){
						int start = t.getStart();
						int end = start + t.getDuration();
						if ((compare.equals(TimetableComparators.MORE_MORNINGS_OFF) && start < noonInSeconds) ||
								compare.equals(TimetableComparators.MORE_EVENINGS_OFF) && (start >= eveningInSeconds || end > eveningInSeconds)){
									Day d = t.getDay();
									timeNotOff.add(Day2String(d));
							}
						}
					}
				  }
			}
			
		return 5 - timeNotOff.size();
		
	}
	
	//helpers for helpers
	
	//counts the number of days off in one week of one semester of one timetable
	public int NumOfdaysOff (String ForS, List <CourseOffering> Config){
		Set <String> daysNotOff = new HashSet <>();
		
		for (CourseOffering courseOff : Config){
			Map<ClassType, ClassTime> times = courseOff.getClassTime();
			for (ClassTime classTimeSlot :times.values()){
				String semTime = classTimeSlot.getclassCode();
				if (semTime.endsWith("Y") || semTime.endsWith(ForS)){
					for (TimeSlot t: classTimeSlot.getTimeSlots()){
						Day d = t.getDay();
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
