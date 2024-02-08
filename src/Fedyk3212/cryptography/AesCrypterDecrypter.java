package Fedyk3212.cryptography;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Collection;

public class AesCrypterDecrypter {
    private static Cipher enchiper;
    private static Cipher dechiper;
    private static SecretKeyFactory factory;
    public AesCrypterDecrypter() {
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static String encrypt(String value) throws Exception {
        Cipher cipher = enchiper;
        byte[] encrypted = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encrypted) throws Exception {
        Cipher cipher = dechiper;
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
        return new String(original);
    }

    public static void initCiphers(String secretKey, String salt) throws Exception {
        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec skeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");
        enchiper = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        enchiper.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
        dechiper = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        dechiper.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
    }
}
