package feodorbal.cryptography;

import feodorbal.useful.Logger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class CryptoHandler {
    public static void initCryptoGraphy() throws NoSuchAlgorithmException, IOException {
        new AesCrypterDecrypter();
        new KeyGen();
        KeyGen.setPubKey();
        KeyGen.setPrivKey();
        Logger.Log(CryptoHandler.class, "Cryptography initialized");
    }
}
