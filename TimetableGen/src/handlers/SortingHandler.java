package handlers;

import businessobject.Timetable;
import businessobject.SemesterConfiguration;

import java.util.List;

import generation.TimetableCompare;

import enums.TimetableComparators;

public class SortingHandler {
	private TimetableCompare comparer;
	
	public SortingHandler (){
		this.comparer = new TimetableCompare(TimetableComparators.MORE_DAYS_OFF);
	}
	
	public List<Timetable> TimetableSort (List<TimetableComparators> chosenRanking, SemesterConfiguration semConfig){
		
	}
}
