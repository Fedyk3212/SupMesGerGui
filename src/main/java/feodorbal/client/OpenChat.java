package feodorbal.client;

import feodorbal.graphics.MainGraphics;
import feodorbal.config.Configs;
import feodorbal.localisation.Localisation;
import feodorbal.config.ValidateConfigs;
import feodorbal.cryptography.CryptoHandler;
import feodorbal.useful.Logger;
import feodorbal.useful.CheckUpdate;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

public class OpenChat {
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
        Logger.Log(OpenChat.class, "ShutDown hook Created");
    }


}
