package com.res;

import javax.swing.*;
import java.util.Base64;

public class images {
    public static ImageIcon image_loader(String base){
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] image = decoder.decode(base);
        return new ImageIcon(image);
    }
}
