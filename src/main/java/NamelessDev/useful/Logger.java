package NamelessDev.useful;

import NamelessDev.resource.exception.DisplayException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    static StringBuilder Journal = new StringBuilder();
    static int cnt = 1;
    public static boolean noLog = false;
    public static boolean terminalnolog = false;

    public static void Log(Class<?> clazz, String msg) {
        if (!terminalnolog) {
            System.out.println("[" + cnt + "]" + "{" + clazz.getSimpleName() + "}" + " " + msg + "\n");
            Journal.append("[").append(cnt).append("]").append("{").append(clazz.getSimpleName()).append("}").append(" ").append(msg).append("\n");
            cnt++;
        }
    }

    public static void initLogger() throws Exception {
        File file = new File("ConfigsAndLoc/log.txt");
        if (!file.exists()) {
            file.createNewFile();
        } else {
            file.delete();
        }
    }

    public static void writeLogToFile() throws IOException {
        if (!noLog) {
            File file = new File("ConfigsAndLoc/log.txt");
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(Journal.toString());
                writer.close();
            } catch (Exception e) {
                DisplayException.display(e);
            }
        }
    }
}
