package businessobject;

import java.util.Map;

import enums.ClassType;

public class CourseOffering {
	private CourseListing listing;
	private Map<ClassType, TimeSlot> timeSlots;
	
	public CourseOffering(CourseListing listing, Map<ClassType, TimeSlot> timeSlots){
		this.listing = listing;
		this.timeSlots = timeSlots;
	}

	public boolean conflictsWith(CourseOffering course){
		for(TimeSlot thisTimeSlot : timeSlots.values()){
			for(TimeSlot otherTimeSlot : course.getTimeSlots().values()){
				if(thisTimeSlot.conflictsWith(otherTimeSlot)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public CourseListing getListing(){
		return listing;
	}
	
	public Map<ClassType, TimeSlot> getTimeSlots(){
		return timeSlots;
	}
}
