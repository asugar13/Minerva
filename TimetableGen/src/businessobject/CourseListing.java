package businessobject;

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
	Set<CourseOffering> offerings; 
	CampusType campus;
	SemesterType semester;

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