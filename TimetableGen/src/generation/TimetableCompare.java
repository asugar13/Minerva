package generation;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import businessobject.Timetable;
import businessobject.TimetableConfiguration;
import enums.TimetableComparators;

public class TimetableCompare implements Comparator<Timetable>{
	private TimetableComparators currentComparator;
	private List <TimetableConfiguration> t1Config;
	private List <TimetableConfiguration> t2Config;
	
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
		switch (this.currentComparator){
			case MORE_DAYS_OFF:
				break;
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
	
	public int numberOfDaysComparisons(Timetable t1, Timetable t2){
		this.t1Config =  t1.getTimetableConfigurations();
		this.t2Config =  t2.getTimetableConfigurations();
		
		return 0;
	}

}
