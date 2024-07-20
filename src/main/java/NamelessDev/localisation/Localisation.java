package NamelessDev.localisation;

import NamelessDev.config.Configs;
import NamelessDev.config.WorkWithProperties.PropertiesHandler;
import NamelessDev.useful.Logger;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Localisation {
    static Configs configs;

    static {
        try {
            configs = new Configs();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final static String langsPath = "ConfigsAndLoc/localisation";
    static String choosedLangPath = langsPath + "/";
    static PropertiesHandler localsationHandler;

    public Localisation() throws IOException {
        localsationHandler = new PropertiesHandler(choosedLangPath);
    }

    public static void initLocalisation() throws IOException, URISyntaxException, ParseException {
        UnpackLangFiles.unpack();
        UnpackLangPacks.unpack();
        firstChoiceMenu();
        choose();
        new Localisation();
        ValidateLocalisationFile.validate();
        Logger.Log(Localisation.class, "Localisation initiated");
    }

    static void firstChoiceMenu() throws IOException, ParseException {
        String[] options = getOptionList();
        int selection;
        if (configs.readByName("Lang").equals("None")) {
            selection = JOptionPane.showOptionDialog(null, "Choose your language/Выберите ваш язык", "Language", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            configs.changeByName("Lang", options[selection]);
        }
    }

    public static void changeLanguage() throws IOException, URISyntaxException, ParseException {
        String[] options = getOptionList();
        int selection;
        selection = JOptionPane.showOptionDialog(null, localsationHandler.readByName("ChooseLanguageMessage"), "Language", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (!options[selection].equals(configs.readByName("Lang"))){
            choose();
            ValidateLocalisationFile.validate();
            configs.changeByName("Lang", options[selection]);
            Logger.Log(Localisation.class, "Language Changed");
            System.exit(0);
        }
    }

    static String[] getOptionList() {
        File[] files = new File(langsPath).listFiles();
        ArrayList<String> options = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
            if (files[i].toString().endsWith(".lang")) {
                options.add(files[i].getName().replace(".lang", ""));
            }
        }
        String[] optionsArray = new String[options.size()];
        for (int i = 0; i != options.size(); i++) {
            optionsArray[i] = options.get(i);
        }
        return optionsArray;
    }


    static void choose() throws IOException, URISyntaxException, ParseException {
        String[] langs = getOptionList();
        String selection = configs.readByName("Lang");
        int i = 0;
        try {
            for (String lang : langs) {
                if (lang.endsWith(selection)) {
                    break;
                } else i++;
            }
            Logger.Log(Localisation.class, i + " " + Arrays.toString(langs) + "  "+ selection);
            choosedLangPath = choosedLangPath + langs[i] + ".lang";
        } catch (ArrayIndexOutOfBoundsException ignored) {
            Logger.Log(Localisation.class, "Localisation File dosent Found trying to fix");
            configs.changeByName("Lang", "English");
            initLocalisation();
        }
    }

    public static String getLocalisedName(String name) throws IOException {
        Logger.Log(Localisation.class, "Get LocalisedName by Name " + name);
        try {
            return localsationHandler.readByName(name);
        } catch (Exception ignore) {
            return "I ma broken :-(( #" + name;
        }
    }

}
