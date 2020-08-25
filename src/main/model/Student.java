package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

// A student with fields such as name, student number and exclusive schedule.
public class Student implements User {
    private String studentName;
    private String studentID;
    private Schedule schedule;



    //Construct a student with student name and student ID.
    public Student(String studentName, String studentID) {
        this.studentName = studentName;
        this.studentID = studentID;
        schedule = new Schedule();
    }

    //MODIFIES: this
    //EFFECTS: add a course to the given schedule if the course is not in schedule.
    //        otherwise, we cannot add this course.
    public String addCourseToSchedule(Course course) {
        if (schedule.getCourses().contains(course)) {
            return "Fail to add this course to the schedule since you have already registered it.";
        }
        schedule.addCourse(course);
        course.setRegistrationStatus(true);
        return "The course has been added to the schedule successfully!";
    }

    //MODIFIES: this
    //EFFECTS: add a course to the given schedule if the course is not in schedule.
    //        otherwise, we cannot add this course. Return a boolean accordingly!
    public boolean addCourse(Course course) {
        if (schedule.getCourses().contains(course)) {
            return false;
        }
        schedule.addCourse(course);
        course.setRegistrationStatus(true);
        return true;
    }

    //REQUIRES: Lists of schedule and courses are not empty.
    //MODIFIES: this
    //EFFECTS: delete given course in the given schedule.
    public String removeCourseFromSchedule(Course course) {
        if (schedule.removeCourse(course)) {
            course.setRegistrationStatus(false);
            return "The course has been removed to the schedule successfully!";
        }
        return "Fail to delete this course since it is not in your schedule.";
    }

    //MODIFIES: json object.
    //EFFECTS: return a JSON object with all the student detail to write in.
    @SuppressWarnings("unchecked")
    public JSONObject studentDetails() {
        JSONObject student = new JSONObject();

        student.put("Name of the student", studentName);
        student.put("StudentID", studentID);

        student.put("Last edited time", schedule.getFormattedRegisterTime());
        student.put("Schedule", schedule.extractCourses());

        return student;
    }



    //EFFECTS: return the name of this student
    @Override
    public String getName() {
        return studentName;
    }

    //MODIFIES: this
    //EFFECTS: set the name of this student
    @Override
    public void setName(String studentName) {
        this.studentName = studentName;
    }

    //EFFECTS: return the student's ID
    @Override
    public String getID() {
        return studentID;
    }

    //MODIFIES: this
    //EFFECTS: set the student's ID
    @Override
    public void setID(String studentID) {
        this.studentID = studentID;
    }

    //EFFECTS: return the schedule
    public Schedule getSchedule() {
        return schedule;
    }

    //MODIFIES: this
    //EFFECTS: set the schedule of this student.
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }



}
