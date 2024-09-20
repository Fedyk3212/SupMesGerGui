package feodorbal.localisation;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class LangPacksGraphics extends JFrame {
    private static final ArrayList<String> optionsList = new ArrayList<>();
    private static final JList<String> list = new JList<>();

    private LangPacksGraphics(String[] options) throws IOException {
        super(Localisation.getLocalisedName("LangPackWindowTile"));
        setSize(200, 100);
        setVisible(true);
    }

    static void makeList(String[] options) {
        for (String optionName : options) {
            list.add(optionName, new JButton());
        }
    }
}
