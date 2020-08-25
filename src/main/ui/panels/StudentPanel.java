package ui.panels;

import model.Course;
import model.Student;
import model.User;
import ui.UserApp;
import ui.data.CoursePath;
import ui.data.CourseReader;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//References: BCS-Degree Navigator (https://github.com/def-not-ys/BCS-Degree-Navigator)
// Represents a student panel where all the functions have been implemented if you log in as a student.
public class StudentPanel extends AppPanel implements ListSelectionListener {

    private HashMap<String, Course> courseDataSet;
    private HashMap<String, HashMap<String, Course>>  coursesByFaculty;
    private static final String[] FACULTY_LIST = "COMM,CPSC,ECON,MATH,PSYC,STAT".split(",");

    private JScrollPane facultyPane;
    private JScrollPane coursePane;
    private JList<String> facultyList;
    private JList<Course> courseList;

    private JTextField textField = new JTextField();
    private JTextArea textArea = new JTextArea();
    private JButton addBtn = new JButton("Add to my schedule");
    private JButton search = new JButton("Search");

    private JLabel facultyListTitles = new JLabel("Choose from faculty:");
    private JLabel courseListTitles = new JLabel("Choose course:");
    private JLabel searchLabel = new JLabel("Search course id in format xxxx123 (e.g. cpsc210) :");


    private Course selectedCourse;

    private HashMap<String, Course> currList;
    private List<String> faculties;
    private KeyListener keyInput;
    private ListSelectionListener selectCourse;
    private static final String FILE_NAME = "./data/wp2394333.jpg";

    private GridBagConstraints gbc = new GridBagConstraints();

