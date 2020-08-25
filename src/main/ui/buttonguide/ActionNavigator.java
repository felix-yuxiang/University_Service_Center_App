package ui.buttonguide;

import ui.UserApp;
import ui.panels.AppPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a class to help you move from one panel to another.
public class ActionNavigator implements ActionListener {
    private UserApp app;
    private AppPanel thisPanel;
    private AppPanel nextPanel;

    public ActionNavigator(UserApp app, AppPanel thisPanel, AppPanel nextPanel) {
        this.app = app;
        this.thisPanel = thisPanel;
        this.nextPanel = nextPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        nextPanel.updatePanel();
        nextPanel.setVisible(true);
        app.setContentPane(nextPanel);
        thisPanel.setVisible(false);
        app.validate();
    }


}
