package model;

import org.json.simple.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
public class ScheduleTest {
    private Schedule schedule;
    private List<Course> courses;
    private Course testCourse1;
    private Course testCourse2;


    @BeforeEach
    public void runBefore() {
        String testCourseID1 = "TEST102";

        testCourse1 = new Course(testCourseID1);
        String testCourseID2 = "TEST103";
        testCourse2 = new Course(testCourseID2);
        courses = new ArrayList<>();
        courses.add(testCourse1);
        courses.add(testCourse2);
        schedule = new Schedule();
    }

    @Test
    public void testScheduleGettersAndSetters() {
        schedule.setCourses(courses);
        schedule.setFormattedRegisterTime("12:00");
        assertEquals("12:00", schedule.getFormattedRegisterTime());
        assertEquals("TEST102", schedule.getCourses().get(0).getCourseIDForGrade());
        assertEquals("TEST103", schedule.getCourses().get(1).getCourseIDForGrade());
        courses.remove(testCourse1);
        schedule.setCourses(courses);
        assertEquals("TEST103",schedule.getCourses().get(0).getCourseIDForGrade());
    }

    @Test
    public void testAddCourse() {
        schedule.addCourse(testCourse1);
        assertEquals("TEST102", schedule.getCourses().get(0).getCourseIDForGrade());
        schedule.addCourse(testCourse2);
        assertEquals("TEST103", schedule.getCourses().get(1).getCourseIDForGrade());
    }

    @Test
    public void testRemoveCourse() {
        schedule.addCourse(testCourse1);
        schedule.addCourse(testCourse2);
        assertTrue(schedule.removeCourse(testCourse1));
        assertEquals("TEST103", schedule.getCourses().get(0).getCourseIDForGrade());
        assertFalse(schedule.removeCourse(testCourse1));
        assertEquals(1, schedule.getCourses().size());
    }

    @Test
    public void testExtractCourses() {
        schedule.addCourse(testCourse1);
        schedule.addCourse(testCourse2);
        JSONArray arr = schedule.extractCourses();
        assertEquals(2,arr.size());
    }


}
