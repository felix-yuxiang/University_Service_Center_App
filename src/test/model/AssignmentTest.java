package model;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.NegativeValueException;
import exceptions.OutGradeBoundException;
import exceptions.OutWeightBoundException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;





class AssignmentTest {
    private Assignment task;

    @BeforeEach
    public void beforeRun() {
        task = new Assignment("Final Exam", 0.3, "Bob");
    }

    @Test
    public void testPrintGradeStatus() {
        assertFalse(task.isGradingStatus());
        assertEquals("Your assignment grading is in process, please wait patiently.",
                task.printGradingStatus());
        task.setGradingStatus(true);
        task.setGrade(96);
        task.giveFeedback("Good job!");
        assertTrue(task.isGradingStatus());
        assertEquals("Your assignment grade is 96.0%\n" +
                "Your assignment is graded by Bob.\n Here is my feedback: Good job!" +
                "\n Feel free to contact me if you have any issue regarding to this assignment!",
                task.printGradingStatus());
        assertEquals("Good job!", task.getFeedback());

    }

    @Test
    public void testAssignmentGettersAndSetters () {
        assertEquals("Final Exam", task.getComponent());
        task.setComponent("Midterm 1");
        assertEquals("Midterm 1", task.getComponent());
        task.setGrade(85.5);
        assertEquals(85.5, task.getGrade());
        assertEquals("Bob", task.getTeachingAssistantName());
        task.setTeachingAssistantName("Felix");
        assertEquals("Felix", task.getTeachingAssistantName());
        assertEquals(0.3, task.getWeight());
        task.setWeight(0.15);
        assertEquals(0.15, task.getWeight());
    }

    @Test
    public void testCreateAssignmentNoException() {
        try {
            task.createAssignment("Quiz", 0.5, "Eliza");
        } catch (NegativeValueException e) {
            fail("Unexpected negative value exception was raised.");
        } catch (OutWeightBoundException e) {
            fail("Unexpected out of weight bound exception was raised.");
        }
        assertEquals(0.5,task.getWeight());
    }

    @Test
    public void testCreateAssignmentNegativeValueException() {
        try {
            task.createAssignment("Quiz", -0.5, "Eliza");
            fail("No exception was thrown.");
        } catch (NegativeValueException e) {
            // All good.
        } catch (OutWeightBoundException e) {
            fail("Unexpected out of weight bound exception was raised.");
        }
    }

    @Test
    public void testCreateAssignmentOutWeightBoundException() {
        try {
            task.createAssignment("Quiz", 12.3, "Eliza");
            fail("No exception was thrown.");
        } catch (NegativeValueException e) {
            fail("Unexpected negative value exception was raised.");
        } catch (OutWeightBoundException e) {
            // All good.
        }
    }

    @Test
    public void testGradeAssignmentNoException() {
        try {
            task.gradeAssignment(75, "You can do better in the future!");
        } catch (NegativeValueException e) {
            fail("Unexpected negative value exception was raised.");
        } catch (OutGradeBoundException e) {
            fail("Unexpected out of grade bound exception was raised.");
        }
        assertEquals(75,task.getGrade());
        assertEquals("You can do better in the future!",task.getFeedback());
    }

    @Test
    public void testGradeAssignmentNegativeValueException() {
        try {
            task.gradeAssignment(-10, "You can do better in the future!");
            fail("No exception was thrown.");
        } catch (NegativeValueException e) {
            //All good.
        } catch (OutGradeBoundException e) {
            fail("Unexpected out of grade bound exception was raised.");
        }
    }

    @Test
    public void testGradeAssignmentOutGradeBoundException() {
        try {
            task.gradeAssignment(123, "You can do better in the future!");
            fail("No exception was thrown.");
        } catch (NegativeValueException e) {
            fail("Unexpected negative value exception was raised.");
        } catch (OutGradeBoundException e) {
            //All good.
        }
    }

    @Test
    public void testAssignmentDetails() {
        try {
            task.gradeAssignment(93.2,"Nice work!");
        } catch (NegativeValueException e) {
            fail("Unexpected negative value exception was raised.");
        } catch (OutGradeBoundException e) {
            fail("Unexpected out of weight bound exception was raised.");
        }
        JSONObject json = task.assignmentDetails();
        assertEquals("Final Exam",json.get("Component"));
        assertEquals(0.3,json.get("Weight"));
        assertEquals("Bob",json.get("Teaching assistant"));
        assertEquals("Nice work!",json.get("Feedback"));
        assertEquals(93.2,json.get("Grade"));
        assertEquals(true,json.get("Grading Status"));
    }
}