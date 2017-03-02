package businessobject;

public class TimeSlot {
    String day;
    Integer start;
    Integer end;
    Integer duration;
    boolean alternative;
    
    public TimeSlot(String day, String time, boolean alternative) {
        this.day = day;
        if (time.contains("-")) {
            this.start = Integer.parseInt(time);
            this.end = (this.start + 1) % 12;
            this.duration = 1;
        } else {
            String[] split = time.split("-");
            this.start = Integer.parseInt(split[0]);
            this.end = Integer.parseInt(split[1]);
            this.duration = (this.end - this.start + 12) % 12;
        }
        this.alternative = alternative;
    }
    
    public boolean conflictsWith(TimeSlot timeslots){
        return true;
    }
    //TODO: Getters and Setters

}
