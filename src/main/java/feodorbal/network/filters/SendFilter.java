package feodorbal.network.filters;

import feodorbal.localisation.Localisation;

import javax.swing.*;
import java.io.IOException;

public class SendFilter {

    public static int filter(String message, int maxMessageLength, int minMessageLength) throws IOException {
        if (message.length() > maxMessageLength) {
            JOptionPane.showMessageDialog(null, Localisation.getLocalisedName("MessageToLongMessage") + " " + message.length() + " >" + " " + maxMessageLength, "Error", JOptionPane.WARNING_MESSAGE);
            return 1;
        } else if (message.length() < minMessageLength) {
            JOptionPane.showMessageDialog(null, Localisation.getLocalisedName("MessageToShortMessage") + " " + message.length() + " <" + " " + minMessageLength, "Error", JOptionPane.WARNING_MESSAGE);
            return 1;
        } else if (message.isEmpty()) {
            return 0;
        } else if (message.replace(" ", "").isEmpty()) {
            return 0;
        }
        return 2;
    }
}
