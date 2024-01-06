package Fedyk3212.Net_Work;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class Packets {
       public static String send_packet(String msg, String token) {
           Map<Object, String> send = new HashMap<>();
           send.put("N", "S");
           send.put("M", msg);
           send.put("T", token);
           JSONObject sendJ = new JSONObject(send);
           return sendJ.toJSONString();
       }

       public static String login_packet(String logins, String password) {
            Map<Object, String> login = new HashMap<>();
            login.put("N", "L");
            login.put("L", logins);
            login.put("P", password);
            JSONObject loginJ = new JSONObject(login);
            return loginJ.toJSONString();
       }

       public static String register_packet(String login, String password) {
           Map<Object, String> register = new HashMap<>();
           register.put("N", "R");
           register.put("L", login);
           register.put("P", password);
           JSONObject jsonObject = new JSONObject(register);
           return jsonObject.toJSONString();
       }
       public static String valid_packet(){
           Map<Object, String> valid = new HashMap<>();
           valid.put("N", "V");
           valid.put("V", "1.5");
           valid.put("AN", "SupMesGer_Gui");
           valid.put("UI", "12:31:12:31");
           return valid.toString();
       }
   }
    