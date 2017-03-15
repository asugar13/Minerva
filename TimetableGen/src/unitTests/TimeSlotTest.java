package unitTests;

import static org.junit.Assert.assertEquals;
import businessobject.TimeSlot;
import enums.Day;

import org.junit.Test;

public class TimeSlotTest {
	
	@Test
	public void timeSlotDayGetterTest(){
		TimeSlot t = TestUtil.createTimeSlot(Day.FRIDAY, 0, 0);
		assertEquals(Day.FRIDAY, t.getDay());
	}
	
	@Test
	public void timeSlotStartGetterTest(){
		TimeSlot t = TestUtil.createTimeSlot(Day.FRIDAY, 10800, 0);
		assertEquals(new Integer (10800), t.getStart());
	}
	
	@Test
	public void timeSlotDurationGetterTest(){
		TimeSlot t = TestUtil.createTimeSlot(Day.FRIDAY, 0, 10800);
		assertEquals(new Integer (10800), t.getDuration());
	}
	
	@Test
	public void timeSlotConflictExactSameTimeTest(){
		TimeSlot t1 = TestUtil.createTimeSlot(Day.FRIDAY, 10800, 10000);
		TimeSlot t2 = TestUtil.createTimeSlot(Day.FRIDAY, 10800, 10000);
		assertEquals(true,t1.conflictsWith(t2));
	}
	
	@Test 
	public void timeSlotConflictStartBeforeTest(){
		TimeSlot t1 = TestUtil.createTimeSlot(Day.FRIDAY, 10800, 10000);
		TimeSlot t2 = TestUtil.createTimeSlot(Day.FRIDAY, 10000, 1000);
		assertEquals(true,t1.conflictsWith(t2));
	}
	
	@Test 
	public void timeSlotConflictStartDuringTest(){
		TimeSlot t1 = TestUtil.createTimeSlot(Day.FRIDAY, 10800, 10000);
		TimeSlot t2 = TestUtil.createTimeSlot(Day.FRIDAY, 11000, 10000);
		assertEquals(true,t1.conflictsWith(t2));
	}
	
	@Test 
	public void timeSlotConflictNestedSlotTest(){
		TimeSlot t1 = TestUtil.createTimeSlot(Day.FRIDAY, 10800, 10000);
		TimeSlot t2 = TestUtil.createTimeSlot(Day.FRIDAY, 11000, 1000);
		assertEquals(true,t1.conflictsWith(t2));
	}
	
	@Test 
	public void timeSlotNoConflictBeforeTest(){
		TimeSlot t1 = TestUtil.createTimeSlot(Day.FRIDAY, 10800, 10000);
		TimeSlot t2 = TestUtil.createTimeSlot(Day.FRIDAY, 10000, 800);
		assertEquals(false,t1.conflictsWith(t2));
	}
	
	@Test 
	public void timeSlotNoConflictAfterTest(){
		TimeSlot t1 = TestUtil.createTimeSlot(Day.FRIDAY, 10800, 10000);
		TimeSlot t2 = TestUtil.createTimeSlot(Day.FRIDAY, 20800, 800);
		assertEquals(false,t1.conflictsWith(t2));
	}
	
	@Test 
	public void timeSlotNoConflictDifferentDayTest(){
		TimeSlot t1 = TestUtil.createTimeSlot(Day.FRIDAY, 10800, 10000);
		TimeSlot t2 = TestUtil.createTimeSlot(Day.MONDAY, 10800, 10000);
		assertEquals(false,t1.conflictsWith(t2));
	}
	
	@Test 
	public void timeSlotSymmetryTest(){
		TimeSlot t1 = TestUtil.createTimeSlot(Day.FRIDAY, 10800, 10000);
		TimeSlot t2 = TestUtil.createTimeSlot(Day.FRIDAY, 10344, 12535);
		assertEquals(t2.conflictsWith(t1),t1.conflictsWith(t2));
	}
}
