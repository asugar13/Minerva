package businessobject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import enums.CampusType;
import enums.ClassType;
import enums.SemesterType;


/**
 * 
 * Course information for a single semester
 *
 */
public class Course {
	String courseCode;
	String name;
	String description;
	int year;
	List<Integer> breadths;
	String prerequisite;
	String exclusions;
	Map<ClassType, List<ClassTime>> classTimes;
	CampusType campus;
	SemesterType semester;

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
	// ----------------------------------------------------//

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
		Course other = (Course) obj;
		if (courseCode == null) {
			if (other.courseCode != null)
				return false;
		} else if (!courseCode.equals(other.courseCode))
			return false;
		if (semester != other.semester)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("course code: " + courseCode + "\n");
		sb.append("course name: " + name + "\n");
		sb.append("description: " + description + "\n");
		sb.append("year: " + year + "\n");
		
		sb.append("breadths:");
		for (int i = 0; i < breadths.size(); i++) {
			sb.append(" " + breadths.get(i));
		}
		sb.append("\n");
		
		sb.append("prerequisites: " + prerequisite + "\n");
		sb.append("exclusions: " + exclusions + "\n");
		
		sb.append("Classes: \n");
		Set<ClassType> s = classTimes.keySet();
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
			List<ClassTime> l = classTimes.get(cl);
			for (int i = 0; i < l.size(); i++) {
				sb.append(l.get(i).toString());
			}
		}
		
		sb.append("campus: ");
		switch (campus) {
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
		switch (semester) {
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
}