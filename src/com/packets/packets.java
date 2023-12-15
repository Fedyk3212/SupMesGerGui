package com.packets;

import com.net.ChatClient;

import java.util.HashMap;
import java.util.Map;
public class packets{
public static boolean privatemode;
public static String uuid;
public static String packet_name;
public static String msg;

public static String send_message_packet(String message){
        packet_name = "Send";
        uuid = uuid;
        msg = message;

        Map<String, Object> packet_send = new HashMap<>();
        packet_send.put("packet_name", packet_name);
        packet_send.put("uuid", uuid);
        packet_send.put("privatemode", privatemode);
        packet_send.put("message", msg);
        return packet_send.toString();
        }
public static String send_login_packet(String login,String password){
        packet_name = "Login";

        Map<String, Object> packet_login = new HashMap<>();
        packet_login.put("packet_name", packet_name);
        packet_login.put("login", login);
        packet_login.put("password", password);
        return packet_login.toString();
        }
public static void send_register_packet(){

        }
        }