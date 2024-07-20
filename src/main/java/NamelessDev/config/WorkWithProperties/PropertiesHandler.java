package NamelessDev.config.WorkWithProperties;

import NamelessDev.useful.Logger;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Properties;

public class PropertiesHandler {
    public String path;

    public PropertiesHandler(String path) throws IOException {
        this.path = path;
        Logger.Log(PropertiesHandler.class,"Initialized with path " + path);
    }

    public void changeByName(String name, String parameter) throws IOException, ParseException {
        HashMap<String, Object> loadedproperties = new HashMap<>();
        Properties properties = new Properties();
        Path path1 = Paths.get(path);
        properties.load(new InputStreamReader(Files.newInputStream(Paths.get(path)), StandardCharsets.UTF_8));
        for (String key : properties.stringPropertyNames()) {
            loadedproperties.put(key, properties.get(key).toString());
        }
        loadedproperties.replace(name, parameter);
        Properties propertiesfile = new Properties();
        propertiesfile.putAll(loadedproperties);
        propertiesfile.store(new OutputStreamWriter(Files.newOutputStream(Paths.get(path)), StandardCharsets.UTF_8), null);
        Logger.Log(PropertiesHandler.class, "Param Changed by name " + name);
    }

    public HashMap<String, Object> readAll() throws IOException {
        HashMap<String, Object> loadedproperties = new HashMap<>();
        Properties properties = new Properties();
        properties.load(new InputStreamReader(Files.newInputStream(Paths.get(path).toFile().toPath()), StandardCharsets.UTF_8));
        for (String key : properties.stringPropertyNames()) {
            loadedproperties.put(key, properties.get(key).toString());
        }
        Logger.Log(PropertiesHandler.class, "Read all properties file");
        return loadedproperties;

    }

    public void addNewParameter(String name, String Parameter) throws IOException {
        HashMap<String, Object> loadedproperties = new HashMap<>();
        Properties properties = new Properties();
        Path path1 = Paths.get(path);
        properties.load(new InputStreamReader(Files.newInputStream(Paths.get(path).toFile().toPath()), StandardCharsets.UTF_8));
        for (String key : properties.stringPropertyNames()) {
            loadedproperties.put(key, properties.get(key).toString());
        }
        loadedproperties.put(name, Parameter);
        Properties propertiesfile = new Properties();
        propertiesfile.putAll(loadedproperties);
        propertiesfile.store(new OutputStreamWriter(Files.newOutputStream(Paths.get(path)), StandardCharsets.UTF_8), null);
        Logger.Log(PropertiesHandler.class, "addedNewParameter " + name + ": " + Parameter);
    }

    public String readByName(String name) throws IOException {
        HashMap<String, Object> loadedproperties = new HashMap<>();
        Properties properties = new Properties();
        properties.load(new InputStreamReader(Files.newInputStream(Paths.get(path).toFile().toPath()), StandardCharsets.UTF_8));
        for (String key : properties.stringPropertyNames()) {
            loadedproperties.put(key, properties.get(key).toString());
        }
        Logger.Log(PropertiesHandler.class, "Param read by name " + name);
        return (String) loadedproperties.get(name);
    }

    public void fillDefParam(String[] keys, String value) throws IOException {
        for (String key : keys) {
            if (readByName(key) == null) {
                addNewParameter(key, value);
            }
        }
    }
}
