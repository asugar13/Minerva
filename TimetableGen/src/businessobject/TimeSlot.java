package businessobject;

public interface TimeSlot {
	public boolean conflictsWith(TimeSlot timeslots);
}
