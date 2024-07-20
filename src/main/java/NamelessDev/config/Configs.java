package NamelessDev.config;

import NamelessDev.config.WorkWithProperties.PropertiesHandler;
import NamelessDev.useful.Logger;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Properties;

public class Configs extends PropertiesHandler {
    private static final String path = "ConfigsAndLoc/Configs.properties";

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
        File file = new File(path);
        if (!file.exists()) {
            new File("ConfigsAndLoc").mkdir();
            boolean result = file.createNewFile();
            Configs.makeProperties();
            Logger.Log(Configs.class, "Configs File Created");
        }
        Logger.Log(Configs.class, "Configs File Exist");
    }

    public static void makeProperties() throws IOException {
        Properties propertiesfile = new Properties();
        propertiesfile.putAll(defaultProperties);
        propertiesfile.store(new OutputStreamWriter(Files.newOutputStream(Paths.get(path)), StandardCharsets.UTF_8), null);
        Logger.Log(Configs.class, "Set up default properties");
    }
}
