package generation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import businessobject.CourseListing;
import businessobject.CourseSelection;
import businessobject.SemesterConfiguration;
import businessobject.TimetableConfiguration;
import dao.CourseListingDao;
import enums.SemesterType;

public class SemesterConfigurationGenerator implements TimetableConfigurationGenerator {
	private CourseListingDao listingDao;

	public SemesterConfigurationGenerator(CourseListingDao listingDao) {
		this.listingDao = listingDao;
	}

	@Override
	public Set<TimetableConfiguration> generateConfigurations(Set<CourseSelection> courses) {
		// TODO Uses listingDao to get CourseOffering for each course, creates
		// all possible TimetableConfigurations for 2 semesters given the
		// restrictions in CourseSelection and a maximum of 6 courses per
		// semester
		/*
		 * HashSet<CourseListing> semester1 = new HashSet<>(); //set of courses
		 * in the 1st semester HashSet<CourseListing> semester2 = new
		 * HashSet<>(); //set of courses in the 2nd semester Map<String,
		 * CourseListing> allCourseListings =
		 * this.listingDao.getAllCourseListings();
		 * 
		 * 
		 * for (CourseSelection course : courses){ if
		 * (allCourseListings.containsKey(course.getCourseCode())){
		 * CourseListing courseListing =
		 * allCourseListings.get(course.getCourseCode()); Iterator<SemesterType>
		 * offeredIn = courseListing.getSemesterToCourse().keySet().iterator();
		 * while (offeredIn.hasNext()){ SemesterType term = offeredIn.next(); if
		 * (term == SemesterType.WINTER) { semester1.add(courseListing); } else
		 * if (term == SemesterType.FALL){ semester2.add(courseListing); } else
		 * if (term == SemesterType.YEAR){ semester1.add(courseListing);
		 * semester2.add(courseListing); } } } } for (CourseListing
		 * courseListing : semester1){
		 * 
		 * }
		 * 
		 * return null;
		 * 
		 * }
		 */
		final Set<CourseListing> baseSem1 = new HashSet<>();
		final Set<CourseListing> baseSem2 = new HashSet<>();
		courses = addBaseCourses(courses, baseSem1, baseSem2);
		Set<TimetableConfiguration> timetableConfigurations = new HashSet<>();
		for (int k = 1; k <= 6; k++) {
			if (k <= courses.size() && baseSem1.size() + k <= 6 && baseSem2.size() + courses.size() - k <= 6) {
				timetableConfigurations.addAll(chooseCourses(new ArrayList<>(courses), k, baseSem1, baseSem2));
			}
		}
		return timetableConfigurations;
	}

	private Set<CourseSelection> addBaseCourses(Set<CourseSelection> courses, Set<CourseListing> baseSem1,
			Set<CourseListing> baseSem2) {
		Set<CourseSelection> coursesCopy = new HashSet<>(courses);
		for (CourseSelection course : courses) {
			Set<SemesterType> restrictions = course.getRestrictions();
			if (restrictions.size() == 1) {
				String courseCode = course.getCourseCode();
				if (restrictions.contains(SemesterType.FALL)) {
					baseSem1.add(getCourse(courseCode, SemesterType.FALL));
				} else if (restrictions.contains(SemesterType.WINTER)) {
					baseSem2.add(getCourse(courseCode, SemesterType.WINTER));
				} else {
					baseSem1.add(getCourse(courseCode, SemesterType.YEAR));
					baseSem2.add(getCourse(courseCode, SemesterType.YEAR));
				}
				coursesCopy.remove(course);
			}
		}
		return coursesCopy;
	}

	private Set<TimetableConfiguration> chooseCourses(List<CourseSelection> courses, int numChoose,
			Set<CourseListing> baseSem1, Set<CourseListing> baseSem2) {
		/*
		 * Set<TimetableConfiguration> timetableConfigurations = new
		 * HashSet<>(); int numCourses = courses.size(); int r = 0; int i = 0;
		 * Set<CourseListing> sem1 = new HashSet(baseSem1); Set<CourseListing>
		 * sem2 = new HashSet(baseSem2); while(r >= 0){ if(i <= numCourses + r -
		 * numChoose){ sem1.add(getCourse(courses.get(i).getCourseCode(),
		 * SemesterType.FALL)); if(r == numChoose - 1){ courses. } } }
		 */
		Set<TimetableConfiguration> timetableConfigurations = new HashSet<>();
		int numCourses = courses.size();
		Set<CourseListing> sem1 = new HashSet<>(baseSem1);
		Set<CourseListing> sem2 = new HashSet<>(baseSem2);
		List<Integer> indices = new ArrayList<>();
		int r = numChoose;
		if (r <= numCourses) {
			for (int i = 0; i < numChoose; i++) { // initial input seq
				indices.add(i);
			}
			try {
				timetableConfigurations.add(createTimetableConfiguration(courses, sem1, sem2, indices));
			} catch (IllegalArgumentException e) {
			}
			sem1 = new HashSet<>(baseSem1);
			sem2 = new HashSet<>(baseSem2);
			while (true) {
				int i = numChoose - 1;
				while (i >= 0 && indices.get(i) == numCourses - numChoose + i) {
					i--;
				}
				if (i < 0) {
					break;
				} else {
					int index = indices.get(i);
					indices.set(i, index + 1);
					for (++i; i < numChoose; i++) {
						indices.set(i, indices.get(i - 1) + 1);
					}
					try {
						timetableConfigurations.add(createTimetableConfiguration(courses, sem1, sem2, indices));
					} catch (IllegalArgumentException e) {
					}
					sem1 = new HashSet<>(baseSem1);
					sem2 = new HashSet<>(baseSem2);
				}
			}
		}
		return timetableConfigurations;
	}

	private TimetableConfiguration createTimetableConfiguration(List<CourseSelection> courses, Set<CourseListing> sem1,
			Set<CourseListing> sem2, List<Integer> indices) {
		for (int i = 0; i < courses.size(); i++) {
			CourseSelection courseSelection = courses.get(i);
			if (indices.contains(i)) {
				sem1.add(getCourse(courseSelection.getCourseCode(), SemesterType.FALL));
			} else {
				sem2.add(getCourse(courseSelection.getCourseCode(), SemesterType.WINTER));
			}
		}
		return new SemesterConfiguration(new HashSet<>(sem1), new HashSet<>(sem2));
	}

	private CourseListing getCourse(String courseCode, SemesterType semester) {
		return listingDao.getCourseListing(courseCode + semester.code()).get(courseCode + semester.code());
	}
}
