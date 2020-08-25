package model;

import exceptions.FacultyNotFoundException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.data.CoursePath;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CourseTest {
    Course testCourse;
    String testCourseName = "A university verified course.";
    String courseDescription = "A template for course description.";
    String testFaculty = "facultyA";
    String testCourseID = "TEST101";
    String testPreReq = "NO prerequisites required";
    Course testCourse1;
    String testCourseName1 = "A university verified course1.";
    String courseDescription1 = "A template for course description.";
    String testFaculty1 = "facultyB";
    String testCourseID1 = "TEST102";
    String testPreReq1 = "TEST101";
    Course testCourse2;
    String testCourseName2 = "A university verified course2.";
    String courseDescription2 = "A template for course description.";
    String testFaculty2 = "facultyC";
    String testCourseID2 = "TEST103";
    String testPreReq2 = "TEST101 AND TEST102";
    Course testCourseInSchedule;
    Course testCourseInSchedule1;
    Course testCourseInSchedule2;


    @BeforeEach
    public void runBefore() {
        testCourse = new Course(testCourseName, courseDescription,testFaculty,testCourseID,testPreReq);
        testCourse1 = new Course(testCourseName1, courseDescription1,testFaculty1,testCourseID1,testPreReq1);
        testCourse2 = new Course(testCourseName2, courseDescription2,testFaculty2,testCourseID2,testPreReq2);
        testCourseInSchedule = new Course(testCourseID);
        testCourseInSchedule1 = new Course(testCourseID1);
        testCourseInSchedule2 = new Course(testCourseID2);
    }

    @Test
    public void testCourseGettersAndSetters() {
        assertEquals("A university verified course.",testCourse.getCourseName());
        assertEquals("A university verified course1.", testCourse1.getCourseName());
        assertEquals("A university verified course2.",testCourse2.getCourseName());
        testCourse1.setCourseName("Intermediate German");
        assertEquals("Intermediate German", testCourse1.getCourseName());
        assertEquals("A template for course description.",testCourse.getCourseDescription());
        testCourse1.setCourseDescription("Learn intermediate German");
        assertEquals("Learn intermediate German", testCourse1.getCourseDescription());
        assertEquals("facultyA", testCourse.getFaculty());
        assertEquals("facultyB", testCourse1.getFaculty());
        assertEquals("facultyC", testCourse2.getFaculty());
        testCourse1.setFaculty("GERM");
        assertEquals("GERM",testCourse1.getFaculty());
        assertEquals("TEST101", testCourse.getCourseID());
        assertEquals("TEST102",testCourse1.getCourseID());
        assertEquals("TEST103",testCourse2.getCourseID());
        testCourse1.setCourseID("GERM200");
        assertEquals("GERM200", testCourse1.getCourseID());
        assertEquals("NO prerequisites required", testCourse.getPreReq());
        assertEquals("TEST101", testCourse1.getPreReq());
        assertEquals("TEST101 AND TEST102", testCourse2.getPreReq());
        testCourse1.setPreReq("NO prerequisites required");
        assertEquals("NO prerequisites required", testCourse1.getPreReq());
        assertFalse(testCourseInSchedule.isGradingFinalStatus());
        testCourseInSchedule.setGradingFinalStatus(true);
        assertTrue(testCourseInSchedule.isGradingFinalStatus());
        testCourseInSchedule.setGradeFinal(70);
        assertEquals(70,testCourseInSchedule.getGradeFinal());
        assertFalse(testCourseInSchedule.isRegistrationStatus());
        testCourseInSchedule.setRegistrationStatus(true);
        assertTrue(testCourseInSchedule.isRegistrationStatus());
        testCourseInSchedule.setCourseIDForGrade("TEST195");
        assertEquals("TEST195", testCourseInSchedule.getCourseIDForGrade());

    }

    @Test
    public void testPrintGradeStatus() {
        testCourseInSchedule2.setGradingFinalStatus(true);
        testCourseInSchedule2.setGradeFinal(65);
        assertEquals("Grading in process, please be patient for your final grade :).\n",
                testCourseInSchedule.printGradeStatus());
        assertEquals("Your final grade is available!\n"
                + "Your total percentage grade for TEST103 is 65%.\n"
                + "You can check the grading detail in assignments.", testCourseInSchedule2.printGradeStatus());
    }

    @Test
    public void testViewAssignments() {
        Assignment task1;
        Assignment task2;
        List<Assignment> assignments;
        task1 = new Assignment("midterm1", 0.1,"Beren");
        task2 = new Assignment("midterm2", 0.15,"Jerry");
        assignments = new ArrayList<>();
        assignments.add(task1);
        assignments.add(task2);
        testCourseInSchedule.setAssignments(assignments);
        assertEquals(2,testCourseInSchedule.getAssignments().size());
        assertEquals("midterm1",testCourseInSchedule.getAssignments().get(0).getComponent());
        assignments.remove(task1);
        testCourseInSchedule.setAssignments(assignments);
        assertEquals(1, testCourseInSchedule.getAssignments().size());
        assertEquals(0.15,testCourseInSchedule.getAssignments().get(0).getWeight());
        assertEquals("midterm2", testCourseInSchedule.getAssignments().get(0).getComponent());
    }

    @Test
    public void testCalculateFinalGrade() {
        Assignment task1;
        Assignment task2;
        Assignment task3;
        List<Assignment> assignments;
        task1 = new Assignment("midterm1", 0.2,"Beren");
        task2 = new Assignment("midterm2", 0.3,"Jerry");
        task3 = new Assignment("Final",0.5, "Felix");
        assignments = new ArrayList<>();
        task1.setGrade(70);
        task2.setGrade(87);
        task3.setGrade(96);
        assignments.add(task1);
        assignments.add(task2);
        assignments.add(task3);
        testCourseInSchedule.setAssignments(assignments);
        testCourseInSchedule.calculateFinalGrade();
        assertEquals(88,testCourseInSchedule.getGradeFinal());
        assertTrue(testCourseInSchedule.isGradingFinalStatus());
    }

    @Test
    public void testCourseDetails() {
        testCourseInSchedule.setGradingFinalStatus(true);
        testCourseInSchedule.setGradeFinal(96);
        Assignment task1;
        Assignment task2;
        Assignment task3;
        List<Assignment> assignments;
        task1 = new Assignment("midterm1", 0.1,"Beren");
        task2 = new Assignment("midterm2", 0.3,"Jerry");
        task3 = new Assignment("Final",0.5, "Felix");
        assignments = new ArrayList<>();
        task1.setGrade(70);
        task2.setGrade(87);
        task3.setGrade(96);
        assignments.add(task1);
        assignments.add(task2);
        assignments.add(task3);
        testCourseInSchedule.setAssignments(assignments);
        JSONObject obj = testCourseInSchedule.courseDetails();
        assertEquals(96,obj.get("Final grade"));
        assertEquals(true,obj.get("Grading status"));
        assertEquals("TEST101",obj.get("courseID"));
        assertEquals(3,((JSONArray)obj.get("Assignments")).size());
    }

    @Test
    public void testCourseToText() {
        assertEquals("Course ID: TEST101\n"
        + "Course name: A university verified course.\n"
       + "Course description: A template for course description.",testCourse.courseToText());
        assertEquals("TEST101",testCourse.toString());
        assertEquals("TEST101",testCourseInSchedule.toString());
        assertFalse(testCourse.equals(null));
        assertFalse(testCourse.equals(testCourseName));
        assertEquals(31,testCourse.hashCode());
    }

    @Test
    public void testFacultyNotFoundException() {
        CoursePath path = new CoursePath();
        assertEquals("error",path.findPath("EOSC"));
        try {
            path.findFacultyDataPath("SOCI");
            fail("An exception should be thrown!!!");
        } catch (FacultyNotFoundException e) {
            //all good
        }
    }


}
