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
        if(this.day == timeSlot.getDay()){
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
}
