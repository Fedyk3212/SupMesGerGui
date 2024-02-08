package Fedyk3212.resource;

import javax.swing.*;
import java.awt.*;
public class Errors{
    public static void Show_validation_error(){
        JOptionPane.showMessageDialog(Frame.getFrames()[0], "Validation Error check Server", "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void Show_Encrypt_Error(){
        JOptionPane.showMessageDialog(Frame.getFrames()[0], "Encryption Error maybe public key not founded try to reconnect to the server or reset config", "Encrypt Error",JOptionPane.ERROR_MESSAGE);
    }
}
