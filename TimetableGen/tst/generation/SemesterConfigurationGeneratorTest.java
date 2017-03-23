package generation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import businessobject.CourseSelection;
import businessobject.CourseListing;
import dao.CourseListingDao;
import dao.CourseLoader;
import enums.SemesterType;

public class SemesterConfigurationGeneratorTest {

	public static final String FILE_PATH = "res/courses.json";
	public static final CourseListingDao LISTING_DAO = new CourseLoader(FILE_PATH);
	public static final TimetableConfigurationGenerator GENERATOR = new SemesterConfigurationGenerator(LISTING_DAO);
	
	@Test
	public void testConfigurationGeneratorMany() {
		Set<CourseSelection> courses = new HashSet<>();
		courses.add(new CourseSelection("CSC301", new HashSet<SemesterType>(Arrays.asList(SemesterType.FALL))));
		courses.add(new CourseSelection("CSC373", new HashSet<SemesterType>(Arrays.asList(SemesterType.WINTER))));
		courses.add(new CourseSelection("CSC369",
				new HashSet<SemesterType>(Arrays.asList(SemesterType.FALL, SemesterType.WINTER))));
		courses.add(new CourseSelection("CSC401",
				new HashSet<SemesterType>(Arrays.asList(SemesterType.FALL, SemesterType.WINTER))));
		courses.add(new CourseSelection("CSC258",
				new HashSet<SemesterType>(Arrays.asList(SemesterType.FALL, SemesterType.WINTER))));
		courses.add(new CourseSelection("CSC309",
				new HashSet<SemesterType>(Arrays.asList(SemesterType.FALL, SemesterType.WINTER))));
		courses.add(new CourseSelection("ECO100",
				new HashSet<SemesterType>(Arrays.asList(SemesterType.YEAR))));
		System.out.println(GENERATOR.generateConfigurations(courses).size());
		GENERATOR.generateConfigurations(courses).forEach((thing) -> {
			System.out.println("NEW CONFIG");
			for (Set<CourseListing> listings : thing.getSemesterConfigurations()) {
				System.out.println("NEW SEMESTER");
				for (CourseListing listing : listings) {
					System.out.println(listing.getCourseCode());
				}
			}
			System.out.println("");
		});
	}

	@Test
	public void testConfigurationGeneratorRestrictions() {
		Set<CourseSelection> courses = new HashSet<>();
		courses.add(new CourseSelection("CSC301", new HashSet<SemesterType>(Arrays.asList(SemesterType.FALL))));
		courses.add(new CourseSelection("CSC373", new HashSet<SemesterType>(Arrays.asList(SemesterType.WINTER))));
		courses.add(new CourseSelection("CSC369",
				new HashSet<SemesterType>(Arrays.asList(SemesterType.FALL, SemesterType.WINTER))));
		GENERATOR.generateConfigurations(courses).forEach((thing) -> {
			System.out.println("NEW CONFIG");
			for (Set<CourseListing> listings : thing.getSemesterConfigurations()) {
				System.out.println("NEW SEMESTER");
				for (CourseListing listing : listings) {
					System.out.println(listing.getCourseCode());
				}
			}
			System.out.println("");
		});
	}

	@Test
	public void testConfigurationGeneratorUnavailableSemester() {
		Set<CourseSelection> courses = new HashSet<>();
		courses.add(new CourseSelection("CSC301", new HashSet<SemesterType>(Arrays.asList(SemesterType.FALL))));
		courses.add(new CourseSelection("CSC373", new HashSet<SemesterType>(Arrays.asList(SemesterType.WINTER))));
		courses.add(new CourseSelection("CSC401",
				new HashSet<SemesterType>(Arrays.asList(SemesterType.FALL, SemesterType.WINTER))));
		GENERATOR.generateConfigurations(courses).forEach((thing) -> {
			System.out.println("NEW CONFIG");
			for (Set<CourseListing> listings : thing.getSemesterConfigurations()) {
				System.out.println("NEW SEMESTER");
				for (CourseListing listing : listings) {
					System.out.println(listing.getCourseCode());
				}
			}
			System.out.println("");
		});
	}
}
