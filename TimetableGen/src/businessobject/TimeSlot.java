package businessobject;

import org.json.simple.JSONObject;

import enums.Day;

public class TimeSlot {
    private Day day;
    private Integer start;
    private Integer duration;
    
    public TimeSlot(Day day, int start, int duration) {
        this.day = day;
        this.start = start;
        this.duration = duration;
    }
    
    public boolean conflictsWith(TimeSlot timeSlot) {
        if (this.day == timeSlot.getDay()) {
        	Integer end = this.start + this.duration;
        	Integer otherStart = timeSlot.getStart();
        	Integer otherEnd = timeSlot.getStart() + timeSlot.getDuration();
        	return ((this.start >= otherStart && this.start < otherEnd) || (otherStart >= this.start && otherStart < end));
        } else {
        	return false;
        }
    }
    
    public int getIntCode() {
    	int result = 0;
    	
    	switch (day) {
		case MONDAY:
			result |= 1 << 30;
			break;
		case TUESDAY:
			result |= 1 << 29;
			break;
		case WEDNESDAY:
			result |= 1 << 28;
			break;
		case THURSDAY:
			result |= 1 << 27;
			break;
		case FRIDAY:
			result |= 1 << 26;
			break;
		default:
			break;
		}
    	
    	for (int i = 0; i < duration / 3600; i++) {
    		result |= (1 << ((start / 3600) + i));
    	}
    	
    	return result;
    }
    
    //TODO: Getters and Setters
    public Day getDay() {
    	return this.day;
    }
    
    public Integer getStart() {
    	return this.start;
    }
    
    public Integer getDuration() {
    	return this.duration;
    }
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("day: ");
    	switch (this.day) {
		case MONDAY:
			sb.append("Monday\t");
			break;
		case TUESDAY:
			sb.append("Tuesday\t");
			break;
		case WEDNESDAY:
			sb.append("Wednesday\t");
			break;
		case THURSDAY:
			sb.append("Thursday\t");
			break;
		case FRIDAY:
			sb.append("Friday\t");
			break;
		default:
			break;
		}

    	sb.append("start :" + this.start + "\t");
        sb.append("duration: " + this.duration + "\n");
        return sb.toString();
    }

	public JSONObject toJsonObject() {
		JSONObject timeslot = new JSONObject();
		timeslot.put("day", day.name());
		timeslot.put("start", start / 3600);
		timeslot.put("end", (start + duration) / 3600);
		return timeslot;
	}
 
}
