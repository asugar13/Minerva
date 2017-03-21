package generation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dao.CourseLoader;
import businessobject.ClassTime;
import businessobject.CourseListing;
import businessobject.CourseOffering;
import businessobject.SemesterConfiguration;
import businessobject.Timetable;
import enums.ClassType;
import enums.SemesterType;

public class TimetableGenerator {	
	public TimetableGenerator() {}

	public SemesterConfiguration generate(SemesterConfiguration courses) {
		courses.addPossibleTimetables1(generateSemester(courses, courses.getSemester1(), SemesterType.FALL));
		courses.addPossibleTimetables2(generateSemester(courses, courses.getSemester2(), SemesterType.WINTER));
		return courses;
	}
	
	private List<Timetable> generateSemester(SemesterConfiguration courses, Set<CourseListing> courseListing, SemesterType type) {
		List<Timetable> result = new ArrayList<>();
		
		List<ClassIteratorHandler> iterators = new ArrayList<>();
		List<CourseListing> listings = new ArrayList<>();
		courseListing.forEach((l) -> {
			listings.add(l);
			iterators.add(new ClassIteratorHandler(l, type));
		});
		
		int courseNum = iterators.size();
		for (int i = 0; i < courseNum; i++) {
			iterators.get(i).next();
		}

		while (true) {
			int[] code = new int[6];
			for (int i = 0; i < 6; i++) {
				code[i] = 0;
			}
			
			for (int i = 0; i < courseNum; i++) {
				int[] temp = iterators.get(i).getCurCode();
				for (int i2 = 0; i2 < 5; i2++) {
					if ((code[i2] & temp[i2]) > 0) {
						code[5] = -1;
					}
					code[i2] |= temp[i2];
				}
			}
			
			List<CourseOffering> co = new ArrayList<>();
			for (int i = 0; i < courseNum; i++) {
				co.add(iterators.get(i).getCourseOffering());
			}
			
			Timetable timetable = new Timetable(co, (code[5] == -1) ? true : false);
			result.add(timetable);
			
			if (next(iterators, 0, listings, type) == -1) {
				break;
			}
		}
		return result;
	}

	private int next(List<ClassIteratorHandler> l, int index, List<CourseListing> cl, SemesterType type) {
		int[] code = l.get(index).next();
		if (code[5] == -1) {
			if (index == l.size() - 1) {
				return -1;
			}
			return next(l, index + 1, cl, type);
		}

		for (int i = 0; i < index; i++) {
			l.set(i, new ClassIteratorHandler(cl.get(i), type));
			l.get(i).next();
		}
		return 0;
	}
	
	private class ClassIteratorHandler {
		private Map<ClassType, ClassIterator> classes;
		
		private CourseListing course;
		private SemesterType semester;
		
		private int[] curCode;
		
		private ClassIteratorHandler(CourseListing course, SemesterType semester) {
			this.course = course;
			this.semester = semester;
			
			classes = new HashMap<>();
			
			ClassIterator temp = new ClassIterator(course, semester, ClassType.LEC);
			if (!temp.isNull()) {
				classes.put(ClassType.LEC, temp);
			}
			
			temp = new ClassIterator(course, semester, ClassType.PRA);
			if (!temp.isNull()) {
				classes.put(ClassType.PRA, temp);
			}
			
			temp = new ClassIterator(course, semester, ClassType.TUT);
			if (!temp.isNull()) {
				classes.put(ClassType.TUT, temp);
			}
		}
		
		public ClassTime getCurLec() {
			if (!classes.containsKey(ClassType.LEC)) {
				return null;
			}
			return classes.get(ClassType.LEC).getCurClass();
		}
		
		private boolean hasNextLec() {		
			if (classes.get(ClassType.LEC).hasNextClass()) {
				if (classes.containsKey(ClassType.TUT)) {
					classes.put(ClassType.TUT, new ClassIterator(course, semester, ClassType.TUT));					
				}
				if (classes.containsKey(ClassType.PRA)) {
					classes.put(ClassType.PRA, new ClassIterator(course, semester, ClassType.PRA));					
				}
				return true;
			}
			return false;
		}
		
		public ClassTime getCurPra() {
			if (!classes.containsKey(ClassType.PRA)) {
				return null;
			}
			return classes.get(ClassType.PRA).getCurClass();
		}
		
		private boolean hasNextPra() {
			if (classes.get(ClassType.PRA).hasNextClass()) {
				if (classes.containsKey(ClassType.TUT)) {
					classes.put(ClassType.TUT, new ClassIterator(course, semester, ClassType.TUT));
				}
				return true;
			}
			return false;
		}
		
		public ClassTime getCurTut() {
			if (!classes.containsKey(ClassType.TUT)) {
				return null;
			}
			return classes.get(ClassType.TUT).getCurClass();
		}
		
		private boolean hasNextTut() {
			if (classes.get(ClassType.TUT).hasNextClass()) {
				return true;
			}
			return false;
		}
		
