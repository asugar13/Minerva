package businessobject;

import enums.Day;

public class TimeSlot {
    Day day;
    Integer start;
    Integer duration;
    
    public TimeSlot(Day day, int start, int duration) {
        this.day = day;
        this.start = start;
        this.duration = duration;
    }
    
    public boolean conflictsWith(TimeSlot timeSlot){
        if (this.day == timeSlot.getDay()) {
        	Integer end = start + duration;
        	Integer otherStart = timeSlot.getStart();
        	Integer otherEnd = timeSlot.getStart() + timeSlot.getDuration();
        	return ((start > otherStart && start < otherEnd) || (otherStart > start && otherStart < end));
        } else {
        	return false;
        }
    }
    
    //TODO: Getters and Setters
    public Day getDay(){
    	return day;
    }
    
    public Integer getStart(){
    	return start;
    }
    
    public Integer getDuration(){
    	return duration;
    }
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("day: ");
    	switch (day) {
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

    	sb.append("start :" + start + "\t");
        sb.append("duration: " + duration + "\n");
        return sb.toString();
    }
}
