package businessobjectTest;
import static org.junit.Assert.assertEquals;
import businessobject.Course;
import org.junit.Test;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import enums.CampusType;
import enums.ClassType;
import enums.SemesterType;

public class CourseTest {
	//Basic constructor and getter tests.
	
	private static Course c = TestUtil.createCourse();
	
	@Test
	public void courseNameGetterTest(){
	    assertEquals ("Intro to Academic Studies",c.getName());  
	}
	
	@Test
	public void courseCodeGetterTest(){
		 assertEquals ("ABP100Y1Y",c.getCourseCode());
	}
	
	@Test
	public void courseDescriptionGetterTest(){
		 assertEquals ("",c.getDescription());
	}
	
	@Test
	public void coursePrerequisiteGetterTest(){
		 assertEquals ("",c.getPrerequisite());
	}
	
	@Test
	public void courseExclusionGetterTest(){
		 assertEquals ("",c.getExclusions());
	}
	
	@Test
	public void courseYearGetterTest(){
		 assertEquals (2016,c.getYear());
	}
	@Test
	public void courseSemesterGetterTest(){
		 assertEquals (SemesterType.YEAR,c.getSemester());
	}
	
	@Test
	public void courseCampusrGetterTest(){
		 assertEquals (CampusType.UTSG,c.getCampus());
	}
}
