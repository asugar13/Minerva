package generation;
import java.util.Comparator;

import businessobject.Timetable;
import enums.TimetableComparators;

public class TimetableSort implements Comparator<Timetable>{
	private TimetableComparators currentComparator;
	
	public TimetableSort (TimetableComparators currentComparator){
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
		
		return 0;
	}

}
