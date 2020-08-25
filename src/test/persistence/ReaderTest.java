package persistence;

import model.*;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest {
    User student;
    User teachingAssistant;
    Writer writer;
    Reader reader;
    List<User> users;
    String location = "./data/reader_test.json";

    @BeforeEach
    public void runBefore() {
        users = new LinkedList<>();
        student = new Student("Risgy","33445566");
        teachingAssistant = new TeachingAssistant("Jerry","123");
    }

    @Test
    public void testReaderTwo() {
        try {
            users.add(student);
            users.add(teachingAssistant);
            writer = new Writer(location);
            writer.write(users);
            writer.close();
        } catch (IOException e) {
            fail("write fail to write users");
        }

        try {
            reader = new Reader(location);
            users = reader.read();
            assertEquals(2, users.size());
            assertEquals("123",users.get(0).getID());
            assertEquals("33445566",users.get(1).getID());
        } catch (IOException e) {
            fail("reader IO exception error");
        } catch (ParseException e) {
            fail("reader parse exception error");
        }
    }

    @Test
    public void testReaderLots() {
        Student s1 = new Student("Bob","12561256");
        Student s2 = new Student("Luna", "10101010");
        Assignment task = new Assignment("Final Exam", 0.3, "Bob");
        Assignment task1 = new Assignment("MIDTERM", 0.3, "Bob");
        Course math100 = new Course("MATH100");
        Course soci100 = new Course("SOCI100");
        math100.getAssignments().add(task);
        math100.getAssignments().add(task1);
        s1.addCourseToSchedule(math100);
        s1.addCourseToSchedule(soci100);

        users.add(student);
        users.add(teachingAssistant);
        users.add(s1);
        users.add(s2);

        try {
            writer = new Writer(location);
            writer.write(users);
        } catch (IOException e) {
            fail("write fail to write users");
        }

        try {
            reader = new Reader(location);
            users = reader.read();
            assertEquals(4, users.size());
            assertEquals("12561256",users.get(3).getID());
            assertEquals("33445566",users.get(2).getID());
            assertEquals("Jerry",users.get(1).getName());
            assertEquals("Luna",users.get(0).getName());


            assertEquals(2, ((Student)users.get(3)).getSchedule().getCourses().size());
            assertEquals(2, ((Student)users.get(3)).getSchedule().getCourses().get(0).getAssignments().size());

        } catch (IOException e) {
            fail("reader IOE exception error");
        } catch (ParseException e) {
            fail("reader parse exception error");
        }
    }

    @Test
    public void testIOException() {
        try {
            reader = new Reader("file does not exist");
        } catch (FileNotFoundException e) {
            // expected
        }
    }
}
