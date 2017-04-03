package businessobject;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import enums.CampusType;
import enums.ClassType;
import enums.Day;
import enums.SemesterType;

public class SemesterConfigurationSample {
	public static void main(String[] args) {

		TimeSlot t1 = new TimeSlot(Day.TUESDAY, 43200, 7200);
		TimeSlot t2 = new TimeSlot(Day.TUESDAY, 64800, 7200);

		TimeSlot t3 = new TimeSlot(Day.THURSDAY, 43200, 3600);
		TimeSlot t4 = new TimeSlot(Day.THURSDAY, 46800, 3600);
		List<TimeSlot> timelist1 = new ArrayList<TimeSlot>();
		timelist1.add(t1);
		List<TimeSlot> timelist2 = new ArrayList<TimeSlot>();
		timelist2.add(t2);
		List<TimeSlot> timelist3 = new ArrayList<TimeSlot>();
		timelist3.add(t3);
		List<TimeSlot> timelist4 = new ArrayList<TimeSlot>();
		timelist4.add(t4);

		ClassTime classtime1 = new ClassTime("L0101", timelist1);
		ClassTime classtime2 = new ClassTime("L0201", timelist2);
		ClassTime classtime3 = new ClassTime("T0101", timelist3);
		ClassTime classtime4 = new ClassTime("T0201", timelist4);
		List<ClassTime> classlec1 = new ArrayList<ClassTime>();
		classlec1.add(classtime1);
		classlec1.add(classtime2);
		List<ClassTime> classtut1 = new ArrayList<ClassTime>();
		classtut1.add(classtime3);
		classtut1.add(classtime4);

		Map<ClassType, List<ClassTime>> classmap1 = new HashMap<ClassType, List<ClassTime>>();
		classmap1.put(ClassType.LEC, classlec1);
		classmap1.put(ClassType.TUT, classtut1);

		List<Integer> breadthlist1 = new ArrayList<Integer>();
		breadthlist1.add(5);

		TimeSlot t5 = new TimeSlot(Day.MONDAY, 43200, 3600);
		TimeSlot t6 = new TimeSlot(Day.WEDNESDAY, 43200, 3600);

		TimeSlot t7 = new TimeSlot(Day.FRIDAY, 43200, 3600);
		TimeSlot t8 = new TimeSlot(Day.FRIDAY, 46800, 3600);
		List<TimeSlot> timelist5 = new ArrayList<TimeSlot>();
		timelist1.add(t5);
		timelist1.add(t6);
		List<TimeSlot> timelist6 = new ArrayList<TimeSlot>();
		timelist6.add(t7);
		List<TimeSlot> timelist7 = new ArrayList<TimeSlot>();
		timelist7.add(t8);

		ClassTime classtime5 = new ClassTime("L0101", timelist5);
		ClassTime classtime6 = new ClassTime("T0101", timelist6);
		ClassTime classtime7 = new ClassTime("T0201", timelist7);
		List<ClassTime> classlec2 = new ArrayList<ClassTime>();
		classlec2.add(classtime5);
		List<ClassTime> classtut2 = new ArrayList<ClassTime>();
		classtut2.add(classtime6);
		classtut2.add(classtime7);

		Map<ClassType, List<ClassTime>> classmap2 = new HashMap<ClassType, List<ClassTime>>();
		classmap2.put(ClassType.LEC, classlec2);
		classmap2.put(ClassType.TUT, classtut2);

		List<Integer> breadthlist2 = new ArrayList<Integer>();
		breadthlist2.add(3);

		Course c1 = new Course("Introduction to Software Engineering", "CSC301H1", "description", "CSC209H1", "None",
				2017, CampusType.UTSG, SemesterType.FALL, classmap1, breadthlist1);
		Course c2 = new Course("Computers and Society", "CSC300H1", "description", "Any half course on computing",
				"None", 2017, CampusType.UTSG, SemesterType.FALL, classmap2, breadthlist2);

		Map<SemesterType, Course> courselistingmap1 = new HashMap<SemesterType, Course>();
		courselistingmap1.put(SemesterType.FALL, c1);
		courselistingmap1.put(SemesterType.FALL, c2);
		CourseListing listing1 = new CourseListing("CSC301H1", courselistingmap1);
		CourseListing listing2 = new CourseListing("CSC300H1", courselistingmap1);

		Set<CourseListing> semesterfall = new HashSet<CourseListing>();
		semesterfall.add(listing1);
		semesterfall.add(listing2);

		TimeSlot t9 = new TimeSlot(Day.TUESDAY, 64800, 7200);
		TimeSlot t10 = new TimeSlot(Day.THURSDAY, 64800, 7200);

		TimeSlot t11 = new TimeSlot(Day.TUESDAY, 72000, 3600);
		TimeSlot t12 = new TimeSlot(Day.THURSDAY, 72000, 3600);
		TimeSlot t13 = new TimeSlot(Day.FRIDAY, 43200, 3600);
		List<TimeSlot> timelist8 = new ArrayList<TimeSlot>();
		timelist8.add(t9);
		List<TimeSlot> timelist9 = new ArrayList<TimeSlot>();
		timelist9.add(t10);
		List<TimeSlot> timelist10 = new ArrayList<TimeSlot>();
		timelist10.add(t11);
		List<TimeSlot> timelist11 = new ArrayList<TimeSlot>();
		timelist11.add(t12);
		List<TimeSlot> timelist12 = new ArrayList<TimeSlot>();
		timelist12.add(t13);
		ClassTime classtime8 = new ClassTime("L0101", timelist8);
		ClassTime classtime9 = new ClassTime("L0201", timelist9);
		ClassTime classtime10 = new ClassTime("T0101", timelist10);
		ClassTime classtime11 = new ClassTime("T0201", timelist11);
		ClassTime classtime12 = new ClassTime("T0201", timelist12);
		List<ClassTime> classlec3 = new ArrayList<ClassTime>();
		classlec3.add(classtime8);
		classlec3.add(classtime9);
		List<ClassTime> classtut3 = new ArrayList<ClassTime>();
		classtut3.add(classtime10);
		classtut3.add(classtime11);
		classtut3.add(classtime12);

		Map<ClassType, List<ClassTime>> classmap3 = new HashMap<ClassType, List<ClassTime>>();
		classmap3.put(ClassType.LEC, classlec3);
		classmap3.put(ClassType.TUT, classtut3);

		List<Integer> breadthlist3 = new ArrayList<Integer>();
		breadthlist3.add(5);

		Course c3 = new Course("Operating Systems", "CSC369H1", "description", "CSC209H1,CSC258H1", "None", 2017,
				CampusType.UTSG, SemesterType.WINTER, classmap3, breadthlist3);

		TimeSlot t14 = new TimeSlot(Day.MONDAY, 39600, 3600);
		TimeSlot t15 = new TimeSlot(Day.WEDNESDAY, 39600, 3600);
		TimeSlot t16 = new TimeSlot(Day.MONDAY, 64800, 7200);
		TimeSlot t17 = new TimeSlot(Day.MONDAY, 72000, 3600);
		TimeSlot t18 = new TimeSlot(Day.THURSDAY, 43200, 3600);
		TimeSlot t19 = new TimeSlot(Day.FRIDAY, 39600, 3600);
		TimeSlot t20 = new TimeSlot(Day.FRIDAY, 46800, 3600);

		List<TimeSlot> timelist13 = new ArrayList<TimeSlot>();
		timelist13.add(t14);
		timelist13.add(t15);
		List<TimeSlot> timelist14 = new ArrayList<TimeSlot>();
		timelist14.add(t16);
		List<TimeSlot> timelist15 = new ArrayList<TimeSlot>();
		timelist15.add(t17);
		List<TimeSlot> timelist16 = new ArrayList<TimeSlot>();
		timelist16.add(t18);
		List<TimeSlot> timelist17 = new ArrayList<TimeSlot>();
		timelist17.add(t19);
		List<TimeSlot> timelist18 = new ArrayList<TimeSlot>();
		timelist18.add(t20);
		ClassTime classtime13 = new ClassTime("L0101", timelist13);
		ClassTime classtime14 = new ClassTime("L0201", timelist14);
		ClassTime classtime15 = new ClassTime("T0101", timelist15);
		ClassTime classtime16 = new ClassTime("T0201", timelist16);
		ClassTime classtime17 = new ClassTime("T0301", timelist17);
		ClassTime classtime18 = new ClassTime("T0401", timelist18);
		List<ClassTime> classlec4 = new ArrayList<ClassTime>();
		classlec4.add(classtime13);
		classlec4.add(classtime14);
		List<ClassTime> classtut4 = new ArrayList<ClassTime>();
		classtut4.add(classtime15);
		classtut4.add(classtime16);
		classtut4.add(classtime17);
		classtut4.add(classtime18);
		Map<ClassType, List<ClassTime>> classmap4 = new HashMap<ClassType, List<ClassTime>>();
		classmap4.put(ClassType.LEC, classlec4);
		classmap4.put(ClassType.TUT, classtut4);

		List<Integer> breadthlist4 = new ArrayList<Integer>();
		breadthlist4.add(5);
		Course c4 = new Course("Programming on the web", "CSC309H1", "description", "CSC209H1", "None", 2017,
				CampusType.UTSG, SemesterType.WINTER, classmap4, breadthlist4);

		Map<SemesterType, Course> courselistingmap2 = new HashMap<SemesterType, Course>();
		courselistingmap2.put(SemesterType.FALL, c3);
		courselistingmap2.put(SemesterType.FALL, c4);
		CourseListing listing3 = new CourseListing("CSC369H1", courselistingmap2);
		CourseListing listing4 = new CourseListing("CSC309H1", courselistingmap2);

		Set<CourseListing> semesterwinter = new HashSet<CourseListing>();
		semesterwinter.add(listing3);
		semesterwinter.add(listing4);

		SemesterConfiguration s = new SemesterConfiguration(semesterfall, semesterwinter);
		s.addPossibleTimetables1(generateTimetables());
		JSONObject semester = s.toJsonObject();
		String jsonString = semester.toString();
		//System.out.println(jsonString);
		JSONObject resJson = new JSONObject();
		JSONArray configurationsJson = new JSONArray();
		int i = 0;
		JSONObject possibleConfigJson = s.toJsonObject();
		possibleConfigJson.put("configurationNumber", i);
		configurationsJson.add(possibleConfigJson);
		i ++;
		resJson.put("configurations", configurationsJson);
		System.out.println(resJson);
	}

