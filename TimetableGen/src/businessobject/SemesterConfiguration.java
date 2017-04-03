package businessobject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import enums.ClassType;

public class SemesterConfiguration implements TimetableConfiguration {

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
	
	public JSONObject toJsonObject(){
		JSONObject semesters = new JSONObject();
		JSONObject fallSem = new JSONObject();
		JSONArray fallTimetables = new JSONArray();
		JSONObject winSem = new JSONObject();
		JSONArray winTimetables = new JSONArray();
		for (Timetable timetable : possibleTimetables1){
			fallTimetables.add(timetable.toJsonObject());
		}
		for (Timetable timetable : possibleTimetables2){
			winTimetables.add(timetable.toJsonObject());
		}
		fallSem.put("timetables", fallTimetables);
		winSem.put("timetables", winTimetables);
		semesters.put("Fall", fallSem);
		semesters.put("Winter", winSem);
		return semesters;
	}
	
	@Override
	public String toJsonString() {
		// TODO
		JSONObject obj = new JSONObject();
		
		//iterate through each possible timetable for semester1
		for (Timetable timetable : this.possibleTimetables1){
			JSONArray coursesArray = new JSONArray();
			
			//iterate through each course offering in current timetable
			for (CourseOffering courseoffering : timetable.courseOfferings){
				JSONObject course = new JSONObject();
				JSONArray classesArray = new JSONArray();
				course.put("courseCode", courseoffering.getListing().getCourseCode());
				
				//iterate through each course offering's class times
				Iterator<ClassType> classTypes = courseoffering.getClassTime().keySet().iterator();
				while (classTypes.hasNext()){
					ClassType classtype = classTypes.next();
					
					if (classtype == ClassType.LEC){
						JSONObject lecture = new JSONObject();
						JSONArray lectureTimesArray = new JSONArray();
						ClassTime lectureTime = courseoffering.getSpecificClasstime(classtype);
						lecture.put("classCode", lectureTime.getclassCode());
						List<TimeSlot> timeslots = lectureTime.getTimeSlots();
						
						for (TimeSlot timeslot: timeslots){
							JSONObject timeslotobj = new JSONObject();
							timeslotobj.put("day", timeslot.getDay());
							timeslotobj.put("start", timeslot.getStart());
							timeslotobj.put("end",timeslot.getStart() + timeslot.getDuration());
							lectureTimesArray.add(timeslotobj);

						}
						lecture.put("times", lectureTimesArray);
						JSONObject LEC = new JSONObject();
						LEC.put("LEC", lecture);
						classesArray.add(LEC);


					}
					else if  (classtype == ClassType.TUT){
						JSONObject tutorial = new JSONObject();
						JSONArray tutorialTimesArray = new JSONArray();
						ClassTime tutorialTime = courseoffering.getSpecificClasstime(classtype);
						tutorial.put("classCode", tutorialTime.getclassCode());
						List<TimeSlot> timeslots = tutorialTime.getTimeSlots();
						
						for (TimeSlot timeslot: timeslots){
							JSONObject timeslotobj = new JSONObject();
							timeslotobj.put("day", timeslot.getDay());
							timeslotobj.put("start", timeslot.getStart());
							timeslotobj.put("end",timeslot.getStart() + timeslot.getDuration());
							tutorialTimesArray.add(timeslotobj);

						}
						tutorial.put("times", tutorialTimesArray);
						JSONObject TUT = new JSONObject();
						TUT.put("TUT", tutorial);
						classesArray.add(TUT);

					}
					else if  (classtype == ClassType.PRA){
						JSONObject PRAobject = new JSONObject();
						JSONArray PRATimesArray = new JSONArray();
						ClassTime PRATime = courseoffering.getSpecificClasstime(classtype);
						PRAobject.put("classCode", PRATime.getclassCode());
						List<TimeSlot> timeslots = PRATime.getTimeSlots();
						
						for (TimeSlot timeslot: timeslots){
							JSONObject timeslotobj = new JSONObject();
							timeslotobj.put("day", timeslot.getDay());
							timeslotobj.put("start", timeslot.getStart());
							timeslotobj.put("end",timeslot.getStart() + timeslot.getDuration());
							PRATimesArray.add(timeslotobj);
						}
						PRAobject.put("times", PRATimesArray);
						JSONObject PRA = new JSONObject();
						PRA.put("PRA", PRAobject);
						classesArray.add(PRA);

					}
					
				}
				//before being done with current course offering
				course.put("classes", classesArray);
			}
		}
		
		return null;
	}
}
