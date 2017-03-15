package generation;
import java.util.Comparator;

import businessobject.Timetable;
import enums.TimetableComparators;

public class TimetableCompare implements Comparator<Timetable>{
	private TimetableComparators currentComparator;
	
	public TimetableCompare (TimetableComparators currentComparator){
		this.currentComparator = currentComparator;
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

}
