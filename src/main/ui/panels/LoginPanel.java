package ui.panels;

import model.User;
import ui.UserApp;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Represents a panel where you can log in with your student or TA number.
public class LoginPanel extends AppPanel {

    private JLabel title;
    private JLabel number;
    private JLabel name;
    private JTextField nameText;
    private JPasswordField password;
    private static final String FILE_NAME = "./data/wp2394208.jpg";

    public LoginPanel(UserApp app, List<User> users) {
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
    // EFFECTS: initializes all the login contents
    @Override
    protected void initializeContents() {
        addButtons();
        title = new JLabel("Login");
        title.setFont(new Font("Serif",Font.BOLD,50));
        title.setForeground(Color.white);
        number = new JLabel("Your Student/TA number:");
        number.setForeground(Color.white);
        name = new JLabel("Your Name:");
        name.setForeground(Color.white);
        name.setFont(new Font("Serif",Font.PLAIN,20));
        number.setFont(new Font("Serif",Font.PLAIN,20));
        nameText = new JTextField();
        nameText.setPreferredSize(new Dimension(350,50));
        nameText.setFont(new Font("Calibri",Font.PLAIN,40));
        password = new JPasswordField();
        password.setPreferredSize(new Dimension(350,50));
        password.setFont(new Font("Calibri",Font.PLAIN,40));

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
        add(name,bgc);
        bgc.gridx = 0;
        bgc.gridy = 2;
        add(nameText,bgc);
        bgc.gridx = 0;
        bgc.gridy = 3;
        add(number,bgc);
        bgc.gridx = 0;
        bgc.gridy = 4;
        add(password,bgc);
        addButtonsToPanel(bgc);
    }

    // MODIFIES: this
    // EFFECTS: helper method, adds buttons to this panel
    private void addButtonsToPanel(GridBagConstraints bgc) {
        bgc.anchor = GridBagConstraints.LAST_LINE_START;
        bgc.insets = new Insets(30,3,3,3);
        bgc.gridx = 0;
        bgc.gridy = 5;
        add(buttons.get(0),bgc);
    }


    // MODIFIES: this, app
    // EFFECTS: assigns according panel to each button
    @Override
    protected void initializeInteraction() {
        buttons.get(0).addActionListener(e -> {
            String passwordText = String.valueOf(password.getPassword());
            loginAction(passwordText);
        });

    }

    //EFFECTS: Using previously registered ID to login.
    public void loginAction(String id) {
        int flag = 0;
        for (User member : users) {
            if (member.getID().equals(id)) {
                if (member.getID().length() == 3) {
                    user = member;
                    JOptionPane.showMessageDialog(
                            this,
                            "Hello, TA " + user.getName() + " Welcome to use my application again!");
//                    new TeachingAssistantConsole(user,app);
                    flag = 1;
                } else {
                    user = member;
                    JOptionPane.showMessageDialog(
                            this,
                            "Hello, Student " + user.getName() + " Welcome to use my application again!");
                    flag = 1;
                }
            }
        }
        noAccountInSystem(flag);
    }

    //EFFECTS: if there is no matching id in the system, prints out the message to sign up an account.
    private void noAccountInSystem(int flag) {
        if (flag == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "No account in the system. "
                            + "Please sign up an account before using it. Thank you for your patience.");
        }
    }


    // EFFECTS: updates data display on the panel when active
    @Override
    public void updatePanel() {
        nameText.setText("");
        password.setText("");
    }

    //MODIFIES: this
    //EFFECTS; set the background picture of the LOGIN panel.

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon image = new ImageIcon(FILE_NAME);
        setBounds(0,0,1200,715);
        image.paintIcon(this,g,0,0);

    }
}
