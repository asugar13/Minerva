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
	public void CourseNameGetterTest(){
	    assertEquals ("Intro to Academic Studies",c.getName());  
	}
	
	@Test
	public void CourseCodeGetterTest(){
		 assertEquals ("ABP100Y1Y",c.getCourseCode());
	}
	
	@Test
	public void CourseDescriptionGetterTest(){
		 assertEquals ("",c.getDescription());
	}
	
	@Test
	public void CoursePrerequisiteGetterTest(){
		 assertEquals ("",c.getPrerequisite());
	}
	
	@Test
	public void CourseExclusionGetterTest(){
		 assertEquals ("",c.getExclusions());
	}
	
	@Test
	public void CourseYearGetterTest(){
		 assertEquals (2016,c.getYear());
	}
	@Test
	public void CourseSemesterGetterTest(){
		 assertEquals (SemesterType.YEAR,c.getSemester());
	}
	
	@Test
	public void CourseCampusrGetterTest(){
		 assertEquals (CampusType.UTSG,c.getCampus());
	}
}
