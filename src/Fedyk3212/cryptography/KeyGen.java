package Fedyk3212.cryptography;

import Fedyk3212.client.MainGraphics;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

public class KeyGen {
    private static KeyPair pair;
    public KeyGen() throws NoSuchAlgorithmException {
        System.out.println("Key Generated");
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
        keygen.initialize(2048);
        pair = keygen.generateKeyPair();

    }
    // Public
    public static void setPubKey() throws IOException {
        StringWriter writer = new StringWriter();
        PemWriter pemWriter = new PemWriter(writer);
        pemWriter.writeObject(new PemObject("PUBLIC KEY", pair.getPublic().getEncoded()));
        pemWriter.flush();
        pemWriter.close();
        MainGraphics.Pubkey = writer.toString();
    }
    // Private
    public static void setPrivKey() throws IOException{
        PrivateKey privateKey = pair.getPrivate();
        StringWriter writer = new StringWriter();
        PemWriter pemWriter = new PemWriter(writer);
        pemWriter.writeObject(new PemObject("PRIVATE KEY", privateKey.getEncoded()));
        pemWriter.flush();
        pemWriter.close();
        MainGraphics.Prikey = writer.toString();
    }
}
