package persistence;

import model.Student;
import model.TeachingAssistant;
import model.User;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

// Represents a class that writes data to a JSON file
// Reference: https://howtodoinjava.com/library/json-simple-read-write-json-examples/
//            TellerApp (https://github.students.cs.ubc.ca/CPSC210/TellerApp.git)
//           BCS-Degree Navigator (https://github.com/def-not-ys/BCS-Degree-Navigator)
public class Writer {

    FileWriter file;


    //EFFECTS: construct a file with given location.
    public Writer(String location) throws IOException {
        file = new FileWriter(location);
    }

    // MODIFIES: this
    // EFFECTS: writes the users into JSON file
    @SuppressWarnings("unchecked")
    public void write(List<User> users) throws IOException {

        JSONObject allUsers = new JSONObject();
        for (User user: users) {
            if (user.getID().length() == 3) {
                JSONObject data = ((TeachingAssistant) user).teachingAssistantDetails();
                allUsers.put(user.getID(), data);
            } else {
                JSONObject data = ((Student) user).studentDetails();
                allUsers.put(user.getID(), data);
            }

        }

        file.write(allUsers.toJSONString());
        file.flush();
    }

    // MODIFIES: this
    // EFFECTS: close the print write
    // NOTE: MUST be called when done writing
    public void close() throws IOException {
        file.close();
    }
}
