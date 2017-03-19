package generation;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import businessobject.CourseOffering;
import businessobject.SemesterConfiguration;
import businessobject.Timetable;
import businessobject.TimetableConfiguration;
import enums.TimetableComparators;

public class TimetableCompare implements Comparator<SemesterConfiguration>{
	private TimetableComparators currentComparator;
	private List <Set<CourseOffering>> t1Config;
	private List <Set<CourseOffering>> t2Config;
	
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
	public int compare(SemesterConfiguration t1, SemesterConfiguration t2) {
		int compareResult = 0;
		switch (this.currentComparator){
			case MORE_DAYS_OFF:
				return numberOfDaysComparisons(t1,t2);
			case LESS_DAYS_OFF:
				break;
			case MORNINGS_OFF:
				break;
			case EVENINGS_OFF:
				break;
			default:
				return 0;
		}
		return compareResult;
	}
	
	//Helper functions
	
	public int numberOfDaysComparisons(SemesterConfiguration t1, SemesterConfiguration t2){
		this.t1Config =  t1.getSemesterConfigurations();
		this.t2Config =  t2.getSemesterConfigurations();
		int t1Count = 0;
		int t2Count = 0;
		
		
		for (Set<CourseOffering> s : this.t1Config){
			for (CourseOffering c : s){
				c.getClassTime().values();
			}
		}
		
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
