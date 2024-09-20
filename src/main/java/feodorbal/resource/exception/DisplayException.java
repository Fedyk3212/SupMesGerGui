package feodorbal.resource.exception;

import javax.swing.*;

public class DisplayException {
    public static void display(Exception e){
        JOptionPane.showMessageDialog(null, e.getClass().getName(), e.getLocalizedMessage(), JOptionPane.ERROR_MESSAGE);
    }
    public static void displayStringException(String ex, String title){
        JOptionPane.showMessageDialog(null, title, ex, JOptionPane.ERROR_MESSAGE);
    }
}
