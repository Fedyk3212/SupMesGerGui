package Fedyk3212.config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Localisation {
    // Локализация
    public static String[] choosedloc() throws IOException, ParseException {
        int choose = Integer.parseInt(Configs.readByName("Lang"));
        Map<String, Object> hashMapru = getStringObjectMap();
        Map<String, Object> hashMapEng = getObjectMap();
        JSONObject rujsobj = new JSONObject(hashMapru);
        JSONObject engobj = new JSONObject(hashMapEng);
        File file = new File("Ruloc.json");
        File file1 = new File("Engloc.json");
        if (!file.exists()) {
            FileWriter writer1 = new FileWriter(file);
            writer1.write(rujsobj.toJSONString());
            writer1.flush();
            writer1.close();
        } else {
            System.out.println("existRU");
        }
        if (!file1.exists()) {
            FileWriter writer1 = new FileWriter(file1);
            writer1.write(engobj.toJSONString());
            writer1.flush();
            writer1.close();
        } else {
            System.out.println("existEng");
        }
        Object obj = new JSONParser().parse(new FileReader(file));
        Object obj2 = new JSONParser().parse(new FileReader(file1));
        JSONObject jeng = (JSONObject) obj2;
        JSONObject jru = (JSONObject) obj;
        String[] options = {"English", "Русский"};
        File file2 = new File("Configs.properties");
        if (Integer.parseInt(Configs.readByName("Lang")) != 1 && Integer.parseInt(Configs.readByName("Lang")) != 0) {
            choose = JOptionPane.showOptionDialog(null, "Choose your language", "Click a Button", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        }
        Configs.changeByName("Lang", String.valueOf(choose));
        String chatroom;
        String settgs;
        String Bind;
        String Pivatemode;
        String succes;
        String Fail;
        String help;
        String helpdesc;
        String publicservers;
        if (choose == 1) {
            chatroom = (String) jru.get("Chatroom");
            settgs = (String) jru.get("Setting");
            Bind = (String) jru.get("Bind");
            Pivatemode = (String) jru.get("Private mode");
            succes = (String) jru.get("Succes");
            Fail = (String) jru.get("Fail");
            help = (String) jru.get("Help");
            helpdesc = (String) jru.get("HelpDesc");
            publicservers = (String) jru.get("PublicServer");
        } else if (choose == 0) {
            chatroom = (String) jeng.get("Chatroom");
            settgs = (String) jeng.get("Setting");
            Bind = (String) jeng.get("Bind");
            Pivatemode = (String) jeng.get("Private mode");
            succes = (String) jeng.get("Succes");
            Fail = (String) jeng.get("Fail");
            help = (String) jeng.get("Help");
            helpdesc = (String) jeng.get("HelpDesc");
            publicservers = (String) jeng.get("PublicServer");
        } else {
            chatroom = (String) jeng.get("Chatroom");
            settgs = (String) jeng.get("Setting");
            Bind = (String) jeng.get("Bind");
            Pivatemode = (String) jeng.get("Private mode");
            succes = (String) jeng.get("Succes");
            Fail = (String) jeng.get("Fail");
            help = (String) jeng.get("Help");
            helpdesc = (String) jeng.get("HelpDesc");
            publicservers = (String) jeng.get("PublicServer");
        }
        return new String[]{chatroom, settgs, Bind, Pivatemode, succes, Fail, help, helpdesc, publicservers};
    }
    public static Map<String, Object> getObjectMap() {
        Map<String, Object> hashMapEng = new HashMap<>();
        hashMapEng.put("Chatroom", "Chatroom");
        hashMapEng.put("Setting", "Settings");
        hashMapEng.put("Bind", "Connect");
        hashMapEng.put("Private mode", "Private mode");
        hashMapEng.put("Succes", "Successful connection");
        hashMapEng.put("Fail", "I am broked");
        hashMapEng.put("Help", "Help");
        hashMapEng.put("HelpDesc", "<html>1) To log into the server, click the connect button by entering the IP and port in the appropriate fields<br>1.1) If you leave the fields empty, the connection will occur by default on IP 127.0.0.1 Port 65433<br>2) To change the name before connecting, enter the appropriate name in the Username field before connecting<br>2.1) To change your nickname after connecting, enter /nick [Name]<br>2.2) View the list of chat participants command /user list<br>2.3) View the list of available commands /help<br>2.3) View command description /help [Command]");
        hashMapEng.put("PublicServer", "Public servers");
        return hashMapEng;
    }

    public static Map<String, Object> getStringObjectMap() {
        Map<String, Object> hashMapru = new HashMap<>();
        hashMapru.put("Chatroom", "Чат комната");
        hashMapru.put("Setting", "Настройки");
        hashMapru.put("Bind", "Подключиться");
        hashMapru.put("Private mode", "Приватный режим");
        hashMapru.put("Succes", "Подключение успешно");
        hashMapru.put("Fail", "Я сломался");
        hashMapru.put("Help", "Помощь");
        hashMapru.put("HelpDesc", "<html>1) Чтобы зайти на сервер нажмите кнопку подключиться введя IP и порт в соответствующие поля<br>1.1) Если оставить поля пустыми то подключение произойдет по умолчанию на IP 127.0.0.1 Port 65433<br>2) Чтобы сменить имя до подключения введите введите соответствующее имя в поле Username до подключения<br>2.1) Чтобы сменить ник после подключения введите /nick [Имя]<br>2.2) Посмотреть список участников чата команда /user list<br>2.3) Посмотреть список доступных команд /help<br>2.3) Посмотреть описание команды /help [Команда]");
        hashMapru.put("PublicServer", "Публичные сервера");
        return hashMapru;
    }
}
