package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    private Student Bob;
    private Course testCourse1;
    private Course testCourse2;

    @BeforeEach
    public void runBefore() {
         String studentName = "Bob";
         String studentID = "45467788";
        String testCourseID1 = "TEST102";
        String testCourseID2 = "TEST103";

         Bob = new Student(studentName,studentID);
        testCourse1 = new Course(testCourseID1);
        testCourse2 = new Course(testCourseID2);
    }

    @Test
    public void testAddCourseToSchedule() {
         assertEquals("The course has been added to the schedule successfully!",
                 Bob.addCourseToSchedule(testCourse1));
         assertTrue(testCourse1.isRegistrationStatus());
         assertEquals("Fail to add this course to the schedule since you have already registered it.",
                 Bob.addCourseToSchedule(testCourse1));
         assertFalse(Bob.addCourse(testCourse1));
         assertTrue(Bob.addCourse(testCourse2));
         Bob.addCourseToSchedule(testCourse2);
         assertTrue(testCourse2.isRegistrationStatus());

    }

    @Test
    public void testRemoveCourseFromSchedule() {
        Bob.addCourseToSchedule(testCourse1);
        Bob.addCourseToSchedule(testCourse2);
        assertEquals("The course has been removed to the schedule successfully!",
                Bob.removeCourseFromSchedule(testCourse1));
        assertFalse(testCourse1.isRegistrationStatus());
        assertTrue(testCourse2.isRegistrationStatus());
        assertEquals("Fail to delete this course since it is not in your schedule.",
                Bob.removeCourseFromSchedule(testCourse1));
        assertFalse(testCourse1.isRegistrationStatus());
    }

    @Test
    public void testGettersAndSetters() {
        Bob.setName("Alice");
        assertEquals("Alice", Bob.getName());
        Bob.setID("07123456");
        assertEquals("07123456",Bob.getID());
        Schedule schedule = new Schedule();
        Bob.addCourseToSchedule(testCourse1);
        Bob.addCourseToSchedule(testCourse2);
        schedule.addCourse(testCourse1);
        Bob.setSchedule(schedule);
        assertEquals(1,Bob.getSchedule().getCourses().size());
        assertEquals("TEST102",Bob.getSchedule().getCourses().get(0).getCourseIDForGrade());
    }

    @Test
    public void testStudentDetails() {
        Bob.addCourseToSchedule(testCourse1);
        Bob.addCourseToSchedule(testCourse2);
        Bob.getSchedule().setFormattedRegisterTime("12:00");
        JSONObject json = Bob.studentDetails();
        assertEquals("Bob",json.get("Name of the student"));
        assertEquals("45467788",json.get("StudentID"));
        assertEquals("12:00",json.get("Last edited time"));
        assertEquals(2,((JSONArray)json.get("Schedule")).size());
    }

}
