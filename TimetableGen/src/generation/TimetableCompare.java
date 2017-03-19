package generation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import businessobject.TimeSlot;
import businessobject.Course;
import businessobject.CourseOffering;
import businessobject.ClassTime;
import businessobject.SemesterConfiguration;
import businessobject.Timetable;
import businessobject.TimetableConfiguration;
import enums.TimetableComparators;

public class TimetableCompare implements Comparator<Timetable>{
	private TimetableComparators currentComparator;
	private List <TimetableConfiguration> t1Config;
	private List <TimetableConfiguration> t2Config;
	
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
		switch (this.currentComparator){
			case MORE_DAYS_OFF:
				return numberOfDaysComparisons(t1,t2);
			case LESS_DAYS_OFF:
				break;
			case MORNINGS_OFF:
				return timeOffComparisons (t1,t2,TimetableComparators.MORNINGS_OFF);
			case EVENINGS_OFF:
				return timeOffComparisons (t1,t2,TimetableComparators.EVENINGS_OFF);
			default:
				return 0;
		}
		return compareResult;
	}
	
	//Helper functions
	
	public int numberOfDaysComparisons(Timetable t1, Timetable t2){
			this.t1Config = t1.getTimetableConfigurations();
			this.t2Config = t2.getTimetableConfigurations();
			
			
		
		return 0;
	}
	
	public int timeOffComparisons (Timetable t1, Timetable t2, TimetableComparators compare){
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
