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

		do {
			int[] code = new int[6];
			for (int i = 0; i < 6; i++) {
				code[i] = 0;
			}
			
			for (int i = 0; i < courseNum; i++) {
				int[] temp = iterators.get(i).getCurCode();
				for (int i2 = 0; i2 < 5; i2++) {
					if ((code[i2] & temp[i2]) > 0) {
						code[5]++;
					}
					code[i2] |= temp[i2];
				}
			}
			
			List<CourseOffering> co = new ArrayList<>();
			for (int i = 0; i < courseNum; i++) {
				co.add(iterators.get(i).getCourseOffering());
			}
			
			Timetable timetable = new Timetable(co, code[5]);
			result.add(timetable);
		} while (next(iterators, 0, listings, type));
		return result;
	}

	private boolean next(List<ClassIteratorHandler> l, int index, List<CourseListing> cl, SemesterType type) {
		int[] code = l.get(index).next();
		if (code[5] == -1) {
			if (index == l.size() - 1) {
				return false;
			}
			return next(l, index + 1, cl, type);
		}

		for (int i = 0; i < index; i++) {
			l.set(i, new ClassIteratorHandler(cl.get(i), type));
			l.get(i).next();
		}
		return true;
	}
	
	public class ClassIteratorHandler {		
		private Map<ClassType, ClassIterator> classes;
		
		private CourseListing course;
		private SemesterType semester;
		
		private int[] curCode;
		
		public ClassIteratorHandler(CourseListing course, SemesterType semester) {
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
			
			init();
		}
		
		private void init() {
			if (classes.containsKey(ClassType.TUT)) {
				if (classes.containsKey(ClassType.PRA)) {
					classes.get(ClassType.PRA).getNextClass();
				}
				if (classes.containsKey(ClassType.LEC)) {
					classes.get(ClassType.LEC).getNextClass();
				}
			} else if (classes.containsKey(ClassType.PRA)) {
				if (classes.containsKey(ClassType.LEC)) {
					classes.get(ClassType.LEC).getNextClass();
				}
			} 
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
		
		public ClassTime getCurLec() {
			if (!classes.containsKey(ClassType.LEC)) {
				return null;
			}
			return classes.get(ClassType.LEC).getCurClass();
		}
		
		public ClassTime getCurPra() {
			if (!classes.containsKey(ClassType.PRA)) {
				return null;
			}
			return classes.get(ClassType.PRA).getCurClass();
		}
		
		public ClassTime getCurTut() {
			if (!classes.containsKey(ClassType.TUT)) {
				return null;
			}
			return classes.get(ClassType.TUT).getCurClass();
		}
		
		private boolean hasNextClass(ClassType type) {
			if (classes.containsKey(type)) {
				return classes.get(type).hasNextClass();
			}
			return false;
		}
		
		private void putNewClass(ClassType type) {
			if (classes.containsKey(type)) {
				classes.put(type, new ClassIterator(course, semester, type));
				classes.get(type).getNextClass();
			}
		}
		
		private boolean hasNext() {
			if (hasNextClass(ClassType.TUT)) {
				classes.get(ClassType.TUT).getNextClass();
				return true;
			} else if (hasNextClass(ClassType.PRA)) {
				classes.get(ClassType.PRA).getNextClass();
				putNewClass(ClassType.TUT);
				return true;
			} else if (hasNextClass(ClassType.LEC)) {
				classes.get(ClassType.LEC).getNextClass();
				putNewClass(ClassType.TUT);
				putNewClass(ClassType.PRA);
				return true;
			}
			return false;
		}
		
		public int[] next() {
			do {
				int[] result = new int[6];
				for (int i = 0; i < 6; i++) {
					result[i] = 0;
				}
				
				if (!hasNext()) {
					result[5] = -1; // indicates no more.
					curCode = result;
					return result;
				}
				
				if (classes.containsKey(ClassType.LEC)) {
					int[] codes = classes.get(ClassType.LEC).getCurClass().getIntCodes();
					for (int i = 0; i < 5; i++) {
						result[i] |= codes[i];
					}
				}
				if (classes.containsKey(ClassType.PRA)) {
					boolean conflict = false;
					int[] codes = classes.get(ClassType.PRA).getCurClass().getIntCodes();
					for (int i = 0; i < 5; i++) {
						if ((result[i] & codes[i]) > 0) {
							conflict = true;
							break;
						}
						result[i] |= codes[i];
					}
					if (conflict) {
						continue;
					}
				}
				if (classes.containsKey(ClassType.TUT)) {
					boolean conflict = false;
					int[] codes = classes.get(ClassType.TUT).getCurClass().getIntCodes();
					for (int i = 0; i < 5; i++) {
						if ((result[i] & codes[i]) > 0) {
							conflict = true;
							break;
						}
						result[i] |= codes[i];
					}
					if (conflict) {
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
			classes = null;
			if (course.getCourseBySemester(semester).getClassTimes().containsKey(type)) {
				classes = course.getCourseBySemester(semester).getClassTimes().get(type);
				nextIndex = 0;
				curIndex = 0;
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
			return (isNull()) ? false : nextIndex < classes.size();
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
		listing1.add(acl.get("CSC209H1F"));
		Set<CourseListing> cl1 = new HashSet<CourseListing>(listing1);
		
		List<CourseListing> listing2 = new ArrayList<>();
		listing2.add(acl.get("CSC207H1S"));
		listing2.add(acl.get("CSC209H1S"));
		listing2.add(acl.get("CSC258H1S"));
		listing2.add(acl.get("CSC301H1S"));
		listing2.add(acl.get("CSC309H1S"));
		listing2.add(acl.get("CSC324H1S"));
		Set<CourseListing> cl2 = new HashSet<CourseListing>(listing2);
		
		SemesterConfiguration sc = new SemesterConfiguration(cl1, cl2);		
		TimetableGenerator tg = new TimetableGenerator();
		tg.generate(sc);
		
//		sc.getSemester1().forEach((l) -> {
//			System.out.println(l.toString());
//			System.out.println("======================================================");
//		});
		
		sc.getPossibleTimetables1().forEach((l) -> {
			System.out.println(l.toString());
			System.out.println(l.hasConflicts());
			System.out.println("======================================================");
		});
	}
}
