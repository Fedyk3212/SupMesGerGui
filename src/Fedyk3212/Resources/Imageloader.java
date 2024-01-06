package Fedyk3212.Resources;

import javax.swing.*;
import java.util.Base64;

public class Imageloader {
    public static ImageIcon load_image(String base){
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] image = decoder.decode(base);
        return new ImageIcon(image);
    }
}
