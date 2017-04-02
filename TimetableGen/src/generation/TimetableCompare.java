package generation;

import java.util.Comparator;
import businessobject.Timetable;
import enums.TimetableComparators;


public class TimetableCompare implements Comparator<Timetable> {
	private TimetableComparators currentComparator;

	public TimetableCompare(TimetableComparators currentComparator) {
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
		switch (this.currentComparator){
			case MORE_DAYS_OFF:
				return numberOfDaysComparisons(t1,t2);
			case LESS_DAYS_OFF:
				return -numberOfDaysComparisons(t1,t2);
			case MORE_MORNINGS_OFF:
				return timeOffComparisons (currentComparator,t1,t2);
			case MORE_EVENINGS_OFF:
				return timeOffComparisons (currentComparator,t1,t2);
			case MORE_BREAKS:
				return breaksComparisons(t1,t2);
			case LESS_BREAKS:
				return -breaksComparisons(t1,t2);
			default:
				return 0;
		}
	}

	// Helper
	// functions--------------------------------------------------------//

	public int numberOfDaysComparisons(Timetable t1,Timetable t2) {
		int numDaysOfft1 = t1.getNumOffDays();
		int numDaysOfft2 = t2.getNumOffDays();

		return numDaysOfft1 - numDaysOfft2;
	}

	public int timeOffComparisons(TimetableComparators compare, Timetable t1,Timetable t2) {
		int timeOfft1, timeOfft2;
		if(compare.equals(TimetableComparators.MORE_MORNINGS_OFF)){
			timeOfft1 = t1.getNumOfDaysWithMorningOff();
			timeOfft2 = t2.getNumOfDaysWithMorningOff();
		} else{
			timeOfft1 = t1.getNumOfDaysWithEveningOff();
			timeOfft2 = t2.getNumOfDaysWithEveningOff();
		}

		return timeOfft1 - timeOfft2;
	}

	public int breaksComparisons(Timetable t1,Timetable t2) {
		//Note: may behave a little odd if a timetable has conflicting courses 
		int breakst1 = t1.getNumBreaks();
		int breakst2=  t2.getNumBreaks();

		return breakst1 - breakst2;
	}

}
