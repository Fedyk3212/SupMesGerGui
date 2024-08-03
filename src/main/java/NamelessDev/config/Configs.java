package NamelessDev.config;

import NamelessDev.config.WorkWithProperties.PropertiesHandler;
import NamelessDev.useful.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Properties;

public class Configs extends PropertiesHandler {
    private static final String path = "./ConfigsAndLoc/Configs.properties".replace("/", File.separator);

    static HashMap<String, Object> defaultProperties = new HashMap<>();

    static {
        defaultProperties.put("IP", "127.0.0.1");
        defaultProperties.put("Port", "65433");
        defaultProperties.put("Lang", "None");
        defaultProperties.put("isShowed", "false");
        defaultProperties.put("fullNolog", "false");
        defaultProperties.put("IgnoreUpdate", "");
    }

    public Configs() throws IOException {
        super(path);
    }

    public static void MakeConfigFile() throws IOException {
       File file = Paths.get(path).toFile();
        if (!file.exists()) {
            new File(String.valueOf(Paths.get("ConfigsAndLoc/").toFile())).mkdir();
            Configs.makeProperties();
            Logger.Log(Configs.class, "Configs File Created");
        }
        Logger.Log(Configs.class, "Configs File Exist");
    }

    public static void makeProperties() throws IOException {
        Properties propertiesfile = new Properties();
        propertiesfile.putAll(defaultProperties);
        propertiesfile.store(Files.newOutputStream(Paths.get(path)), null);
        Logger.Log(Configs.class, "Set up default properties");
    }
}
