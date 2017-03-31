package handlers;

import java.util.List;

import businessobject.Timetable;
import businessobject.SemesterConfiguration;

import generation.TimetableCompare;

import enums.TimetableComparators;

public class SortingHandler {
	
	private TimetableCompare comparer;
	
	public SortingHandler (){
		this.comparer = new TimetableCompare(TimetableComparators.MORE_DAYS_OFF);
	}
	
	public void TimetableSort (List<TimetableComparators> chosenRanking, SemesterConfiguration semConfig){
		
		List<Timetable> sem1 = semConfig.getPossibleTimetables1();
		List<Timetable> sem2 = semConfig.getPossibleTimetables2();
		
		for (TimetableComparators SortType : chosenRanking){
			comparer.setCurrentComparator(SortType);
			sem1.sort(comparer);
			sem2.sort(comparer);
		}
	}
}
