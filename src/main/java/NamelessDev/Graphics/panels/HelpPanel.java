package NamelessDev.Graphics.panels;

import NamelessDev.Graphics.MainGraphics;
import NamelessDev.localisation.Localisation;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class HelpPanel extends JPanel {

    public HelpPanel() throws IOException {
        makeHelpPanel();
    }
    void makeHelpPanel() throws IOException {
        setBackground(MainGraphics.defaultPanelColor);
        JLabel helpPanelDescription = new JLabel(Localisation.getLocalisedName("HelpPanelDesc"));
        helpPanelDescription.setFont(new Font("Arial", Font.BOLD, 14));
        helpPanelDescription.setForeground(new Color(0xFFFFFF));
        add(helpPanelDescription);
    }
}
