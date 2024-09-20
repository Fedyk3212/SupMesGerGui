package feodorbal.network;

import feodorbal.cryptography.AesCrypterDecrypter;
import feodorbal.useful.Logger;

import java.io.*;

public class Token {
    private static String token = null;
    public static final String path = "ConfigsAndLoc/token.txt";
    public static void remToken() {
        token = null;
    }

    public static void writeTokenFile(String token) throws Exception {
        File file = new File(path);
        Writer writer = new FileWriter(file);
        writer.write(AesCrypterDecrypter.decrypt(token));
        writer.flush();
        writer.close();
        Logger.Log(Token.class, "token wrote");
    }
    public static void delTokenFile(){
        File file = new File(path);
        if (file.delete()) {
            Logger.Log(Token.class, "File deleted");
            Token.remToken();
        } else {
            Logger.Log(Token.class,"File not found");
        }
    }

    public static String getToken() {
        try {
            if (token == null) {
                BufferedReader reader = new BufferedReader(new FileReader(path));
                token = reader.readLine();
                reader.close();
                Logger.Log(Token.class, "get Token");
                return token;
            }
        } catch (Exception ex) {
            return null;
        }
        return token;
    }
}