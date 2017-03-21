package businessobject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SemesterConfiguration implements TimetableConfiguration{
	
	/** Maximum courses for a semester is 6 **/
	private Set<CourseListing> semester1; //set of courses in the 1st semester
	private Set<CourseListing> semester2; //set of courses in the 2nd semester
	private List<Timetable> possibleTimetables1; //possible timetables for sem1
	private List<Timetable> possibleTimetables2; //possible timetables for sem2
	
	public SemesterConfiguration(Set<CourseListing> semester1, Set<CourseListing> semester2){
		this.semester1 = semester1;
		this.semester2 = semester2;
		this.possibleTimetables1 = new ArrayList<>();
		this.possibleTimetables2 = new ArrayList<>();
	}

	@Override
	public List<Set<CourseListing>> getSemesterConfigurations() {
		List<Set<CourseListing>> semesterConfigurations = new ArrayList<>();
		semesterConfigurations.add(semester1);
		semesterConfigurations.add(semester2);
		return semesterConfigurations;
	}
	
	public Set<CourseListing> getSemester1() {
		return semester1;
	}

	public Set<CourseListing> getSemester2() {
		return semester2;
	}
	
	public void addPossibleTimetables1(List<Timetable> timetables) {
		possibleTimetables1.addAll(timetables);
	}

	public void addPossibleTimetables2(List<Timetable> timetables) {
		possibleTimetables2.addAll(timetables);
	}
	
	public List<Timetable> getPossibleTimetables1() {
		return possibleTimetables1;
	}
	
	public List<Timetable> getPossibleTimetables2() {
		return possibleTimetables2;
	}
	
	@Override
	public String toJsonString() {
		// TODO
		return null;
	}
}
