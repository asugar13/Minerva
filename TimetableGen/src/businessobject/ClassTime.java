package businessobject;

import java.util.List;

public class ClassTime {

	private String classCode;
	private List<TimeSlot> timeSlots;

	public ClassTime(String classCode, List<TimeSlot> timeSlots){
		this.classCode = classCode;
		this.timeSlots = timeSlots;
	}
	
	public boolean conflictsWith(ClassTime otherClass) {
		for (TimeSlot timeSlot : this.timeSlots) {
			
			for (TimeSlot otherTimeSlot : otherClass.getTimeSlots()) {
				if (timeSlot.conflictsWith(otherTimeSlot)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public List<TimeSlot> getTimeSlots(){
		return timeSlots;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class code: " + classCode + "\n");
		for (int i = 0; i < timeSlots.size(); i++) {
			sb.append("\t" + timeSlots.get(i).toString());
		}
		return sb.toString();
	}
}
