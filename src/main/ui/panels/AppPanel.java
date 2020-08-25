package ui.panels;

import model.User;
import ui.UserApp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


//General app panel. It sets some of the configuration of a panel.
public abstract class AppPanel extends JPanel {

    protected UserApp app;

    protected List<JButton> buttons;
    protected List<AppPanel> panels;

    static User user;
    protected List<User> users;

    static final Dimension PANEL_DIMENSION = new Dimension(1200,715);

    public AppPanel(UserApp app, User user) {
        super(new GridBagLayout());
        AppPanel.user = user;
        this.users = app.getUsers();

        this.app = app;
        this.buttons = new ArrayList<>();
        this.panels = new ArrayList<>();

        setPreferredSize(PANEL_DIMENSION);
        setAlignmentX(Component.RIGHT_ALIGNMENT);
        setAlignmentY(Component.TOP_ALIGNMENT);
        setVisible(true);
    }

    // Overload a constructor.
    public AppPanel(UserApp app, List<User> users) {
        super(new GridBagLayout());
        this.users = users;

        this.app = app;
        this.buttons = new ArrayList<>();
        this.panels = new ArrayList<>();

        setPreferredSize(PANEL_DIMENSION);
        setAlignmentX(Component.RIGHT_ALIGNMENT);
        setAlignmentY(Component.TOP_ALIGNMENT);
        setVisible(true);
    }

    // MODIFIES: this, app
    // EFFECTS: initializes contents
    protected abstract void initializeContents();

    // MODIFIES: this
    // EFFECTS: adds contents to this panel
    protected abstract void addToPanel();

    // MODIFIES: this, app
    // EFFECTS: assigns according panel to each button
    protected abstract void initializeInteraction();

    // EFFECTS: updates data display on the panel when active
    public abstract void updatePanel();
}
