package unitTests;

import unitTests.TestUtil;
import businessobject.Timetable;
import enums.Day;
import enums.TimetableComparators;
import handlers.SortingHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import businessobject.SemesterConfiguration;


public class SortingTest {

	public static void main(String[] args) {
		SortingHandler sorter = new SortingHandler ();
		SemesterConfiguration testConfig = new SemesterConfiguration (null,null);
		List<Timetable> sem1 = new ArrayList<>();
		List<Timetable> sem2 = new ArrayList<>();
		List<Day> DesiredDaysOff = new ArrayList<>();
		List<TimetableComparators> chosenRanking = new ArrayList<>();
		
		//Play around with the criteria if you want
		chosenRanking.add(TimetableComparators.MORE_MORNINGS_OFF);
		DesiredDaysOff.add(Day.FRIDAY);
		//One sort test
		Set <String> testSet = new HashSet<>();
		String t1c1 = "Monday:43200:3600:CSC301F";
		String t1c2 = "Monday:46800:3600:CSC301F";
		testSet.add(t1c1);
		testSet.add(t1c2);
		Timetable t1 = TestUtil.createTimetable(testSet);
		testSet.clear();
		
		String t2c1 = "Monday:40000:3600:CSC301F";
		String t2c2 = "Tuesday:46800:3600:CSC301F";
		String t2c3 = "Wednesday:46800:3600:CSC301F";
		testSet.add(t2c1);
		testSet.add(t2c2);
		testSet.add(t2c3);
		Timetable t2 = TestUtil.createTimetable(testSet);
		testSet.clear();
		
		String t5c1 = "Monday:40000:3600:CSC301F";
		String t5c2 = "Tuesday:40000:3600:CSC301F";
		String t5c3 = "Friday:40000:3600:CSC301F";
		testSet.add(t5c1);
		testSet.add(t5c2);
		testSet.add(t5c3);
		Timetable t5 = TestUtil.createTimetable(testSet);
		testSet.clear();
		
		//sem 2:
		String t3c1 = "Monday:43000:3600:CSC301F";
		testSet.add(t3c1);
		Timetable t3 = TestUtil.createTimetable(testSet);
		testSet.clear();
		
		String t4c1 = "Monday:43200:3600:CSC301F";
		testSet.add(t4c1);
		Timetable t4 = TestUtil.createTimetable(testSet);
		testSet.clear();
		
		sem1.add(t2);
		sem1.add(t1);
		sem1.add(t5);
		sem2.add(t3);
		sem2.add(t4);
		testConfig.addPossibleTimetables1(sem1);
		testConfig.addPossibleTimetables2(sem2);

		
		SemesterConfiguration newConfig = sorter.TimetableSort(DesiredDaysOff, chosenRanking, testConfig);
		sem1 = newConfig.getPossibleTimetables1();
		sem2 = newConfig.getPossibleTimetables2();
		
		System.out.println("First Sem Sort:");
		for (Timetable t: sem1)
			System.out.println(t.getNumOfDaysWithMorningOff());
		
		System.out.println("Second Sem Sort:");
		for (Timetable t: sem2)
			System.out.println(t.getNumOfDaysWithMorningOff());
		
	}

}
