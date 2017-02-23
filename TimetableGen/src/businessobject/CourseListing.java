package businessobject;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CourseListing{
	String courseCode;
	String name;
	String description;
	int year;
	List<Integer> breadths;
	String prerequisite;
	String exclusions;
	Set<LecTimeSlots> lecSlots;
	Set<TutTimeSlots> tutSlots;
	CampusType campus;
	SemesterType semester;
	
	public CourseListing(String name, String courseCode, String descrption, String prerequisite, String exclusions, int year, CampusType campus, SemesterType semester){
		
		/**Instance variables we know upon instantiation*/
		this.name = name;
		this.courseCode = courseCode;
		this.description = descrption;
		this.prerequisite = prerequisite;
		this.exclusions = exclusions;
		this.year = year;
		this.campus = campus;
		this.semester = semester;
		
		/**Data Structures:
		 * NOTE: I don't know if these will be populated at the point of instantiation. If not, use adders below to populate.
		 * You may also change it from hashSet/linkedList to something else if it's more convenient for you.*/
		
		this.lecSlots = new HashSet<LecTimeSlots>();
		this.tutSlots = new HashSet<TutTimeSlots>();
		this.breadths = new LinkedList<Integer>();
	}
	
	//--------------Getters, Setters, Adders--------------//
	/**Getters and adders for set and linked list*/
	
	public void addLec (LecTimeSlots lec){
		this.lecSlots.add(lec);
	}
	
	public Set<LecTimeSlots> getLecSlots (){
		return this.lecSlots;
	}
	
	public void addTut (TutTimeSlots tut){
		this.tutSlots.add(tut); 
	}
	
	public Set<TutTimeSlots> getTutSlots (){
		return this.tutSlots;
	}
	
	public void addBreadth (Integer breadth){
		this.breadths.add(breadth);
	}
	
	public List<Integer> getBreadths(){
		return this.breadths;
	}
	
	/**Getters*/

	public String getCourseCode() {
		return courseCode;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getYear() {
		return year;
	}

	public String getPrerequisite() {
		return prerequisite;
	}

	public String getExclusions() {
		return exclusions;
	}

	public CampusType getCampus() {
		return campus;
	}

	public SemesterType getSemester() {
		return semester;
	}
	//----------------------------------------------------//
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseCode == null) ? 0 : courseCode.hashCode());
		result = prime * result + ((semester == null) ? 0 : semester.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseListing other = (CourseListing) obj;
		if (courseCode == null) {
			if (other.courseCode != null)
				return false;
		} else if (!courseCode.equals(other.courseCode))
			return false;
		if (semester != other.semester)
			return false;
		return true;
	}
	
}