package generation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import businessobject.ClassTime;
import businessobject.Course;
import businessobject.CourseOffering;
import enums.ClassType;

public class CourseIterator {
	Course course;
	Map<ClassType, Integer> coursePositions;
	ArrayList<ClassType> classTypes;

	public CourseIterator(Course course) {
		coursePositions = new HashMap<>();
		Set<ClassType> courseTypes = course.getClassTimes().keySet();
		classTypes = new ArrayList<>(courseTypes.size());
		for (ClassType classType : courseTypes) {
			coursePositions.put(classType, 0);
			classTypes.add(classType);
		}
	}

	public CourseOffering getCurrentOffering() {
		Map<ClassType, ClassTime> offeringTimeSlots = new HashMap<>();
		Map<ClassType, List<ClassTime>> classTimes = course.getClassTimes();
		for (ClassType classType : classTypes) {
			int typeIndex = coursePositions.get(classType);
			offeringTimeSlots.put(classType, classTimes.get(classType).get(typeIndex));
		}
		return new CourseOffering(this.course, offeringTimeSlots);
	}

	// Returns false if nextOffering not possible
	public boolean nextOffering() {
		return nextOffering(0);
	}

	/**
	 * Increments the iterator at the ClassType classType, if exceeds bounds of
	 * that classType, will reset that iterator then increment next classType
	 * 
	 * @param classType
	 *            ClassType to increment
	 * @return True if increment successful False if increment results in no
	 *         result
	 */
	public boolean nextOffering(ClassType classType) {
		int classTypeIndex = classTypes.indexOf(classType);

		// No more possible offerings
		if (classTypeIndex < 0) {
			return false;
		}

		return incrementClassType(classType, classTypeIndex);
	}

	/**
	 * Increments the iterator at the ClassType classType. Will not reset and
	 * increment if out of bounds
	 * 
	 * @param classType
	 *            ClassType to increment
	 * @return True if increment successful False if increment results in out of
	 *         bounds
	 */
	public boolean nextOfferingByType(ClassType classType) {
		int classTypeIndex = classTypes.indexOf(classType);

		// No more possible offerings
		if (classTypeIndex < 0) {
			return false;
		}

		List<ClassTime> typeTimeSlots = course.getClassTimes().get(classType);
		int classTypePosition = coursePositions.get(classType) + 1;
		coursePositions.put(classType, classTypePosition);
		if (classTypePosition >= typeTimeSlots.size()) {
			return false;
		} else {
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

	private boolean incrementClassType(ClassType classType, int classTypeIndex) {
		List<ClassTime> typeTimeSlots = course.getClassTimes().get(classType);
		int classTypePosition = coursePositions.get(classType) + 1;
		if (classTypePosition >= typeTimeSlots.size()) {
			coursePositions.put(classType, 0);
			return nextOffering(classTypeIndex + 1);
		} else {
			coursePositions.put(classType, classTypePosition);
			return true;
		}
	}

	private boolean nextOffering(int classTypeIndex) {
		ClassType classType = classTypes.get(classTypeIndex);

		// No more possible offerings
		if (classTypeIndex > classTypes.size()) {
			return false;
		}

		return incrementClassType(classType, classTypeIndex);
	}
}
