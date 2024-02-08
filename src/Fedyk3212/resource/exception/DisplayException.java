package Fedyk3212.resource.exception;

import javax.swing.*;
import java.awt.*;

public class DisplayException {
    public static void display(Exception e){
        JOptionPane.showMessageDialog(Frame.getFrames()[0], e.getClass().getName(), e.getLocalizedMessage(), JOptionPane.ERROR_MESSAGE);
    }
}
