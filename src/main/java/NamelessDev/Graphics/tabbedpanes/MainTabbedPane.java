package NamelessDev.Graphics.tabbedpanes;

import NamelessDev.Graphics.panels.ChatRoomPanel;
import NamelessDev.Graphics.panels.HelpPanel;
import NamelessDev.Graphics.panels.PublicServerPanel;
import NamelessDev.Graphics.panels.SettingPanel;
import NamelessDev.localisation.Localisation;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class MainTabbedPane extends JTabbedPane {

    public MainTabbedPane() throws IOException {
        makeTabbedPane();
        add(new ChatRoomPanel(), Localisation.getLocalisedName("ChatroomPanel"));
        add(new SettingPanel(), Localisation.getLocalisedName("SettingPanel"));
        add(new HelpPanel(), Localisation.getLocalisedName("HelpPanel"));
        add(new PublicServerPanel(), Localisation.getLocalisedName("PublicServerPanel"));

    }

    void makeTabbedPane() {
        setPreferredSize(new Dimension(900, 400));
        setBackground(Color.YELLOW);
        setFocusable(false);
        setTabPlacement(BOTTOM);
    }

}
