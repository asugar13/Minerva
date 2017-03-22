package unitTests;

import org.junit.Test;

import businessobject.Timetable;
import unitTests.TestUtil;
import generation.TimetableCompare;
import enums.TimetableComparators;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

public class TimetableCompareTest {

	@Test
	public void NumOfDaysCalculationTest(){
		Set <String> testSet = new HashSet<>();
		TimetableCompare comparer = TestUtil.createTimetableCompare(TimetableComparators.MORE_DAYS_OFF);
		
		//4 days off in first semester, 5 days off in second
		String t1c1 = "Monday:43200:3600:CSC301F";
		testSet.add(t1c1);
		Timetable t1 = TestUtil.createTimetable(testSet);
		
		testSet.clear();
		
		//3 days off in each semester
		String t2c1 = "Monday:43200:3600:CSC301Y";
		String t2c2 = "Tuesday:43200:3600:CSC301Y";
		testSet.add(t2c1);
		testSet.add(t2c2);
		Timetable t2 = TestUtil.createTimetable(testSet);
		
		int result = comparer.compare(t1, t2);
		assertEquals(3, result);
	}
	
	@Test
	public void T1HasMoreDaysOffTest(){
		Set <String> testSet = new HashSet<>();
		TimetableCompare comparer = TestUtil.createTimetableCompare(TimetableComparators.MORE_DAYS_OFF);
		
		String t1c1 = "Monday:43200:3600:CSC301Y";
		testSet.add(t1c1);
		Timetable t1 = TestUtil.createTimetable(testSet);
		
		testSet.clear();
		
		String t2c1 = "Monday:43200:3600:CSC301Y";
		String t2c2 = "Tuesday:43200:3600:CSC301Y";
		testSet.add(t2c1);
		testSet.add(t2c2);
		Timetable t2 = TestUtil.createTimetable(testSet);
		
		int result = comparer.compare(t1, t2);
		assertTrue(result > 0);
	}
	
	@Test
	public void SameNumberOfDaysOffTest(){
		Set <String> testSet = new HashSet<>();
		TimetableCompare comparer = TestUtil.createTimetableCompare(TimetableComparators.MORE_DAYS_OFF);
		
		String t1c1 = "Monday:43200:3600:CSC301Y";
		testSet.add(t1c1);
		Timetable t1 = TestUtil.createTimetable(testSet);
		
		testSet.clear();
		
		String t2c1 = "Monday:43200:3600:CSC301Y";
		testSet.add(t2c1);
		Timetable t2 = TestUtil.createTimetable(testSet);
		
		int result = comparer.compare(t1, t2);
		assertTrue(result == 0);
	}
	
	@Test
	public void LessDaysOffTest(){
		Set <String> testSet = new HashSet<>();
		TimetableCompare comparer = TestUtil.createTimetableCompare(TimetableComparators.LESS_DAYS_OFF);
		
		String t1c1 = "Monday:43200:3600:CSC301Y";
		String t1c2 = "Tuesday:43200:3600:CSC301Y";
		testSet.add(t1c1);
		testSet.add(t1c2);
		Timetable t1 = TestUtil.createTimetable(testSet);
		
		testSet.clear();
		
		String t2c1 = "Monday:43200:3600:CSC301Y";
		testSet.add(t2c1);
		Timetable t2 = TestUtil.createTimetable(testSet);
		
		int result = comparer.compare(t1, t2);
		assertTrue(result > 0);
	}
}
