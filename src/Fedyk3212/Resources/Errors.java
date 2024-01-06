package Fedyk3212.Resources;

import javax.swing.*;
import java.awt.*;

public class Errors {
    public static void Show_login_error() {
        JOptionPane.showMessageDialog(Frame.getFrames()[0], "Login or password uncorrect", "Login Error", JOptionPane.ERROR_MESSAGE);
    }
    public static void Show_registration_error(){
        JOptionPane.showMessageDialog(Frame.getFrames()[0], "Registration Data uncorrect dont use UniCode or something", "Registration Error", JOptionPane.ERROR_MESSAGE);
    }
    public static void Show_validation_error(){
        JOptionPane.showMessageDialog(Frame.getFrames()[0], "Validation Error check Server", "Validation Error", JOptionPane.ERROR_MESSAGE);
    }
    public static void Show_Ethernet_error(){
        JOptionPane.showMessageDialog(Frame.getFrames()[0], "Check Ethernet Connection, chat turned local mode, to fix it restart your app", "No Ethernet?", JOptionPane.INFORMATION_MESSAGE);
    }
}
