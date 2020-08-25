package ui.panels;

import exceptions.NegativeValueException;
import exceptions.OutGradeBoundException;
import exceptions.OutWeightBoundException;
import model.Assignment;
import model.Course;
import model.Student;
import model.User;
import ui.UserApp;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

//Represents a teaching assistant user interface with all the functions which a ta could invoke.
// Transform it from console-based to GUI.
public class TeachingAssistantPanel extends AppPanel implements ListSelectionListener {
    private static final String FILE_NAME = "./data/sky.jpg";


    private JScrollPane studentPane;
    private JScrollPane coursePane;
    private JScrollPane assignmentPane;
    private JList<String> studentList;
    private JList<Course> courseList;
    private JList<Assignment> assignmentList;

    private JTextArea textArea = new JTextArea();
    private JButton createBtn = new JButton("Create Assignment");
    private JButton removeBtn = new JButton("Remove Assignment");
    private JButton gradeBtn = new JButton("Grade Assignment");

    private JLabel studentListTitles = new JLabel("Choose from students' ID:");
    private JLabel courseRegisterTitles = new JLabel("Choose from registered courses:");
    private JLabel assignmentTitles = new JLabel("Choose from assignments:");
    private JLabel infoLabel = new JLabel("Assignment Description");


    private Course selectedCourse;
    private Assignment selectedAssignment;

    private Student currStudent;
    private ListSelectionListener selectCourse;
    private ListSelectionListener selectAssignment;

    private GridBagConstraints gbc = new GridBagConstraints();

    public TeachingAssistantPanel(UserApp app, User user) {
        super(app, user);

        initializeInteraction();
        initializeLists();
        initializeContents();

        addToPanel();
    }



    // MODIFIES: this
    // EFFECTS: initializes student list, course list, and the assignment list.
    private void initializeLists() {
        boolean flag = true;

        DefaultListModel<String> fl = new DefaultListModel<>();
        studentList = new JList<>();
        studentList.setModel(fl);

        for (User user: users) {
            if (user.getID().length() != 3) {
                fl.addElement(user.getID());
                flag = false;
            }
        }
        if (flag) {
            textArea.setText("No students have registered on this application!\n "
                    + "You will be notified for further instructions from instructor.");
        }

        studentList.addListSelectionListener(this);
        courseList = new JList<>();
        courseList.addListSelectionListener(selectCourse);
        assignmentList = new JList<>();
        assignmentList.addListSelectionListener(selectAssignment);
    }

    // MODIFIES: this
    // EFFECTS: update the course list pane based on current faculty selection
    private void updateCoursePane() {
        DefaultListModel<Course> cl = new DefaultListModel<>();

        for (Course course: currStudent.getSchedule().getCourses()) {
            cl.addElement(course);
        }

        try {
            courseList.setModel(cl);
        } catch (NullPointerException exp) {
            // can't figure out why it's throwing exception
        }
    }


