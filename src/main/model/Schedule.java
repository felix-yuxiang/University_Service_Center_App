package model;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Class schedule incorporates a list of registered courses and the time when you complete editing.
public class Schedule {
    private List<Course> courses;
    private String formattedRegisterTime;

    //Construct a schedule with the given name and the time when you
    //finish choosing the course for this schedule.
    public Schedule() {
        courses = new ArrayList<>();
        formattedRegisterTime = "";
    }

    //MODIFIES: this
    //EFFECTS: add course to this schedule
    public void addCourse(Course course) {
        courses.add(course);
    }


    //MODIFIES: this
    //EFFECTS: If the course you want to delete is in the schedule,
    //         remove that course from the schedule and return true.
    //         Otherwise, return false.
    public boolean removeCourse(Course course) {
        return courses.remove(course);
    }

    //EFFECTS: return the courses in the schedule.
    public List<Course> getCourses() {
        return courses;
    }

    //MODIFIES: this
    //EFFECTS: select courses and put them in schedule.
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    //EFFECTS: get the time you complete registration.
    public String getFormattedRegisterTime() {
        return formattedRegisterTime;
    }

    //MODIFIES: this
    //EFFECTS: set the time of to when you finish course registration.
    public void setFormattedRegisterTime(String formattedRegisterTime) {
        this.formattedRegisterTime = formattedRegisterTime;
    }

    //MODIFIES: JSON object
    //EFFECTS: add list of course with detail to json array
    @SuppressWarnings("unchecked")
    public JSONArray extractCourses() {
        JSONArray listOfCourses = new JSONArray();
        for (Course c: courses) {
            listOfCourses.add(c.courseDetails());
        }
        return listOfCourses;
    }
}
