package feodorbal.resource;

import javax.swing.*;
import java.awt.*;

public class Succefuls {
    public static void LoginSucces(){
        JOptionPane.showMessageDialog(Frame.getFrames()[0], "Login Success", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void RegisterSucces(){
        JOptionPane.showMessageDialog(Frame.getFrames()[0], "Registration Success", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
