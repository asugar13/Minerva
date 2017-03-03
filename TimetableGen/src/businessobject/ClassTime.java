package businessobject;

import java.util.List;

public class ClassTime {

	private List<TimeSlot> timeSlots;

	public ClassTime(String classCode, List<TimeSlot> timeSlots){
		this.timeSlots = timeSlots;
	}
	
	public boolean conflictsWith(ClassTime otherClass){
		for(TimeSlot timeSlot : timeSlots){
			for(TimeSlot otherTimeSlot : otherClass.getTimeSlots()){
				if(timeSlot.conflictsWith(otherTimeSlot)){
					return true;
				}
			}
		}
		return false;
	}
	
	public List<TimeSlot> getTimeSlots(){
		return timeSlots;
	}
}
