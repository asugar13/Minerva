package businessobject;

import java.util.List;

import org.json.simple.JSONObject;

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
	
	public String getclassCode (){
		return this.classCode;
	}
	
	public List<TimeSlot> getTimeSlots(){
		return timeSlots;
	}
	
	public int[] getIntCodes() {		
		int[] result = new int[5];
		for (int i = 0; i < 5; i++) {
			result[i] = 0;
		}
		
		for (int i = 0; i < timeSlots.size(); i++) {
			int code = timeSlots.get(i).getIntCode();
			switch (code >> 26) {
			case 0b10000:
				result[0] |= (code << 6);
				break;
			case 0b1000:
				result[1] |= (code << 6);
				break;
			case 0b100:
				result[2] |= (code << 6);
				break;
			case 0b10:
				result[3] |= (code << 6);
				break;
			case 0b1:
				result[4] |= (code << 6);
				break;
			default:
				break;
			}
		}
		
		return result;
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

	public JSONObject toJsonObject() {
		// TODO Auto-generated method stub
		return null;
	}
}
