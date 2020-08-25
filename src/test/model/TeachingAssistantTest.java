package model;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TeachingAssistantTest {
    private String name = "Bob";
    private String taID = "007";
    private TeachingAssistant ta;

    @BeforeEach
    public void runBefore() {
        ta = new TeachingAssistant(name, taID);
    }

    @Test
    public void testName() {
        assertEquals("Bob", ta.getName());
        ta.setName("Jane");
        assertEquals("Jane",ta.getName());
    }

    @Test
    public void testID() {
        assertEquals("007", ta.getID());
        ta.setID("009");
        assertEquals("009",ta.getID());
    }

    @Test
    public void testTeachingAssistantDetails() {
        JSONObject obj = ta.teachingAssistantDetails();
        assertEquals("Bob",obj.get("Name of the teaching assistant"));
        assertEquals("007",obj.get("Teaching assistant ID"));
    }
}