    public StudentPanel(UserApp app, User user) {
        super(app, user);

        faculties = Arrays.asList(FACULTY_LIST);
        currList = new HashMap<>();

        try {
            constructCourseList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initializeInteraction();
        initializeLists();
        initializeContents();

        addToPanel();
    }

    // MODIFIES: this
    // EFFECTS: construct the faculty course category
    private void constructCourseList() throws IOException {
        courseDataSet = new HashMap<>();
        coursesByFaculty = new HashMap<>();
        for (String faculty : FACULTY_LIST) {
            CoursePath dataPath = new CoursePath();
            String path = dataPath.findPath(faculty);
            HashMap<String, Course> tempCourses;
            tempCourses = constructFacultyCourses(path);
            courseDataSet.putAll(tempCourses);
            coursesByFaculty.put(faculty, tempCourses);
        }
    }

    // REQUIRES: course must be in available faculties
    // EFFECTS: returns the course of the given course id
    public Course findCourse(String courseid) {
        return getCourseDataSet().get(courseid);
    }

    // EFFECTS: returns the course of the given course id
    public HashMap<String, Course> getCourseDataSet() {
        return courseDataSet;
    }

    // EFFECTS: returns the course list of the given faculty
    public HashMap<String, Course> getCoursesByFaculty(String fn) {
        return coursesByFaculty.get(fn);
    }


    // REQUIRES: Faculty is one of COMM ,CPSC ,ECON ,MATH ,PSYC ,STAT
    // MODIFIES: this
    // EFFECTS: returns the list of courses offered by this faculty
    private HashMap<String, Course> constructFacultyCourses(String path) throws IOException {
        CourseReader courseReader = new CourseReader(path);
        return courseReader.getCourses();
    }



    // MODIFIES: this
    // EFFECTS: initializes faculty list and course list
    private void initializeLists() {

        DefaultListModel<String> fl = new DefaultListModel<>();
        facultyList = new JList<>();
        facultyList.setModel(fl);

        for (int i = 0; i < faculties.size(); i++) {
            fl.addElement(faculties.get(i));
        }

        facultyList.addListSelectionListener(this);
        courseList = new JList<>();
//        updateCoursePane();
        courseList.addListSelectionListener(selectCourse);
    }

    // MODIFIES: this
    // EFFECTS: update the course list pane based on current faculty selection
    private void updateCoursePane() {
        DefaultListModel<Course> cl = new DefaultListModel<>();

        for (String key: currList.keySet()) {
            cl.addElement(currList.get(key));
        }

        try {
            courseList.setModel(cl);
        } catch (NullPointerException exp) {
            // can't figure out why it's throwing exception
        }
    }

    // EFFECTS: returns valid course id from raw input
    private Course getCourseIdFromInput(String input) {
        String courseId = "";
        Course c;
        try {
            String fn = input.substring(0, 4).toUpperCase();
            if (faculties.contains(fn)) {
                courseId = fn + input.substring(4, 7);
            } else {
                textArea.setText("I don't recognize this course id. Please try again.");
            }
        } catch (StringIndexOutOfBoundsException e) {
            textArea.setText("I don't recognize this input. Please try again.");
        }
        try {
            c = findCourse(courseId);
            return c;
        } catch (NullPointerException e) {
            textArea.setText("Oops.. This course does not exist at the moment.");
        }
        return null;
    }

    // EFFECTS: processes course id from user input and updates course detail
    private void processInput() {
        String rawInput = textField.getText();
        selectedCourse = getCourseIdFromInput(rawInput);
        if (selectedCourse == null) {
            textArea.setText("Oops.. This course does not exist at the moment.");
        } else {
            textArea.setText(selectedCourse.courseToText());
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
        add(facultyListTitles, gbc);

        gbc.gridy = 1;
        gbc.gridheight = 3;
        add(facultyPane,gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds course list pane to the panel
    private void addCoursePane() {
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        add(courseListTitles, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.gridheight = 3;
        add(coursePane, gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds search pane to the panel
    private void addSearchPane() {
        gbc.insets = new Insets(10,5,5,5);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        add(searchLabel,gbc);

        gbc.gridy = 2;
        add(textField, gbc);

        gbc.insets = new Insets(15,5,10,5);
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        add(textArea, gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to the panel
    private void addButtons() {
        gbc.gridx = 3;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(15,5,10,5);
        gbc.gridy = 0;
        add(search, gbc);
        gbc.gridy = 1;
        add(addBtn, gbc);
    }

    // MODIFIES: this
    // EFFECTS: initializes button interactions
    private void initButtons() {
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processInput();
            }
        });
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedCourse != null && user != null) {
                    Course currentCourse = new Course(selectedCourse.getCourseID());
                    if (((Student)user).addCourse(currentCourse)) {
                        textArea.setText(selectedCourse.getCourseID() + " is now added to your list!");
                    } else {
                        textArea.setText(selectedCourse.getCourseID() + " is already in your list.");
                    }
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initialized list interactions
    private void initList() {
        selectCourse = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedCourse = courseList.getSelectedValue();
                textArea.setText(selectedCourse.courseToText());
            }
        };
    }

    // MODIFIES: this
    // EFFECTS: initialized key input interactions
    private void initKeyInput() {
        keyInput = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    processInput();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
    }

    // MODIFIES: this, app
    // EFFECTS: initializes all contents and components
    @Override
    protected void initializeContents() {
        facultyPane = new JScrollPane(facultyList);

        coursePane = new JScrollPane(courseList);
        coursePane.setPreferredSize(new Dimension(100,427));
        facultyListTitles.setFont(new Font("Serif",Font.BOLD,17));
        facultyListTitles.setForeground(Color.white);
        courseListTitles.setFont(new Font("Serif",Font.BOLD,17));
        courseListTitles.setForeground(Color.white);
        searchLabel.setFont(new Font("Serif",Font.BOLD,17));
        searchLabel.setForeground(Color.white);


        textField.setPreferredSize(new Dimension(400,20));
        textField.addKeyListener(keyInput);

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
        addSearchPane();
        addButtons();
    }

    // MODIFIES: this, app
    // EFFECTS: assigns according panel to each button
    @Override
    protected void initializeInteraction() {
        initList();
        initKeyInput();
        initButtons();

    }

    // EFFECTS: updates data display on the panel when active
    @Override
    public void updatePanel() {
        textField.setText("");
        if (user != null) {
            ((Student)user).getSchedule().setFormattedRegisterTime(UserApp.showLocalTime());
        }

    }

    //MODIFIES: this
    //EFFECTS: when you select a faculty, you can retrieve a list of courses of that faculty.
    //          when you select another one, the list get updated!
    @Override
    public void valueChanged(ListSelectionEvent e) {

        String fc = facultyList.getSelectedValue();
        currList = getCoursesByFaculty(fc);

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
