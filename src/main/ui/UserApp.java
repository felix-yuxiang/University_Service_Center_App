package ui;

import model.User;
import org.json.simple.parser.ParseException;
import persistence.Reader;
import persistence.Writer;
import ui.panels.AppPanel;
import ui.panels.WelcomePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

//Represents a hybrid of GUI and console-based user interface system.
public class UserApp extends JFrame {

    private List<User> users;
    private AppPanel homepage;
    public static final String USER_FILE = "./data/user_data.json";

    static final Dimension FRAME_DIMENSION = new Dimension(1200,715);

    public UserApp() {
        super("University Service Center");

        loadUsers();

        setLayout(new GridBagLayout());
        setPreferredSize(FRAME_DIMENSION);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        homepage = new WelcomePanel(this, users);



        add(homepage);
        addWindowListener(new SaveData());
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);

    }


    //EFFECTS: returns all the users in the program.
    public List<User> getUsers() {
        return users;
    }

    // MODIFIES: this
    // EFFECTS: load the existing users list from data, if not data exists, instantiate new user list
    public void loadUsers() {
        try {
            Reader reader = new Reader(USER_FILE);
            users = reader.read();
        } catch (FileNotFoundException e) {
            users = new LinkedList<>();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: save users data to local file
    public void save() {
        try {
            Writer writer = new Writer(USER_FILE);
            writer.write(users);
            writer.close();
            JOptionPane.showMessageDialog(
                    UserApp.this,
                    "Your information has been successfully saved!","Save",JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            // save failure
            e.printStackTrace();
        }
    }

    // EFFECTS: clear users data in local file
    public void clear() {
        try {
            Writer writer = new Writer(USER_FILE);
            users = new LinkedList<>();
            writer.write(users);
            writer.close();
            JOptionPane.showMessageDialog(
                    UserApp.this,
                    "You have cleared the existing data successfully!","Clear",JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            // save failure
            e.printStackTrace();
        }
    }

    // EFFECTS: end the program and auto-save the existing users
    public void end() {
        try {
            Writer writer = new Writer(USER_FILE);
            writer.write(users);
            writer.close();
            JOptionPane.showMessageDialog(
                    UserApp.this,
                    "Goodbye! I wish nothing but the best for you.","EMC",JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        } catch (IOException e) {
            // save failure
            e.printStackTrace();
        }
    }


    // Reference: https://stackoverflow.com/questions/15198549/popup-for-jframe-close-button
    //EFFECTS: generate a popup window conforming to save the data or not when you close the window.
    private class SaveData extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            int option = JOptionPane.showOptionDialog(
                    UserApp.this,
                    "Would you like to save the data after you exit?",
                    "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, null, null);
            if (option == JOptionPane.YES_OPTION) {
                save();
                System.out.println("save data!");
            } else {
                System.out.println("not saved!");
            }
            System.exit(0);
        }
    }


    //Using java library about local time.
    //EFFECTS: get the local time.
    public static String showLocalTime() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return myDateObj.format(myFormatObj);
    }


}
