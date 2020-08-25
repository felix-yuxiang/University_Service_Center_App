package ui.panels;

import model.Student;
import model.User;
import ui.UserApp;
import ui.buttonguide.ActionNavigator;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Represents a main panel. It will guide you to other panels.
public class WelcomePanel extends AppPanel {
    private UserApp app;
    private List<JButton> home;
    private JLabel message;
    private ActionNavigator actionNavigator;
    private GridBagConstraints bgc;
    private JButton musicButton;
    private Clip clip;

    static final String FILE_NAME = "./data/UBC-Clocktower-and-IKB-Learning-Centre.jpg";
    static final String PICTURE = "./data/music.jpg";
    static final String MUSIC = "./data/Wolfgang-Amadeus-Mozart.wav";


    public WelcomePanel(UserApp app, List<User> users) {
        super(app,users);
        this.app = app;
        this.users = users;
        this.home = new ArrayList<>();

        setAlignmentX(Component.RIGHT_ALIGNMENT);
        setAlignmentY(Component.TOP_ALIGNMENT);
        setPreferredSize(new Dimension(1200,715));
        setVisible(true);


        playSound(MUSIC);
        initializeContents();
        initializeInteraction();
        addToPanel();

    }


    // MODIFIES: this
    // EFFECTS: initializes and adds buttons to lists
    protected void initializeContents() {

        JButton btn0 = new JButton("Save and Exit");
        buttons.add(btn0);
        JButton btn1 = new JButton("Sign Up");
        buttons.add(btn1);
        JButton btn2 = new JButton("Login");
        buttons.add(btn2);
        JButton btn3 = new JButton("Save");
        buttons.add(btn3);
        JButton btn4 = new JButton("Clear Existing Data");
        buttons.add(btn4);
        JButton btn5 = new JButton("Browse Courses");
        buttons.add(btn5);
        JButton btn6 = new JButton("View Schedule");
        buttons.add(btn6);
        ImageIcon music = new ImageIcon(PICTURE);
        musicButton = new JButton(music);
        message = new JLabel("Hello! Nice to meet you.");
        message.setFont(new Font("Serif", Font.BOLD,30));
        message.setForeground(Color.white);


        initializePanels();
        initializeHomeButtons();

    }

    // MODIFIES: this
    // EFFECTS: initializes panels
    private void initializePanels() {
        panels.add(new RegistrationPanel(app, users));
        panels.add(new LoginPanel(app, users));
        panels.add(new StudentPanel(app, user));
        panels.add(new SchedulePanel(app, user));
        panels.add(new TeachingAssistantPanel(app,user));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(3,3,10,3);
        gbc.gridx = 3;
        gbc.gridy = 6;

        for (AppPanel p: panels) {
            JButton back = new JButton("Back");
            home.add(back);
            p.add(back, gbc);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes back buttons on all sub panels
    private void initializeHomeButtons() {
        for (int i = 0; i < panels.size(); i++) {
            home.get(i).addActionListener(new ActionNavigator(app, panels.get(i), this));
        }
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to this pane
    protected void addToPanel() {
        bgc = new GridBagConstraints();
        bgc.gridy = 0;
        this.add(message,bgc);


        bgc.insets = new Insets(260,550,3,550);
        bgc.gridy = 1;
        this.add(musicButton,bgc);
        int y = 2;
        bgc.insets = new Insets(20,550,3,550);
        for (JButton btn: buttons) {
            bgc.gridy = y;
            this.add(btn,bgc);
            y++;
        }
    }

    //MODIFIES: this
    //EFFECTS: initialize part of the panels.
    @Override
    protected void initializeInteraction() {
        buttons.get(0).addActionListener(e -> app.end());
        buttons.get(1).addActionListener(new ActionNavigator(app,this,panels.get(0)));
        buttons.get(2).addActionListener(new ActionNavigator(app,this,panels.get(1)));
        buttons.get(3).addActionListener(e -> app.save());
        buttons.get(4).addActionListener(e -> app.clear());
        actionNavigator = new ActionNavigator(app,this,panels.get(2));
        buttons.get(5).addActionListener(actionNavigator);
        buttons.get(6).addActionListener(new ActionNavigator(app, this, panels.get(3)));
        musicButton.addActionListener(e -> controlMusic());
    }

    //MODIFIES: Clip
    //EFFECTS: when the music icon is clicked, the music will be played
    //         if the music is playing, then it will stop.
    private void controlMusic() {
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            } else {
                clip.start();
            }
        }
    }


    // EFFECTS: updates data display on the panel when active
    @Override
    public void updatePanel() {
        if (user == null) {
            message.setText("Try to sign up or login to build up your exclusive schedule :)");
        } else {
            if (user instanceof Student) {
                message.setText("Hello, student " + user.getName() + " ! Login time: " + UserApp.showLocalTime());
                if (buttons.get(5).getText().equals("Teaching Assistant Menu")) {
                    buttons.get(5).setText("Browse Courses");
                    buttons.get(5).removeActionListener(actionNavigator);
                    actionNavigator = new ActionNavigator(app, this, panels.get(2));
                    buttons.get(5).addActionListener(actionNavigator);
                    this.add(buttons.get(6),bgc);
                }
            } else {
                message.setText("Hello, teaching assistant "
                        + user.getName() + " ! Login time: " + UserApp.showLocalTime());
                buttons.get(5).setText("Teaching Assistant Menu");
                buttons.get(5).removeActionListener(actionNavigator);
                actionNavigator = new ActionNavigator(app, this, panels.get(4));
                buttons.get(5).addActionListener(actionNavigator);
                this.remove(buttons.get(6));
            }

        }

    }

    //MODIFIES: this
    //EFFECTS; set the background picture of the welcome panel.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon image = new ImageIcon(FILE_NAME);
        setBounds(0,0,1200,715);
        image.paintIcon(this,g,0,0);

    }


    //EFFECTS: play the background music continuously from data file
    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

}
