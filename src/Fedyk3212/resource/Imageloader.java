package Fedyk3212.resource;

import javax.swing.*;
import java.awt.*;
import java.util.Base64;

public class Imageloader {
    public static ImageIcon loadIcon(String base){
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] image = decoder.decode(base);
        return new ImageIcon(image);
    }
    public static byte[] getImageBytes(String base){
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(base);
    }
}
