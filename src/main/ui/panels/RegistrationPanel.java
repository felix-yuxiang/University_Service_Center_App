package ui.panels;

import model.Student;
import model.TeachingAssistant;
import model.User;
import ui.UserApp;

import javax.swing.*;
import java.awt.*;
import java.util.List;


// Represents a registration panel where you either register as a TA or a student.
public class RegistrationPanel extends AppPanel {

    private JLabel title;
    private JLabel number;
    private JLabel name;
    private JTextField nameText;
    private JPasswordField password;
    private JRadioButton r1;
    private JRadioButton r2;
    private static final String FILE_NAME = "./data/wp2394204-anime-sky-wallpapers.jpg";

    public RegistrationPanel(UserApp app, List<User> users) {
        super(app, users);


        initializeContents();
        addToPanel();
        initializeInteraction();
    }


    //MODIFIES: this
    //EFFECTS: adds button to the login panel
    public void addButtons() {
        JButton forward = new JButton("Submit");
        buttons.add(forward);
    }

    // MODIFIES: this, app
    // EFFECTS: initializes all the registration contents
    @Override
    protected void initializeContents() {
        addButtons();
        title = new JLabel("Sign Up");
        title.setFont(new Font("Serif",Font.BOLD,50));
        number = new JLabel("Your Student/TA number:");
        name = new JLabel("Your Name:");
        name.setFont(new Font("Serif",Font.PLAIN,20));
        number.setFont(new Font("Serif",Font.PLAIN,20));
        nameText = new JTextField();
        nameText.setPreferredSize(new Dimension(350,50));
        nameText.setFont(new Font("Calibri",Font.PLAIN,40));
        password = new JPasswordField();
        password.setPreferredSize(new Dimension(350,50));
        password.setFont(new Font("Calibri",Font.PLAIN,40));
        r1 = new JRadioButton("Register as a student");
        r2 = new JRadioButton("Register as a teaching assistant");
        r1.setOpaque(false);
        r2.setOpaque(false);
        r1.setFont(new Font("Calibri",Font.BOLD,18));
        r2.setFont(new Font("Calibri",Font.BOLD,18));
    }


    // MODIFIES: this
    // EFFECTS: adds contents to this panel
    @Override
    protected void addToPanel() {
        GridBagConstraints bgc = new GridBagConstraints();
        bgc.anchor = GridBagConstraints.PAGE_START;
        bgc.insets = new Insets(10,3,3,3);
        bgc.gridx = 0;
        bgc.gridy = 0;
        add(title,bgc);
        bgc.anchor = GridBagConstraints.LINE_START;
        bgc.gridx = 0;
        bgc.gridy = 1;
        add(r1,bgc);
        bgc.gridx = 1;
        bgc.gridy = 1;
        add(r2,bgc);
        addButtonsToPanel(bgc);
    }

    // MODIFIES: this
    // EFFECTS: helper method, add buttons to panel
    private void addButtonsToPanel(GridBagConstraints bgc) {
        bgc.gridx = 0;
        bgc.gridy = 2;
        add(name,bgc);
        bgc.gridx = 0;
        bgc.gridy = 3;
        add(nameText,bgc);
        bgc.gridx = 0;
        bgc.gridy = 4;
        add(number,bgc);
        bgc.gridx = 0;
        bgc.gridy = 5;
        add(password,bgc);
        bgc.anchor = GridBagConstraints.LAST_LINE_START;
        bgc.insets = new Insets(30,3,3,3);
        bgc.gridx = 0;
        bgc.gridy = 6;
        add(buttons.get(0),bgc);
    }

    // MODIFIES: this, app
    // EFFECTS: assigns according panel to each button
    @Override
    protected void initializeInteraction() {
        buttons.get(0).addActionListener(e -> {
            if (r1.isSelected() && r2.isSelected()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Emm... You cannot be a student and a teacher at the same time!");
            } else {
                String name = nameText.getText();
                String number = String.valueOf(password.getPassword());
                registerAction(name, number);
            }
        });
    }

    //EFFECTS: Using previously registered ID to login.
    public void registerAction(String name, String id) {
        if (name.length() == 0 || id.length() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please key in the information!");
        } else {
            registerNewUser(name,id);
        }
    }

    //EFFECTS: if you choose to register as a student, a new student object is instantiated
    //         It happens the same for teaching assistant. Also the teaching assistant number has to be three digits!!
    public void registerNewUser(String name, String id) {
        if (!haveSignedUp(id)) {
            if (r1.isSelected()) {
                User student = new Student(name, id);
                users.add(student);
                user = student;
                JOptionPane.showMessageDialog(
                        this,
                        "You have registered as a student successfully!");
            } else if (id.length() == 3 && r2.isSelected()) {
                User ta = new TeachingAssistant(name, id);
                users.add(ta);
                user = ta;
                JOptionPane.showMessageDialog(
                        this,
                        "You have registered as a TA successfully!");
//                new TeachingAssistantConsole(ta,app);
            } else if (!(id.length() == 3) && r2.isSelected()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Teaching Assistant number has to be three digits.");
            }
        }
    }


    //Helper method
    //To check whether you have signed up before.
    //EFFECTS: go to login panel if you have previously signed up.
    public Boolean haveSignedUp(String id) {
        for (User member : users) {
            if (member.getID().equals(id)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Oops~~ It seems that you have signed up an account before.");
                user = member;
                return true;
            }
        }
        return false;
    }

    // EFFECTS: updates data display on the panel when active
    @Override
    public void updatePanel() {
        nameText.setText("");
        password.setText("");
    }

    //MODIFIES: this
    //EFFECTS; set the background picture of the REGISTRATION panel.

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon image = new ImageIcon(FILE_NAME);
        setBounds(0,0,1200,715);
        image.paintIcon(this,g,0,0);

    }

}
