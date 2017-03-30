package businessobject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;

import enums.CampusType;
import enums.ClassType;
import enums.SemesterType;


/**
 * 
 * Course information for a single semester
 *
 */
public class Course {
	private String courseCode;
	private String name;
	private String description;
	private String prerequisite;
	private String exclusions;
	private int year;
	private CampusType campus;
	private List<Integer> breadths;
	private Map<ClassType, List<ClassTime>> classTimes;
	private SemesterType semester;

	public Course(String name, String courseCode, String description, String prerequisite, String exclusions,
			int year, CampusType campus, SemesterType semester, Map<ClassType, List<ClassTime>> classTimes,
			List<Integer> breadths) {

		/** Instance variables we know upon instantiation */
		this.name = name;
		this.courseCode = courseCode;
		this.description = description;
		this.prerequisite = prerequisite;
		this.exclusions = exclusions;
		this.year = year;
		this.campus = campus;
		this.semester = semester;
		this.classTimes = classTimes;
		this.breadths = breadths;
	}

	/** Getters */

	public Map<ClassType, List<ClassTime>> getClassTimes() {
		return this.classTimes;
	}

	public List<Integer> getBreadths() {
		return this.breadths;
	}

	public String getCourseCode() {
		return this.courseCode;
	}

	public String getName() {
		return this.name;
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
	// ----------------------------------------------------//

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//if condition is true then 0, else courseCode.hasCode()
		result = prime * result + ((this.courseCode == null) ? 0 : courseCode.hashCode());
		result = prime * result + ((this.semester == null) ? 0 : semester.hashCode());
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
		Course other = (Course) obj;
		if (this.courseCode == null) {
			if (other.courseCode != null)
				return false;
		} else if (!this.courseCode.equals(other.courseCode))
			return false;
		if (this.semester != other.semester)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("course code: " + this.courseCode + "\n");
		sb.append("course name: " + this.name + "\n");
		sb.append("description: " + this.description + "\n");
		sb.append("year: " + this.year + "\n");
		
		sb.append("breadths:");
		for (int i = 0; i < this.breadths.size(); i++) {
			sb.append(" " + this.breadths.get(i));
		}
		sb.append("\n");
		
		sb.append("prerequisites: " + this.prerequisite + "\n");
		sb.append("exclusions: " + this.exclusions + "\n");
		
		sb.append("Classes: \n");
		Set<ClassType> s = this.classTimes.keySet();
		Iterator<ClassType> tor = s.iterator();
		while (tor.hasNext()) {
			ClassType cl = tor.next();
			switch (cl) {
			case LEC:
				sb.append("lecture: \n");
				break;
			case TUT:
				sb.append("tutorial: \n");
				break;
			case PRA:
				sb.append("practical: \n");
				break;
			default:
				break;
			}
			List<ClassTime> l = this.classTimes.get(cl);
			for (int i = 0; i < l.size(); i++) {
				sb.append(l.get(i).toString());
			}
		}
		
		sb.append("campus: ");
		switch (this.campus) {
		case UTSG:
			sb.append("UTSG\n");
			break;
		case UTM:
			sb.append("UTM\n");
			break;
		case UTSC:
			sb.append("UTSC\n");
			break;
		default:
			break;
		}

		sb.append("semester: ");
		switch (this.semester) {
		case FALL:
			sb.append("fall\n");
			break;
		case WINTER:
			sb.append("winter\n");
			break;
		case YEAR:
			sb.append("year\n");
			break;
		default:
			break;
		}
		
		return sb.toString();
	}

	public JSONObject toJsonObject() {
		JSONObject course = new JSONObject();
		String code = courseCode;
		if(courseCode.length() > 8){
			code = courseCode.substring(0, 8);
		}
		course.put("courseCode", code);
		course.put("name", name);
		course.put("description", description);
		course.put("breadths", breadths);
		course.put("prerequisites", prerequisite);
		course.put("exclusions", exclusions);
		return course;
	}
}