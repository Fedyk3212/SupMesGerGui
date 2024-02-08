package Fedyk3212.cryptography;

import Fedyk3212.client.MainGraphics;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CrypterDecrypter {
    public static String Encrypt(String string) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String keypub = MainGraphics.ServerPublicKey;
        String pubkey = keypub.replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");
        byte[] encoded = Base64.getDecoder().decode(pubkey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        java.security.interfaces.RSAPublicKey publicKey = (java.security.interfaces.RSAPublicKey) keyFactory.generatePublic(keySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bmessage = string.getBytes(StandardCharsets.UTF_8);
        byte[] dmessage = cipher.doFinal(bmessage);
        return Base64.getEncoder().encodeToString(dmessage);
    }
    public static String Decrypt(String string) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] bytesmessage = Base64.getDecoder().decode(string);
        String pk = MainGraphics.Prikey;
        String privateKeyPEM = pk
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");
        byte[] encodedd = Base64.getDecoder().decode(privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedd);
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        Cipher decryptor = Cipher.getInstance("RSA");
        decryptor.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decmsgbytes = decryptor.doFinal(bytesmessage);
        return new String(decmsgbytes, StandardCharsets.UTF_8);
    }
}
