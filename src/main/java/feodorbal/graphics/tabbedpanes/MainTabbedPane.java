package feodorbal.graphics.tabbedpanes;

import feodorbal.graphics.panels.ChatRoomPanel;
import feodorbal.graphics.panels.HelpPanel;
import feodorbal.graphics.panels.PublicServerPanel;
import feodorbal.graphics.panels.SettingPanel;
import feodorbal.localisation.Localisation;

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
