package Fedyk3212.config;

import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Properties;

public class Configs {
    private static final String path = "Configs.properties";

    public static void makeProperties() throws IOException {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("IP", "127.0.0.1");
        properties.put("Port", "65433");
        properties.put("Lang", "3");
        properties.put("isShowed", "false");
        Properties propertiesfile = new Properties();
        propertiesfile.putAll(properties);
        propertiesfile.store(Files.newOutputStream(Paths.get(path)), null);
    }

    public static void changeByName(String name, String parameter) throws IOException, ParseException {
        HashMap<String, Object> loadedproperties = new HashMap<>();
        Properties properties = new Properties();
        Path path1 = Paths.get(path);
        properties.load(Files.newInputStream(path1));
        for (String key : properties.stringPropertyNames()) {
            loadedproperties.put(key, properties.get(key).toString());
        }
        loadedproperties.replace(name, parameter);
        Properties propertiesfile = new Properties();
        propertiesfile.putAll(loadedproperties);
        propertiesfile.store(Files.newOutputStream(path1), null);
    }

    public static HashMap<String, Object> readConfigs() throws IOException {
        HashMap<String, Object> loadedproperties = new HashMap<>();
        Properties properties = new Properties();
        Path path1 = Paths.get(path);
        properties.load(Files.newInputStream(path1));
        for (String key : properties.stringPropertyNames()) {
            loadedproperties.put(key, properties.get(key).toString());
        }
        return loadedproperties;
    }

    public static void addNewParameter(String name, String Parameter) throws IOException {
        HashMap<String, Object> loadedproperties = new HashMap<>();
        Properties properties = new Properties();
        Path path1 = Paths.get(path);
        properties.load(Files.newInputStream(path1));
        for (String key : properties.stringPropertyNames()) {
            loadedproperties.put(key, properties.get(key).toString());
        }
        loadedproperties.put(name, Parameter);
    }

    public static String readByName(String name) throws IOException {
        HashMap<String, Object> loadedproperties = new HashMap<>();
        Properties properties = new Properties();
        Path path1 = Paths.get(path);
        properties.load(Files.newInputStream(path1));
        for (String key : properties.stringPropertyNames()) {
            loadedproperties.put(key, properties.get(key).toString());
        }
        return (String) loadedproperties.get(name);
    }

}
