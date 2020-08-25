package model;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

// The course consists of name, faculty, course ID, a list of prerequisites, detailed
// descriptions and grade.
public class Course {
    private String courseName; // "Software Construction"
    private String faculty; // "CPSC"
    private String courseID; //"CPSC210"
    private String courseIDForGrade;//"CPSC210"
    private String courseDescription;
    // "Design, development, and analysis of robust software components.
    // Topics such as software design, computational models, data structures, debugging, and testing."
    private String preReq; // CPSC110/CPSC107

    private List<Assignment> assignments; // Project, midterms, final, assignments.
    private boolean gradingFinalStatus;
    private boolean registrationStatus;
    private int gradeFinal;

    //Construct a course with all the fields
    public Course(String courseName, String description, String faculty, String courseID,String preReq) {
        this.courseName = courseName;
        courseDescription = description;
        this.faculty = faculty;
        this.courseID = courseID;
        this.preReq = preReq;
    }

    //Construct another course with the same courseID that includes all the assignments and grades
    public Course(String courseIDForGrade) {
        this.courseIDForGrade = courseIDForGrade;
        assignments = new ArrayList<>();
        gradingFinalStatus = false;
        registrationStatus = false;
        gradeFinal = 0;
    }



    //MODIFIES: this
    //EFFECTS: calculate the final score of this course
    public void calculateFinalGrade() {
        double totalGrade = 0;
        double totalWeight = 0;

        for (Assignment task: assignments) {
            totalWeight += task.getWeight();
            totalGrade += task.getGrade() * task.getWeight();
            if (totalWeight == 1) {
                int value = (int)totalGrade;
                gradingFinalStatus = true;
                gradeFinal = value;
            }
        }
    }



    //EFFECTS: Returns the status of the grade
    //         If the grading status is true, total percentage grade will be printed.
    //          Otherwise, the grading is current in process.
    public String printGradeStatus() {
        String string;
        if (!gradingFinalStatus) {
            string = "Grading in process, please be patient for your final grade :).\n";
            return string;
        }
        string = "Your final grade is available!\n"
                + "Your total percentage grade for "
                + courseIDForGrade + " is " + gradeFinal + "%.\n"
                + "You can check the grading detail in assignments.";
        return string;
    }

    //EFFECTS: get the name of the course.
    public String getCourseName() {
        return courseName;
    }

    //MODIFIES: this
    //EFFECTS: set the name of the course.
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    //EFFECTS: Return the faculty which offers this course.
    public String getFaculty() {
        return faculty;
    }

    //MODIFIES: this
    //EFFECTS: set the faculty of the course.
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    //EFFECTS: Get the full id of the course.
    public String getCourseID() {
        return courseID;
    }

    //MODIFIES: this
    //EFFECTS: set the full id of the course.
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    //EFFECTS: get the description of the course.
    public String getCourseDescription() {
        return courseDescription;
    }

    //MODIFIES: this
    //EFFECTS: set the description of the course.
    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    //EFFECTS: return the prerequisites of this course
    public String getPreReq() {
        return preReq;
    }

    //MODIFIES: this
    //EFFECTS: set the prerequisites of this course
    public void setPreReq(String preReq) {
        this.preReq = preReq;
    }

    //EFFECTS: get the list of assignments in this course
    public List<Assignment> getAssignments() {
        return assignments;
    }

    //MODIFIES: this
    //EFFECTS: distribute assignments to this course.
    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    //EFFECTS: return the grading status.
    public boolean isGradingFinalStatus() {
        return gradingFinalStatus;
    }

    //MODIFIES: this
    //EFFECTS: set the grading status.
    public void setGradingFinalStatus(boolean gradingFinalStatus) {
        this.gradingFinalStatus = gradingFinalStatus;
    }

    //EFFECTS: get the final grade of this course.
    public int getGradeFinal() {
        return gradeFinal;
    }

    //MODIFIES: this
    //EFFECTS: set the final grade of this course.
    public void setGradeFinal(int gradeFinal) {
        this.gradeFinal = gradeFinal;
    }

    //EFFECTS: return the registration status of this course
    public boolean isRegistrationStatus() {
        return registrationStatus;
    }

    //MODIFIES: this
    //EFFECTS: set the registration status of the course.
    public void setRegistrationStatus(boolean registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    //EFFECTS: return the courseID of course in schedule.
    public String getCourseIDForGrade() {
        return courseIDForGrade;
    }

    //MODIFIES: this
    //EFFECTS: set the course ID for this course in schedule.
    public void setCourseIDForGrade(String courseIDForGrade) {
        this.courseIDForGrade = courseIDForGrade;
    }

    // EFFECTS: returns course detail in formatted string
    public String courseToText() {
        String ids = "Course ID: " + courseID + "\nCourse name: " + courseName;

        return ids +  "\nCourse description: " + courseDescription;
    }

    //MODIFIES: json object.
    //EFFECTS: return a JSON object with all the course detail to write in.
    @SuppressWarnings("unchecked")
    public JSONObject courseDetails() {
        JSONObject course = new JSONObject();

        course.put("courseID", courseIDForGrade);
        course.put("Final grade", gradeFinal);
        course.put("Grading status", gradingFinalStatus);
        course.put("Assignments", this.extractAssignments());

        return course;
    }

    //MODIFIES: JSON object
    //EFFECTS: add list of assignment with detail to json array
    @SuppressWarnings("unchecked")
    private JSONArray extractAssignments() {
        JSONArray tasks = new JSONArray();
        for (Assignment assignment: assignments) {
            tasks.add(assignment.assignmentDetails());
        }
        return tasks;
    }

    @Override
    public String toString() {
        if (this.courseDescription == null) {
            return courseIDForGrade;
        }
        return courseID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course) o;
        return courseIDForGrade.equals(course.courseIDForGrade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseIDForGrade);
    }
}