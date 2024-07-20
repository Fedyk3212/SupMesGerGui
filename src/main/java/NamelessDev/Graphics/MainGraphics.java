package NamelessDev.Graphics;

import NamelessDev.Graphics.tabbedpanes.MainTabbedPane;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainGraphics extends JFrame {
    static Color defaultColor = new Color(0x636363);
    public static Color defaultPanelColor = new Color(0x404040);
    public MainGraphics() throws IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super("Open_Chat_Made_by_Nameless_Dev");
        setPlaf();
        getContentPane().add(new MainTabbedPane());
    }

    public static void initMainGraphics() throws IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        MainGraphics frame = new MainGraphics();
        constructMainFrame(frame);

    }

    static void constructMainFrame(JFrame frame) {
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(defaultColor);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    static void setPlaf() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    }

}