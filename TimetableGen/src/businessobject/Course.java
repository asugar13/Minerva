package businessobject

public class Course {
    String name;
    String code;
    CampusType campus;
    SemesterType semester;
    TimeSlot time;
    
    public Course (String name, String code, CampusType campus, SemesterType semester, TimeSlot time) {
        this.name = name;
        this.code = code;
        this.campus = campus;
        this.semster = semester;
        this.time = time;
    }
    
    // Getters
    
    public String getName() {
        return this.name;
    }
    public String getCode) {
        return this.code;
    }
    public CampusType getCampusType() {
        return this.campus;
    }
    public SemesterType getSemesterType() {
        return this.semester;
    }
    public TimeSlot getTimeSlot() {
        return this.time;
    }
    
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setCampus(CampusType campusType) {
        this.campus = campusType;
    }
    public void setSemester(SemesterType semesterType) {
        this.semester = semesterType;
    }
    public void setTimeSlot(TimeSlot time) {
        this.time = time;
    }
    
}
