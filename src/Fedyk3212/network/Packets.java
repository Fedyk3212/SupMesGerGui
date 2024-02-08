package Fedyk3212.network;

import Fedyk3212.client.MainGraphics;
import Fedyk3212.cryptography.AesCrypterDecrypter;
import Fedyk3212.cryptography.AesCrypterDecrypter;
import Fedyk3212.resource.exception.EncryptException;
import org.json.simple.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

public class Packets {
    public static String sendPacket(String msg, String token) throws EncryptException {
        Map<Object, String> send = new HashMap<>();
        send.put("N", "S");
        try {
            send.put("M", AesCrypterDecrypter.encrypt(msg));
            send.put("T", AesCrypterDecrypter.encrypt(token));
        }catch (Exception e){
            throw new EncryptException();}
        JSONObject sendJ = new JSONObject(send);
        return sendJ.toJSONString();
    }

    public static String loginPacket(String logins, String password) throws Exception {
        Map<Object, String> login = new HashMap<>();
        login.put("N", "L");
        login.put("L", AesCrypterDecrypter.encrypt(logins));
        login.put("P", AesCrypterDecrypter.encrypt(password));
        JSONObject loginJ = new JSONObject(login);
        return loginJ.toJSONString();
    }

    public static String registerPacket(String login, String password) throws Exception {
        Map<Object, String> register = new HashMap<>();
        register.put("N", "R");
        register.put("L", AesCrypterDecrypter.encrypt(login));
        register.put("P", AesCrypterDecrypter.encrypt(password));
        JSONObject jsonObject = new JSONObject(register);
        return jsonObject.toJSONString();
    }
    public static String validPacket(){
        Map<Object, String> valid = new HashMap<>();
        valid.put("N", "V");
        valid.put("V", "1.5");
        valid.put("AN", "SupMesGer_Gui");
        valid.put("UI", "12:31:12:31");
        return valid.toString();
    }
    public static String pingToken() throws Exception {
        Map<Object, String> ping_token_key = new HashMap<>();
        ping_token_key.put("N", "PT");
        if (MainGraphics.getToken() != null) {
            ping_token_key.put("T", AesCrypterDecrypter.encrypt(MainGraphics.getToken()));
        }else {
            ping_token_key.put("T", "IDK");
        }
        JSONObject jp = new JSONObject(ping_token_key);
        return jp.toJSONString();
    }
    public static String pingKey() throws IOException, NoSuchAlgorithmException{
        Map<Object,String> ping_key = new HashMap<>();
        ping_key.put("N", "PK");
        ping_key.put("K", MainGraphics.Pubkey);
        JSONObject jl = new JSONObject(ping_key);
        return jl.toJSONString();
    }
}
