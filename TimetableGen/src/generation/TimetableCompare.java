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

public class TimetableCompare implements Comparator<Timetable> {
	private TimetableComparators currentComparator;
	private List<CourseOffering> t1Config;
	private List<CourseOffering> t2Config;

	private final int noonInSeconds = 43200; // seconds from midnight to 12pm
	private final int eveningInSeconds = 64800; // seconds from midnight to 6pm
	private final int hour = 3600; // hour in seconds

	public TimetableCompare(TimetableComparators currentComparator) {
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

		t1Config = t1.getTimetableConfigurations();
		t2Config = t2.getTimetableConfigurations();

		switch (this.currentComparator) {
		case MORE_DAYS_OFF:
			return numberOfDaysComparisons();
		case LESS_DAYS_OFF:
			return -numberOfDaysComparisons();
		case MORE_MORNINGS_OFF:
			return timeOffComparisons(currentComparator);
		case MORE_EVENINGS_OFF:
			return timeOffComparisons(currentComparator);
		case MORE_BREAKS:
			return breaksComparisons();
		default:
			return 0;
		}
	}

	// Helper
	// functions--------------------------------------------------------//

	public int numberOfDaysComparisons() {
		int numDaysOfft1Sem1 = NumOfdaysOff(t1Config, "F");
		int numDaysOfft1Sem2 = NumOfdaysOff(t1Config, "S");
		int numDaysOfft2Sem1 = NumOfdaysOff(t2Config, "F");
		int numDaysOfft2Sem2 = NumOfdaysOff(t2Config, "S");

		return (numDaysOfft1Sem1 + numDaysOfft1Sem2) - (numDaysOfft2Sem1 + numDaysOfft2Sem2);
	}

	public int timeOffComparisons(TimetableComparators compare) {
		int timeOfft1Sem1 = NumOfDaysWithTimeOff(compare, t1Config, "F");
		int timeOfft1Sem2 = NumOfDaysWithTimeOff(compare, t1Config, "S");
		int timeOfft2Sem1 = NumOfDaysWithTimeOff(compare, t2Config, "F");
		int timeOfft2Sem2 = NumOfDaysWithTimeOff(compare, t2Config, "S");

		return (timeOfft1Sem1 + timeOfft1Sem2) - (timeOfft2Sem1 + timeOfft2Sem2);

	}

	public int breaksComparisons() {
		int breakst1Sem1 = NumBreaks(t1Config, "F");
		int breakst1Sem2 = NumBreaks(t1Config, "S");
		int breakst2Sem1 = NumBreaks(t2Config, "F");
		int breakst2Sem2 = NumBreaks(t2Config, "S");

		return (breakst1Sem1 + breakst1Sem2) - (breakst2Sem1 + breakst2Sem2);
	}

	// helpers for helpers----------------------------------------------------//

	// counts the number of days that don't have the specified time off
	public int NumOfDaysWithTimeOff(TimetableComparators compare, List<CourseOffering> Config, String FirstOrSecond) {

		Set<String> timeNotOff = new HashSet<>();

		for (CourseOffering courseOff : Config) {
			Map<ClassType, ClassTime> times = courseOff.getClassTime();
			for (ClassTime classTimeSlot : times.values()) {
				String semTime = classTimeSlot.getclassCode();
				if (semTime.endsWith("Y") || semTime.endsWith(FirstOrSecond)) {
					for (TimeSlot timeS : classTimeSlot.getTimeSlots()) {
						int start = timeS.getStart();
						int end = start + timeS.getDuration();
						if ((compare.equals(TimetableComparators.MORE_MORNINGS_OFF) && start < noonInSeconds)
								|| compare.equals(TimetableComparators.MORE_EVENINGS_OFF)
										&& (start >= eveningInSeconds || end > eveningInSeconds)) {
							Day d = timeS.getDay();
							timeNotOff.add(Day2String(d));
						}
					}
				}
			}
		}

		return 5 - timeNotOff.size();

	}

	// counts the number of days off in one week of one semester of one
	// timetable
	public int NumOfdaysOff(List<CourseOffering> Config, String FirstOrSecond) {
		Set<String> daysNotOff = new HashSet<>();

		for (CourseOffering courseOff : Config) {
			Map<ClassType, ClassTime> times = courseOff.getClassTime();
			for (ClassTime classTimeSlot : times.values()) {
				String semTime = classTimeSlot.getclassCode();
				if (semTime.endsWith("Y") || semTime.endsWith(FirstOrSecond)) {
					for (TimeSlot timeS : classTimeSlot.getTimeSlots()) {
						Day d = timeS.getDay();
						daysNotOff.add(Day2String(d));
					}
				}
			}
		}

		return 5 - daysNotOff.size();
	}

	// counts the number of 1 hour breaks between classes
	public int NumBreaks(List<CourseOffering> Config, String FirstOrSecond) {
		Set<TimeSlot> TimeSlots = new HashSet<>();
		int total = 0;
		Set<String> days = new HashSet<>();
		days.add("Monday");
		days.add("Tuesday");
		days.add("Wednesday");
		days.add("Thursday");
		days.add("Friday");

		for (String day : days) {
			TimeSlots.clear();
			for (CourseOffering courseOff : Config) {
				Map<ClassType, ClassTime> times = courseOff.getClassTime();
				for (ClassTime classTimeSlot : times.values()) {
					String semTime = classTimeSlot.getclassCode();
					if ((semTime.endsWith("Y") || semTime.endsWith(FirstOrSecond))) {
						for (TimeSlot ts : classTimeSlot.getTimeSlots()) {
							if (Day2String(ts.getDay()).equals(day)) {
								TimeSlots.add(ts);
							}
						}
					}
				}
			}
			List<TimeSlot> orderedTime = TimeSort(TimeSlots);
			Iterator<TimeSlot> OrderTimeI = orderedTime.iterator();
			TimeSlot current = OrderTimeI.next();
			TimeSlot next = OrderTimeI.next();
			// take sorted timeslots for single day and add to the total the
			// number of breaks in between classes
			while (next != null) {
				total += (next.getStart() - (current.getStart() + current.getDuration())) / hour;
				current = next;
				next = OrderTimeI.next();
			}
		}

		return total;
	}

	// selection sorting of time slot in a single day by start time
	public List<TimeSlot> TimeSort(Set<TimeSlot> TimeSlots) {
		List<TimeSlot> orderedTime = new LinkedList<>();
		int max = 0;
		TimeSlot currentMax = null;
		while (!TimeSlots.isEmpty()) {
			max = 0;
			for (TimeSlot t : TimeSlots) {
				if (t.getStart() > max) {
					max = t.getStart();
					currentMax = t;
				}
			}

			orderedTime.add(currentMax);
			TimeSlots.remove(currentMax);
		}
		return orderedTime;
	}

	// changes enums to strings to store in set
	public String Day2String(Day d) {
		switch (d) {
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
