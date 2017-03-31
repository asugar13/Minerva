package businessobject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import enums.ClassType;
import enums.Day;
import enums.TimetableComparators;

public class Timetable {

	List<CourseOffering> courseOfferings;
	private int numConflicts;
	private int numOffDays;
	private int numOfDaysWithMorningOff;
	private int numOfDaysWithEveningOff;
	private int numBreaks;

	public Timetable(List<CourseOffering> courseOfferings, int numConflicts) {
		this.courseOfferings = courseOfferings;
		this.numConflicts = numConflicts;

		// Compare variables
		this.numBreaks = NumBreaks(courseOfferings, "F", 3600) + NumBreaks(courseOfferings, "S", 3600);
		this.numOffDays = NumOfdaysOff(courseOfferings, "F") + NumOfdaysOff(courseOfferings, "S");

		this.numOfDaysWithMorningOff = NumOfDaysWithTimeOff(TimetableComparators.MORE_MORNINGS_OFF, courseOfferings,
				"F", 43200, 64800)
				+ NumOfDaysWithTimeOff(TimetableComparators.MORE_MORNINGS_OFF, courseOfferings, "S", 43200, 64800);

		this.numOfDaysWithEveningOff = NumOfDaysWithTimeOff(TimetableComparators.MORE_EVENINGS_OFF, courseOfferings,
				"F", 43200, 64800)
				+ NumOfDaysWithTimeOff(TimetableComparators.MORE_EVENINGS_OFF, courseOfferings, "S", 43200, 64800);
	}

	public List<CourseOffering> getCourseOfferings() {
		return courseOfferings;
	}

	public int getNumOffDays() {
		return numOffDays;
	}

	public int getNumOfDaysWithMorningOff() {
		return numOfDaysWithMorningOff;
	}

	public int getNumOfDaysWithEveningOff() {
		return numOfDaysWithEveningOff;
	}

	public int getNumBreaks() {
		return numBreaks;
	}

	public boolean hasConflicts() {
		return !(numConflicts == 0);
	}

	public int getNumConflicts() {
		return numConflicts;
	}

	public JSONObject toJsonObject() {
		JSONArray courseOfferingsJson = new JSONArray();
		for (CourseOffering offering : courseOfferings) {
			courseOfferingsJson.add(offering.toJsonObject());
		}
		JSONObject timetableJson = new JSONObject();
		timetableJson.put("courses", courseOfferingsJson);
		return new JSONObject();
	}

	public String toJsonString() {
		return null;
	}

	// counts the number of days that don't have the specified time off
	public int NumOfDaysWithTimeOff(TimetableComparators compare, List<CourseOffering> Config, String FirstOrSecond,
			int noonInSeconds, int eveningInSeconds) {

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
	public int NumBreaks(List<CourseOffering> Config, String FirstOrSecond, int hour) {
		Set<TimeSlot> TimeSlots = new HashSet<>();
		int total = 0;
		Set<String> days = new HashSet<>();
		days.add("Monday");
		days.add("Tuesday");
		days.add("Wednesday");
		days.add("Thursday");
		days.add("Friday");

		// add to TimeSlots set the time slots for one day of the week
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
			// more than 1 class in a day
			if (TimeSlots.size() > 1) {
				List<TimeSlot> orderedTime = TimeSort(TimeSlots);
				Iterator<TimeSlot> OrderTimeI = orderedTime.iterator();
				TimeSlot current = OrderTimeI.next();
				TimeSlot next = OrderTimeI.next();
				int startOfDay = current.getStart();
				// take sorted timeslots for single day and add to the total the
				// number of breaks in between classes
				while (next != null) {
					total += (next.getStart() - (current.getStart() + current.getDuration())) / hour;
					current = next;
					if (OrderTimeI.hasNext())
						next = OrderTimeI.next();
					else
						next = null;
				}

				// amount of hours leading to your first class and preceeding
				// your last class.
				total += 13 - ((current.getStart() + current.getDuration()) - startOfDay) / hour;
			} else if (TimeSlots.size() == 1) {
				// only one class
				for (TimeSlot t : TimeSlots)
					total += (13 - t.getDuration() / hour);
			} else {
				// full day to yourself (8am to 9pm)
				total += 13;
			}
		}

		return total;
	}

	// selection sorting of time slot in a single day by start time
	public List<TimeSlot> TimeSort(Set<TimeSlot> TimeSlots) {
		List<TimeSlot> orderedTime = new LinkedList<>();
		int min = 100000;
		TimeSlot currentMax = null;
		while (!TimeSlots.isEmpty()) {
			min = 100000;
			for (TimeSlot t : TimeSlots) {
				if (t.getStart() < min) {
					min = t.getStart();
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		courseOfferings.forEach((c) -> {
			sb.append(c.toString());
		});
		return sb.toString();
	}
}