	private static List<Timetable> generateTimetables() {
		List<Timetable> timetables = new ArrayList<>();
		List<CourseOffering> courseOfferings = new ArrayList<>();
		Map<ClassType, List<ClassTime>> classTimes = new HashMap<>();
		List<ClassTime> classTimeList = new ArrayList<>();
		List<TimeSlot> timeSlots = new ArrayList<>();
		timeSlots.add(new TimeSlot(Day.MONDAY, 10000, 2000));
		timeSlots.add(new TimeSlot(Day.WEDNESDAY, 10000, 2000));
		timeSlots.add(new TimeSlot(Day.FRIDAY, 10000, 2000));
		ClassTime classTime = new ClassTime("CSC301", timeSlots);
		classTimeList.add(classTime);
		classTimes.put(ClassType.LEC, classTimeList);
		Course course1 = new Course("Test Course", "CSC301", "This is a test course", "CSC201", "CSC302", 3,
				CampusType.UTSG, SemesterType.FALL, classTimes, new ArrayList<>(Arrays.asList(5, 4)));
		Map<ClassType, ClassTime> courseTimes = new HashMap<>();
		courseTimes.put(ClassType.LEC, classTime);
		CourseOffering courseOffering = new CourseOffering(course1, courseTimes);
		courseOfferings.add(courseOffering);
		Timetable timetable = new Timetable(courseOfferings, 0);
		timetables.add(timetable);
		return timetables;
	}
}