package feodorbal.useful.crossplatform;

import feodorbal.useful.Logger;

import java.io.InputStream;
import java.net.URL;

public class CrossPlatform {
    public static URL getResource(Class<?> clazz, String path) {
        Logger.Log(CrossPlatform.class, "Detected os " + System.getProperty("os.name"));
        if (System.getProperty("os.name").toLowerCase().contains("lin") || System.getProperty("os.name").toLowerCase().contains("mac") || System.getProperty("os.name").toLowerCase().contains("arc")) {
            return clazz.getResource(path);
        } else
            return clazz.getClassLoader().getResource(path);
    }
    public static InputStream getResourceAsStream(Class<?> clazz, String path){
        Logger.Log(CrossPlatform.class, "Detected os " + System.getProperty("os.name"));
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            return clazz.getResourceAsStream(path);
        } else
            return clazz.getClassLoader().getResourceAsStream(path);
    }
}
