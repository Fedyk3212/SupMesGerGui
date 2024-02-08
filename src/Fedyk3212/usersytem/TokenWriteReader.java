package Fedyk3212.usersytem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TokenWriteReader {
    private static HashMap<String, String> map;

    public TokenWriteReader() throws IOException {
        map = new HashMap<>();
        File file = new File("tokens.properties");
        if (file.exists()){
            map = (HashMap<String, String>) hashMapReader();
        }
    }
    public static void Write(String key, String value) throws IOException {
        map.put(key, value);
        hashMapWriter(map);
    }
    static void hashMapWriter(HashMap<String, String> map) throws IOException {
        Map<String, String> ldapContent;
        ldapContent = map;
        Properties properties = new Properties();
        properties.putAll(ldapContent);
        properties.store(Files.newOutputStream(Paths.get("tokens.properties")), null);
    }

    public static Map<String, String> hashMapReader() throws IOException {
        Map<String, String> loaded = new HashMap<String, String>();
        Properties properties = new Properties();
        properties.load(Files.newInputStream(Paths.get("tokens.properties")));
        for (String key : properties.stringPropertyNames()) {
            loaded.put(key, properties.get(key).toString());
        }
        return loaded;
    }
    public static void RemTokByName(String name) throws IOException {
        try {
            if (name!=null) {
                map.remove(name);
            }
        }catch (IllegalArgumentException e){
            System.out.println("keyNotFoundedLmao");
        }
        hashMapWriter(map);
    }
    public static String getTokenbyName(String name) throws IOException{
        try {
            if (name!=null) {
                return map.get(name);
            }
        }catch (IllegalArgumentException e){
            System.out.println("dontWorkLmao");
        }
        return "Error";
    }
}
