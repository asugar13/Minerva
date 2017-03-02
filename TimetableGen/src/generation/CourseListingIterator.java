package generation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import businessobject.ClassTime;
import businessobject.CourseListing;
import businessobject.CourseOffering;
import businessobject.TimeSlot;
import enums.ClassType;

public class CourseListingIterator{
	CourseListing listing;
	Map<ClassType, Integer> coursePositions;
	ArrayList<ClassType> classTypes;
	
	public CourseListingIterator(CourseListing listing){
		coursePositions = new HashMap<>();
		Set<ClassType> listingTypes = listing.getClassTimes().keySet();
		classTypes = new ArrayList<>(listingTypes.size());
		for(ClassType classType : listingTypes){
			coursePositions.put(classType, 0);
			classTypes.add(classType);
		}
	}
	
	public CourseOffering getCurrentOffering(){
		Map<ClassType, ClassTime> offeringTimeSlots = new HashMap<>();
		Map<ClassType, List<ClassTime>> classTimes = listing.getClassTimes();
		for (ClassType classType : classTypes){
			int typeIndex = coursePositions.get(classType);
			offeringTimeSlots.put(classType, classTimes.get(classType).get(typeIndex));
		}
		return new CourseOffering(this.listing, offeringTimeSlots);
	}

	//Returns false if nextOffering not possible
	public boolean nextOffering() {
		return nextOffering(0);
	}

	public boolean nextOffering(ClassType classType) {
		int classTypeIndex = classTypes.indexOf(classType);
		
		//No more possible offerings
		if(classTypeIndex < 0){
			return false;
		}
		
		return incrementClassType(classType, classTypeIndex);
	}
	
	//Doesn't increment and reset if out of bounds
	public boolean nextOfferingByType(ClassType classType){
		int classTypeIndex = classTypes.indexOf(classType);
		
		//No more possible offerings
		if(classTypeIndex < 0){
			return false;
		}
		
		List<ClassTime> typeTimeSlots = listing.getClassTimes().get(classType);
		int classTypePosition = coursePositions.get(classType) + 1;
		coursePositions.put(classType, classTypePosition);
		if(classTypePosition >= typeTimeSlots.size()){
			return false;
		} else{
			return true;
		}
	}

	public void resetIterators() {
		for (ClassType classType : coursePositions.keySet()) {
			coursePositions.put(classType, 0);
		}
	}

	public void resetIterator(ClassType classType) {
		coursePositions.put(classType, 0);
	}
	
	private boolean incrementClassType(ClassType classType, int classTypeIndex){
		List<ClassTime> typeTimeSlots = listing.getClassTimes().get(classType);
		int classTypePosition = coursePositions.get(classType) + 1;
		if(classTypePosition >= typeTimeSlots.size()){
			coursePositions.put(classType, 0);
			return nextOffering(classTypeIndex + 1);
		} else{
			coursePositions.put(classType, classTypePosition);
			return true;
		}
	}
	
	private boolean nextOffering(int classTypeIndex){
		ClassType classType = classTypes.get(classTypeIndex);
		
		//No more possible offerings
		if(classTypeIndex > classTypes.size()){
			return false;
		}
		
		return incrementClassType(classType, classTypeIndex);
	}
}
