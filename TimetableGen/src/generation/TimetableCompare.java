package generation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import businessobject.TimeSlot;
import businessobject.Course;
import businessobject.CourseOffering;
import businessobject.ClassTime;
import businessobject.SemesterConfiguration;
import businessobject.Timetable;
import businessobject.TimetableConfiguration;
import enums.ClassType;
import enums.TimetableComparators;
import enums.Day;


public class TimetableCompare implements Comparator<Timetable>{
	private TimetableComparators currentComparator;
	private List <CourseOffering> t1Config;
	private List <CourseOffering> t2Config;
	
	public TimetableCompare (TimetableComparators currentComparator){
		this.currentComparator = currentComparator;
		this.t1Config = new ArrayList<>();
		this.t2Config = new ArrayList<>();
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
			int numDaysOfft1Sem1 = 0;
			int numDaysOfft1Sem2 = 0;
			Set <String> daysNotOff = new HashSet <>();
			
			for (CourseOffering co : this.t1Config){
				Map<ClassType, ClassTime> times = co.getClassTime();
				Iterator <ClassTime> ct = times.values().iterator();
				while (ct.hasNext()){
					for (TimeSlot t: ct.next().getTimeSlots()){
						Day d = t.getDay();
						daysNotOff.add(d);
					}
				}
			}
			
			
			
			int numDaysOfft2Sem1 = 0;
			int numDaysOfft2Sem2 = 0;
			
		
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

}
