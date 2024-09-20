package feodorbal.client;

import feodorbal.graphics.panels.ChatRoomPanel;
import feodorbal.useful.CheckUpdate;
import feodorbal.useful.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class ConsoleHandler {
    private static final boolean debug = false;

    static void nolog() {
        Logger.noLog = true;
    }

    static void terminalNoLog() {
        Logger.terminalnolog = true;
    }

    static void fullNoLog() {
        Logger.noLog = true;
        Logger.terminalnolog = true;
    }

    static void setDefaultVersion(String version) {
        CheckUpdate checkUpdate = new CheckUpdate();
        checkUpdate.setDefaultVersion(version);
    }

    static void setMaxMessageLimit(int limit) {
        ChatRoomPanel.setMaxMessageLImit(limit);
    }

    static void printHelp() {
        System.out.println(String.join("\n", "-h ---- prints help menu", "-nologFile ---- disable log file creation", "-Termnolog ---- disable terminal log", "-Fullnolog ---- disable all logs", "-debug [option] [arg] ---- access to debug options"));
    }

    static void printDebugHelp() {
        System.out.println(String.join("\n", "-setdefver [args] ---- set default version of program", "-setMaxMessageLimit [int arg] ---- sets max message limit", "-resetconfig ---- resets config"));
    }

    public static void parseArgs(String[] args) throws IOException {
        if (args.length > 0) {

            switch (args[0]) {
                case "-h":
                    printHelp();
                    System.exit(1);
                    break;
                case "-nologFile":
                    nolog();
                    break;
                case "-Termnolog":
                    terminalNoLog();
                    break;
                case "-Fullnolog":
                    fullNoLog();
                    break;
                case "-debug":
                    if (debug) {
                        if (args.length == 3) {
                            if (args[1].equals("-setdefver")) {
                                setDefaultVersion(args[2]);
                            } else if (args[1].equals("-setMaxMessageLimit")) {
                                setMaxMessageLimit(Integer.parseInt(args[2]));
                            }
                        } else if (args.length == 2) {
                            if (args[1].equals("-resetconfig")) {
                                Files.delete(Paths.get("ConfigsAndLoc/Configs.properties"));
                            }
                        } else {
                            printDebugHelp();
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Debug mode shutdown");
                        System.exit(1);
                    break;
            }
        }
        Logger.Log(Class.class, "Ran with " + Arrays.toString(args) + "argument");
    }
    }
}
