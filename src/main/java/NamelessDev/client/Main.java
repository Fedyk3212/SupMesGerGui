package NamelessDev.client;

import NamelessDev.Graphics.MainGraphics;
import NamelessDev.config.Configs;
import NamelessDev.localisation.Localisation;
import NamelessDev.config.ValidateConfigs;
import NamelessDev.cryptography.CryptoHandler;
import NamelessDev.useful.Logger;
import NamelessDev.useful.CheckUpdate;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, URISyntaxException, ParseException {
        ConsoleHandler.parseArgs(args);
        ValidateConfigs.validate();
        addShutDownHook();
        Configs.MakeConfigFile();
        Localisation.initLocalisation();
        CryptoHandler.initCryptoGraphy();
        MainGraphics.initMainGraphics();
        CheckUpdate.CheckForUpdates();
    }

    public static void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Logger.writeLogToFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        Logger.Log(Main.class, "ShutDown hook Created");
    }


}
