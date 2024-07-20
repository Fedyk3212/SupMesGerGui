package NamelessDev.Graphics.panels;

import NamelessDev.Graphics.MainGraphics;

import javax.swing.*;
import java.awt.*;

public class PublicServerPanel extends JPanel {
    public PublicServerPanel() {
        makePublicServersPanel();
    }

    void makePublicServersPanel() {
        setLayout(new GridLayout());
        setBackground(MainGraphics.defaultPanelColor);
    }
}
