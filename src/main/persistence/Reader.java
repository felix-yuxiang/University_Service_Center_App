package persistence;

import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


// Represents a class that reads data from a JSON file
// Reference: https://howtodoinjava.com/library/json-simple-read-write-json-examples/
//            TellerApp (https://github.students.cs.ubc.ca/CPSC210/TellerApp.git)
//            BCS-Degree Navigator (https://github.com/def-not-ys/BCS-Degree-Navigator)
public class Reader {

    FileReader file;
    User student;
    User teachingAssistant;


    // EFFECTS: reads file from a certain location.
    public Reader(String fn) throws FileNotFoundException {
        file = new FileReader(fn);
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: returns the user list constructed from reading the JSON file
    @SuppressWarnings("unchecked")
    public List<User> read() throws IOException, ParseException {
        List<User> users = new LinkedList<>();

        JSONParser jsonParser = new JSONParser();
        JSONObject data = (JSONObject) jsonParser.parse(file);

        HashMap<String, JSONObject> tempMap = new HashMap(data);

        for (String str: tempMap.keySet()) {
            if (str.length() == 3) {
                teachingAssistant = parseTeachingAssistant(tempMap.get(str));
                users.add(teachingAssistant);
            } else {
                student = parseStudent(tempMap.get(str));
                users.add(student);
            }
        }
        return users;
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: returns the teaching assistant constructed by parsing the JSON file
    private User parseTeachingAssistant(JSONObject data) {

        String name = (String) data.get("Name of the teaching assistant");
        String id = (String) data.get("Teaching assistant ID");
        teachingAssistant = new TeachingAssistant(name,id);

        return teachingAssistant;
    }


    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: returns the student constructed by parsing the JSON file
    private User parseStudent(JSONObject data) {
        String name = (String) data.get("Name of the student");
        String id = (String) data.get("StudentID");
        String time = (String) data.get("Last edited time");
        JSONArray rawCourses = (JSONArray) data.get("Schedule");
        student = new Student(name,id);
        ((Student) student).getSchedule().setFormattedRegisterTime(time);
        for (Object k: rawCourses) {
            parseCourse(((JSONObject) k));
        }
        return student;

    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: parse the list of registered course from the JSON file and adds to the student's schedule.
    private void parseCourse(JSONObject data) {
        String id = (String) data.get("courseID");
        int finalGrade = ((Number)data.get("Final grade")).intValue();
        boolean gradingStatus = (boolean) data.get("Grading status");
        JSONArray rawAssignments = (JSONArray) data.get("Assignments");
        Course course = new Course(id);
        course.setGradeFinal(finalGrade);
        course.setGradingFinalStatus(gradingStatus);
        ((Student) student).addCourseToSchedule(course);
        for (Object k: rawAssignments) {
            parseAssignment(((JSONObject) k), course);
        }
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: parse the list of taken course from the JSON file and adds to the student
    private void parseAssignment(JSONObject assignment, Course course) {
        String component = (String) assignment.get("Component");
        double weight = ((Number) assignment.get("Weight")).doubleValue();
        boolean gradingStatus = (boolean) assignment.get("Grading Status");
        double grade = ((Number) assignment.get("Grade")).doubleValue();
        String feedback = (String) assignment.get("Feedback");
        String taname = (String) assignment.get("Teaching assistant");
        Assignment task = new Assignment(component,weight,taname);
        task.setGrade(grade);
        task.setGradingStatus(gradingStatus);
        task.giveFeedback(feedback);
        course.getAssignments().add(task);
    }
}




