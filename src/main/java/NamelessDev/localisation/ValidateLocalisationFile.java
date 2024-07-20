package NamelessDev.localisation;

import NamelessDev.config.Configs;
import NamelessDev.config.WorkWithProperties.PropertiesHandler;
import NamelessDev.useful.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ValidateLocalisationFile {

    private static final Configs configs;

    static {
        try {
            configs = new Configs();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String langsPath = "ConfigsAndLoc/localisation";
    private static final String defaultLangPath = "ConfigsAndLoc/localisation/English.lang";
    private static final PropertiesHandler propertiesHandler;

    static {
        try {
            propertiesHandler = new PropertiesHandler(defaultLangPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final PropertiesHandler langFilePropertiesHandler;

    static {
        try {
            String choosedLangPath = langsPath + "/" + configs.readByName("Lang") + ".lang";
            langFilePropertiesHandler = new PropertiesHandler(choosedLangPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void validate() throws IOException, URISyntaxException, ParseException {
        HashMap<String, Object> deafultKeysValues = getDeafultKeys();
        ArrayList<String> langFileKeys = getLangFileKeys();
        for (String key : deafultKeysValues.keySet()) {
            if (!langFileKeys.contains(key)) {
                langFilePropertiesHandler.addNewParameter(key, (String) deafultKeysValues.get(key));
            }
            if (Objects.equals(langFilePropertiesHandler.readByName(key), "")){
                langFilePropertiesHandler.changeByName(key, "Empty");
            }
        }
        Logger.Log(ValidateLocalisationFile.class, "Localisation File Validated and Fixed");
    }

    static HashMap<String, Object> getDeafultKeys() throws IOException, URISyntaxException {
        HashMap<String, Object> keysValues = new HashMap<>();
        try {
            keysValues = propertiesHandler.readAll();
        } catch (IOException e) {
            try {
                UnpackLangFiles.unpack();
                getDeafultKeys();
            } catch (Exception exception) {
                System.exit(1);
            }
        }
        return keysValues;
    }

    static ArrayList<String> getLangFileKeys() throws IOException, ParseException, URISyntaxException {
        ArrayList<String> keys = new ArrayList<>();
        try {
            keys.addAll(langFilePropertiesHandler.readAll().keySet());
        } catch (IOException e) {
            configs.changeByName("Lang", "None");
            Localisation.initLocalisation();
        }
        return keys;
    }
}
