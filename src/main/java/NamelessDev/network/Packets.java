package NamelessDev.network;

import NamelessDev.cryptography.AesCrypterDecrypter;
import NamelessDev.resource.exception.EncryptException;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Packets {
    public static String sendPacket(String msg, String token) throws EncryptException {
        Map<String, Object> send = new HashMap<>();
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
    public static String checkKey() throws Exception {
        Map<String, String> checkKeyPacket = new HashMap<>();
        checkKeyPacket.put("N", "CHK");
        checkKeyPacket.put("K", AesCrypterDecrypter.encrypt(NetworkVariables.getAESKEY()));
        JSONObject jsonObject = new JSONObject(checkKeyPacket);
        return jsonObject.toJSONString();
    }

    public static String pingToken() throws Exception {
        Map<Object, String> ping_token_key = new HashMap<>();
        ping_token_key.put("N", "PT");
        if (Token.getToken() != null) {
            ping_token_key.put("T", AesCrypterDecrypter.encrypt(Objects.requireNonNull(Token.getToken())));
        }else {
            ping_token_key.put("T", "IDK");
        }
        JSONObject jp = new JSONObject(ping_token_key);
        return jp.toJSONString();
    }
    public static String pingKey() {
        Map<Object,String> ping_key = new HashMap<>();
        ping_key.put("N", "PK");
        ping_key.put("K", NetworkVariables.getPubkey());
        JSONObject jl = new JSONObject(ping_key);
        return jl.toJSONString();
    }
}
