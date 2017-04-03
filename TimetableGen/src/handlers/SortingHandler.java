package handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import businessobject.Timetable;
import businessobject.ClassTime;
import businessobject.CourseOffering;
import businessobject.SemesterConfiguration;
import businessobject.TimeSlot;
import generation.TimetableCompare;

import enums.TimetableComparators;
import enums.ClassType;
import enums.Day;

public class SortingHandler {

	private TimetableCompare comparer;

	public SortingHandler() {
		this.comparer = new TimetableCompare(TimetableComparators.MORE_DAYS_OFF);
	}

	public SemesterConfiguration TimetableSort(List<Day> DesiredDaysOff, List<TimetableComparators> chosenRanking,
			SemesterConfiguration semConfig) {

		List<Timetable> sem1 = semConfig.getPossibleTimetables1();
		List<Timetable> sem2 = semConfig.getPossibleTimetables2();
		
		if (DesiredDaysOff.size() > 0){
			sem1 = dayFilter(DesiredDaysOff,sem1);
			sem2 = dayFilter(DesiredDaysOff,sem2);
		}
		
		for (TimetableComparators SortType : chosenRanking) {
			comparer.setCurrentComparator(SortType);
			sem1.sort(Collections.reverseOrder(comparer));
			sem2.sort(Collections.reverseOrder(comparer));
		}
		
		
		SemesterConfiguration newConfig = new SemesterConfiguration(semConfig.getSemester1(),semConfig.getSemester2());
		newConfig.addPossibleTimetables1(sem1);
		newConfig.addPossibleTimetables2(sem2);
		return newConfig;
	}

	public List<Timetable> dayFilter(List<Day> DesiredDaysOff, List<Timetable> timetableList) {

		List<Timetable> removeList = new ArrayList<>(timetableList);
		
		for (Timetable timetable : timetableList) {
			for (CourseOffering courseOff : timetable.getCourseOfferings()) {
				Map<ClassType, ClassTime> times = courseOff.getClassTime();
				for (ClassTime classTimeSlot : times.values()) {
					for (TimeSlot timeS : classTimeSlot.getTimeSlots()) {
						if (DesiredDaysOff.contains(timeS.getDay())) {
							removeList.remove(timetable);
						}
					}
				}
			}

		}
		return removeList;
	}
}
