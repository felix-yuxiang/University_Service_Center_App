package ui.panels;

import model.Assignment;
import model.Course;
import model.Student;
import model.User;
import ui.UserApp;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Represents a panel where you can view your schedule and remove your course.
public class SchedulePanel extends AppPanel {
    private JScrollPane coursePane;
    private JTextArea textArea = new JTextArea();
    private JList<Course> courseList;

    private JLabel userStatus = new JLabel();
    private JButton registerBtn = new JButton("Registered courses");

    private JButton rmvBtn = new JButton("Remove from my schedule");

    private List<Course> currList;

    private Course selectedCourse;

    private GridBagConstraints gbc = new GridBagConstraints();

    public SchedulePanel(UserApp app, User user) {

        super(app, user);

        setBackground(Color.pink);

        currList = new ArrayList<>();
        userStatus.setFont(new Font("Calibri",Font.PLAIN,25));
        initializeLists();
        initializeInteraction();
        initializeContents();

        addToPanel();
    }

    // MODIFIES: this
    // EFFECTS: initialize lists on this panel
    private void initializeLists() {
        courseList = new JList<>();

        updateCoursePane();
    }

    // MODIFIES: this
    // EFFECTS: updates courses in the course pane
    private void updateCoursePane() {
        updateStatus();

        DefaultListModel<Course> model = new DefaultListModel();
        model.removeAllElements();

        for (Course course: currList) {
            model.addElement(course);
        }

        try {
            courseList.setModel(model);
        } catch (NullPointerException e) {
            // can't figure out why it's throwing exception
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the label showing students' status
    private void updateStatus() {
        if (user == null) {
            userStatus.setText("Nothing has been instantiated!");
        } else {
            int completed = ((Student)user).getSchedule().getCourses().size();
            userStatus.setText("You have registered " + completed + " courses."
                    + "    Last edited time: " + ((Student)user).getSchedule().getFormattedRegisterTime());
        }

    }

    // MODIFIES: this
    // EFFECTS: adds status label on this panel
    private void addStatus() {
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,10,10,10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        add(userStatus,gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds option buttons on this panel
    private void addOptionButtons() {
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(registerBtn, gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds course pane on this panel
    private void addCoursePane() {

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 3;
        add(coursePane, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 2;

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 1;
        gbc.gridheight = 3;
        add(textArea, gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds operation buttons on this panel
    private void addOperationButtons() {
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(rmvBtn, gbc);
    }

    // MODIFIES: this
    // EFFECTS: initializes options button interactions
    private void initOptionButtons() {
        registerBtn.addActionListener(e -> {
            if (user != null) {
                currList = ((Student) user).getSchedule().getCourses();
                updateCoursePane();
            }

        });

    }

    // MODIFIES: this
    // EFFECTS: initializes operation button interactions
    private void initRmvButtons() {
        rmvBtn.addActionListener(e -> {
            if (user != null) {
                if (selectedCourse != null) {
                    ((Student) user).getSchedule().removeCourse(selectedCourse);
                    textArea.setText(selectedCourse.getCourseIDForGrade() + " is removed from your list!");
                    updateStatus();
                    updateCoursePane();
                }
            }

        });
    }

    // MODIFIES: this
    // EFFECTS: initializes list interactions
    private void initListInteraction() {
        ListSelectionListener selection = e -> {
            selectedCourse = courseList.getSelectedValue();
            textArea.setText(selectedCourseText(selectedCourse));
        };
        courseList.addListSelectionListener(selection);
    }


    //EFFECTS: return the assignment status and final grading status.
    private String selectedCourseText(Course selectedCourse) {
        selectedCourse.calculateFinalGrade();
        String str = selectedCourse.printGradeStatus();
        if (selectedCourse.getAssignments().isEmpty()) {
            rmvBtn.setEnabled(true);
            str = str + "\nAssignments have not been posted.";
        } else {
            rmvBtn.setEnabled(false);
            for (Assignment assignment : selectedCourse.getAssignments()) {
                str = str + "\nComponent: " + assignment.getComponent();
                str = str + "\nWeight: " + assignment.getWeight();
                str = str + "\n" + assignment.printGradingStatus();
            }

        }
        return str;
    }


    // MODIFIES: this, app
    // EFFECTS: initializes all the displaying contents related to student's schedule
    @Override
    protected void initializeContents() {
        updateStatus();
        coursePane = new JScrollPane(courseList);
        coursePane.setPreferredSize(new Dimension(150,500));

        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension(300,450));
    }

    // MODIFIES: this
    // EFFECTS: adds contents to this panel
    @Override
    protected void addToPanel() {
        addStatus();
        addOptionButtons();
        addCoursePane();
        addOperationButtons();
    }


    // MODIFIES: this, app
    // EFFECTS: assigns according panel to each button
    @Override
    protected void initializeInteraction() {
        initOptionButtons();
        initListInteraction();
        initRmvButtons();
    }

    // EFFECTS: updates data display on the panel when active
    @Override
    public void updatePanel() {
        currList = new ArrayList<>();
        textArea.setText("");
        updateCoursePane();
    }
}