    // MODIFIES: this
    // EFFECTS: adds faculty list pane to the panel
    private void addFacultyPane() {
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,5,0,5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        add(studentListTitles, gbc);

        gbc.gridy = 1;
        gbc.gridheight = 3;
        add(studentPane,gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds course list pane to the panel
    private void addCoursePane() {
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        add(courseRegisterTitles, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.gridheight = 3;
        add(coursePane, gbc);
    }

    //MODIFIES: this
    //EFFECTS: add assignment pane to the panel
    private void addAssignmentPane() {
        gbc.insets = new Insets(10,5,5,5);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        add(assignmentTitles,gbc);



        gbc.insets = new Insets(15,5,10,5);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        add(assignmentPane, gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds info pane to the panel
    private void addInfoPane() {
        gbc.insets = new Insets(10,5,5,5);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        add(infoLabel,gbc);



        gbc.insets = new Insets(15,5,10,5);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        add(textArea, gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to the panel
    private void addButtons() {
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0,15,10,5);
        gbc.gridx = 0;
        add(createBtn, gbc);
        gbc.gridx = 1;
        add(removeBtn, gbc);
        gbc.gridx = 2;
        add(gradeBtn,gbc);
    }

    // MODIFIES: this
    // EFFECTS: initializes button interactions
    private void initButtons() {
        createBtn.addActionListener(e -> createAssignment());

        removeBtn.addActionListener(e -> {
            if (selectedAssignment != null) {
                selectedCourse.getAssignments().remove(selectedAssignment);
                textArea.setText("The assignment \"" + selectedAssignment.getComponent()
                        + "\" has been removed successfully!");
                updateAssignmentPane();
            }
        });

        gradeBtn.addActionListener(e -> gradeAssignment());
    }

    //MODIFIES: Assignment
    //EFFECTS: grade an assignment, you can put grade and feedback in it.
    public void gradeAssignment() {
        if (selectedAssignment != null) {
            JTextField grade = new JTextField();
            JTextField feedback = new JTextField();
            Object[] message = {
                    "Grade (0 - 100) : ", grade,
                    "Feedback : ", feedback
            };
            int option = JOptionPane.showConfirmDialog(this, message,
                    "Creating assignment", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                keepGradingAssignment(grade, feedback);
            }
        }
    }

    // a helper method.
    private void keepGradingAssignment(JTextField grade, JTextField feedback) {
        try {
            double gradeInDoubleForm = Double.parseDouble(grade.getText());
            selectedAssignment.gradeAssignment(gradeInDoubleForm,feedback.getText());
        } catch (NegativeValueException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Negative value is not acceptable for the grade of the assignment.");
        } catch (OutGradeBoundException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "The grade should not be more than 100.0.");
        }
        textArea.setText("You have finished grading this assignment! Yay~~");
        updateAssignmentPane();
    }

    // MODIFIES: Assignment
    // EFFECTS: create an assignment for a student in a given course.
    private void createAssignment() {
        if (selectedCourse != null) {
            JTextField component = new JTextField();
            JTextField weight = new JTextField();
            Object[] message = {
                    "Component : ", component,
                    "Weight (0 - 1) : ", weight
            };
            int option = JOptionPane.showConfirmDialog(this, message,
                    "Creating assignment", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                keepCreatingAssignment(component, weight);
            }
        }
    }

    // A helper method.
    private void keepCreatingAssignment(JTextField component, JTextField weight) {
        Assignment assignment = new Assignment("",0,"");
        try {
            double weightInDoubleForm = Double.parseDouble(weight.getText());
            assignment.createAssignment(component.getText(),weightInDoubleForm,user.getName());
        } catch (NegativeValueException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Negative value is not acceptable for the weight of the assignment.");
        } catch (OutWeightBoundException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "An assignment should not weigh more than 1.");
        }
        selectedCourse.getAssignments().add(assignment);
        updateAssignmentPane();
    }

    // MODIFIES: this
    // EFFECTS: update the assignment list pane based on current course selection

    private void updateAssignmentPane() {
        selectedCourse = courseList.getSelectedValue();
        DefaultListModel<Assignment> al = new DefaultListModel<>();

        for (Assignment assignment: selectedCourse.getAssignments()) {
            al.addElement(assignment);
        }

        try {
            assignmentList.setModel(al);
        } catch (NullPointerException exp) {
            // can't figure out why it's throwing exception
        }
    }


    // MODIFIES: this
    // EFFECTS: initialized list interactions
    private void initList() {
        selectCourse = e -> {
            selectedCourse = courseList.getSelectedValue();
            DefaultListModel<Assignment> al = new DefaultListModel<>();

            for (Assignment assignment: selectedCourse.getAssignments()) {
                al.addElement(assignment);
            }

            try {
                assignmentList.setModel(al);
            } catch (NullPointerException exp) {
                // can't figure out why it's throwing exception
            }
        };

        selectAssignment = e -> {
            selectedAssignment = assignmentList.getSelectedValue();
            textArea.setText(assignmentToText(selectedAssignment));
        };

    }

    // EFFECTS: print out the assignment info
    private String assignmentToText(Assignment assignment) {
        String str = "";
        str = str + "Component: " + assignment.getComponent();
        str = str + "\n\nWeight: " + assignment.getWeight();
        if (!assignment.isGradingStatus()) {
            str = str + "\n\nYou are available to grade this assignment right now.";
        } else {
            str = str + "\n\nGrade: " + assignment.getGrade();
            str = str + "\n\nFeedback: " + assignment.getFeedback() + "\nCreated by: "
                    + assignment.getTeachingAssistantName();
        }

        return str;
    }


    // MODIFIES: this, app
    // EFFECTS: initializes all contents and components
    @Override
    protected void initializeContents() {
        studentPane = new JScrollPane(studentList);
        studentPane.setPreferredSize(new Dimension(90,427));
        assignmentPane = new JScrollPane(assignmentList);
        assignmentPane.setPreferredSize(new Dimension(90,427));
        coursePane = new JScrollPane(courseList);
        coursePane.setPreferredSize(new Dimension(90,427));
        studentListTitles.setFont(new Font("Serif",Font.BOLD,17));
        studentListTitles.setForeground(Color.white);
        courseRegisterTitles.setFont(new Font("Serif",Font.BOLD,17));
        courseRegisterTitles.setForeground(Color.white);
        assignmentTitles.setFont(new Font("Serif",Font.BOLD,17));
        assignmentTitles.setForeground(Color.white);
        infoLabel.setFont(new Font("Serif",Font.BOLD,17));
        infoLabel.setForeground(Color.white);


        textArea.setPreferredSize(new Dimension(400,400));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
    }


    // MODIFIES: this
    // EFFECTS: adds contents to this panel
    @Override
    protected void addToPanel() {
        addFacultyPane();
        addCoursePane();
        addAssignmentPane();
        addInfoPane();
        addButtons();
    }

    // MODIFIES: this, app
    // EFFECTS: assigns according panel to each button
    @Override
    protected void initializeInteraction() {
        initList();
        initButtons();

    }

    // EFFECTS: updates data display on the panel when active
    @Override
    public void updatePanel() {
        textArea.setText("");

    }

    //MODIFIES: this
    //EFFECTS: when you select a faculty, you can retrieve a list of courses of that faculty.
    //          when you select another one, the list get updated!
    @Override
    public void valueChanged(ListSelectionEvent e) {

        String id = studentList.getSelectedValue();
        for (User user: users) {
            if (user.getID().equals(id)) {
                currStudent = (Student) user;
            }
        }

        updateCoursePane();
    }



    //MODIFIES: this
    //EFFECTS; set the background picture of the student panel.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon image = new ImageIcon(FILE_NAME);
        setBounds(0,0,1200,715);
        image.paintIcon(this,g,0,0);

    }
}