		public CourseOffering getCourseOffering() {
			Map<ClassType, ClassTime> map = new HashMap<>();
			if (getCurLec() != null) {
				map.put(ClassType.LEC, getCurLec());
			}
			if (getCurPra() != null) {
				map.put(ClassType.PRA, getCurPra());
			}
			if (getCurTut() != null) {
				map.put(ClassType.TUT, getCurTut());
			}
			CourseOffering co = new CourseOffering(course.getCourseBySemester(semester), map);
			return co;
		}
		
		public int[] getCurCode() {
			return curCode;
		}
		
		private boolean hasNext() {
			if (classes.containsKey(ClassType.TUT)) {
				if (hasNextTut()) {
					return true;
				}
				if (classes.containsKey(ClassType.PRA) && hasNextPra()) {
					return true;
				}
				if (classes.containsKey(ClassType.LEC) && hasNextLec()) {
					return true;
				}
				return false;
			} else if (classes.containsKey(ClassType.PRA)) {
				if (hasNextPra()) {
					return true;
				}
				if (classes.containsKey(ClassType.LEC) && hasNextLec()) {
					return true;
				}
				return false;
			} else if (classes.containsKey(ClassType.LEC)) {
				return hasNextLec();
			}
			return false;
		}
		
		public int[] next() {
			int[] result = new int[6];
			do {
				for (int i = 0; i < 6; i++) {
					result[i] = 0;
				}
				
				if (!hasNext()) {
					result[5] = -1; // indicates no more.
					curCode = result;
					return result;
				}

				if (classes.containsKey(ClassType.LEC)) {
					int[] codes = classes.get(ClassType.LEC).getNextClass().getIntCodes();
					for (int i = 0; i < 5; i++) {
						result[i] |= codes[i];
					}
					
				}
				if (classes.containsKey(ClassType.PRA)) {
					int conflict = -1;
					int[] codes = classes.get(ClassType.PRA).getNextClass().getIntCodes();
					for (int i = 0; i < 5; i++) {
						if ((result[i] & codes[i]) > 0) {
							conflict = 1;
							break;
						}
						result[i] |= codes[i];
					}
					if (conflict == 1) {
						continue;
					}
				}
				if (classes.containsKey(ClassType.TUT)) {
					int conflict = -1;
					int[] codes = classes.get(ClassType.TUT).getNextClass().getIntCodes();
					for (int i = 0; i < 5; i++) {
						if ((result[i] & codes[i]) > 0) {
							conflict = 1;
							break;
						}
						result[i] |= codes[i];
					}
					if (conflict == 1) {
						continue;
					}
				}
				curCode = result;
				return result;
			} while (true);
		}
	}
	
	private class ClassIterator {
		private List<ClassTime> classes;
		private int curIndex;
		private int nextIndex;
		
		private ClassIterator(CourseListing course, SemesterType semester, ClassType type) {
			if (course.getCourseBySemester(semester).getClassTimes().containsKey(type)) {
				classes = course.getCourseBySemester(semester).getClassTimes().get(type);
				nextIndex = 0;
			} else {
				classes = null;
			}
		}
		
		public boolean isNull() {
			if (classes == null) 
				return true;
			return false;
		}
		
		public ClassTime getCurClass() {
			return classes.get(curIndex);
		}
		
		public boolean hasNextClass() {
			if (isNull()) 
				return false;

			return nextIndex < classes.size();
		}
		
		public ClassTime getNextClass() {
			curIndex = nextIndex;
			nextIndex++;
			return classes.get(curIndex);
		}
	}
	
	public static void main(String[] args) {
		CourseLoader cl = new CourseLoader(CourseLoader.FILE_PATH);
		Map<String, CourseListing> acl = cl.getAllCourseListings();
		

		List<CourseListing> listing1 = new ArrayList<>();
		listing1.add(acl.get("CSC301H1F"));
		listing1.add(acl.get("CSC324H1F"));
		listing1.add(acl.get("CSC309H1F"));
		Set<CourseListing> cl1 = new HashSet<CourseListing>(listing1);
		
		List<CourseListing> listing2 = new ArrayList<>();
		listing2.add(acl.get("CSC301H1S"));
		listing2.add(acl.get("CSC309H1S"));
		listing2.add(acl.get("CSC324H1S"));
		listing2.add(acl.get("CSC358H1S"));
		Set<CourseListing> cl2 = new HashSet<CourseListing>(listing2);
		
		SemesterConfiguration sc = new SemesterConfiguration(cl1, cl2);		
		TimetableGenerator tg = new TimetableGenerator();
		tg.generate(sc);
		
//		sc.getSemester1().forEach((l) -> {
//			System.out.println(l.toString());
//		});
		sc.getPossibleTimetables2().forEach((l) -> {
			l.getCourseOfferings().forEach((ll) -> {
				System.out.println(ll.toString());
			});
			System.out.println();
			System.out.println();			
		});
	}
}
