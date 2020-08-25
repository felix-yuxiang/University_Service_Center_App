package persistence;

import model.Student;
import model.TeachingAssistant;
import model.User;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {
    Writer writer;
    Student student;
    TeachingAssistant ta;
    List<User> users;
    String location = "./data/writer_test.json";


    @BeforeEach
    public void runBefore() throws IOException {
        student = new Student("Tom","12345678");
        ta = new TeachingAssistant("Eric","123");
        writer = new Writer(location);
        users = new LinkedList<>();
        users.add(student);
        users.add(ta);
    }

    @Test
    public void testWriteUser() {
        try {
            writer.write(users);
            writer.close();
        } catch (IOException e) {
            fail("not expected IOException");
        }

        try {
            Reader reader = new Reader(location);
            List<User> readUser = reader.read();
            assertEquals(2, readUser.size());
            assertEquals("Tom",readUser.get(1).getName());
            assertEquals("Eric",readUser.get(0).getName());
        } catch (IOException e) {
            fail("reader fail to read file written");
        } catch (ParseException e) {
            fail("reader fail to parse content");
        }
    }

    @Test
    public void testWriteUsersLots() {
        Student s1 = new Student("s1", "11111111");
        Student s2 = new Student("s2",  "22222222");

        users.add(s1);
        users.add(s2);

        try {
            writer.write(users);
            writer.close();
        } catch (IOException e) {
            fail("not expected IOException");
        }

        try {
            Reader reader = new Reader(location);
            List<User> readUser = reader.read();
            assertEquals(4, readUser.size());
            assertEquals("Tom",readUser.get(2).getName());
            assertEquals("Eric",readUser.get(1).getName());
            assertEquals("s1",readUser.get(0).getName());
            assertEquals("s2",readUser.get(3).getName());
        } catch (IOException e) {
            fail("reader fail to read file written");
        } catch (ParseException e) {
            fail("reader fail to parse content");
        }
    }
}
