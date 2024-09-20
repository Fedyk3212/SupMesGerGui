package feodorbal.useful;

import feodorbal.config.Configs;
import feodorbal.localisation.Localisation;
import feodorbal.resource.exception.DisplayException;
import feodorbal.resource.exception.GetVersionException;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class CheckUpdate {
    private static String defaultVersion = "2.5";
    private static String recivedVersion = "";
    public void setDefaultVersion(String version) {
        defaultVersion = version;
    }

    public static void CheckForUpdates() throws IOException {

        HttpURLConnection con = null;
        try {
            URL url = new URL("https://raw.githubusercontent.com/BackendIsFun/Open-Chat/master/version.txt");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.getResponseCode();
        } catch (Exception e) {
            DisplayException.display(new GetVersionException());
            Logger.Log(CheckUpdate.class, "Cannot Get Chat Version");
            return;
        }
        try {
            BufferedReader scanner = new BufferedReader(new InputStreamReader(con.getInputStream()));
            recivedVersion = scanner.readLine();
            scanner.close();
            con.disconnect();
            if (Objects.equals(recivedVersion, defaultVersion)) {
                Logger.Log(CheckUpdate.class, "Version Correct");
            } else {
                Logger.Log(CheckUpdate.class, "Outdated");
                updateFrame();

            }
        } catch (Exception ex) {
            Logger.Log(AutoUpdate.class, "Cannot get update + " + ex);
        }

    }

    static void updateFrame() throws IOException, ParseException {
        Configs configs = new Configs();
        if (!configs.readByName("IgnoreUpdate").equals(recivedVersion)) {
            String[] options = new String[]{Localisation.getLocalisedName("YesButton"), Localisation.getLocalisedName("NoButton"), Localisation.getLocalisedName("DontRememberButton")};
            int answer = JOptionPane.showOptionDialog(null, Localisation.getLocalisedName("UpdateWindowMessage"), Localisation.getLocalisedName("UpdateWindowTile"), JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            Logger.Log(CheckUpdate.class, "Answer is " + answer);
            if (answer == 0) {
                AutoUpdate.update();
            } else if (answer == 2) {
                configs.changeByName("IgnoreUpdate", recivedVersion);
            }
        }
    }
}
