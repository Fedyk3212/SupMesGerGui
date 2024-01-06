package Fedyk3212.Client;

import Fedyk3212.Net_Work.Packets;
import Fedyk3212.User_System.Registration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main_Graphics extends JFrame {

    public final String[] loc = choosedloc();
    private Client client;

    public Main_Graphics() throws IOException, ParseException {
        super("SuperMesGer_ClientGUI__Made_by_fedyk_3213");
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(900, 400));
        getContentPane().add(tabbedPane);
        chatroompanel(tabbedPane);
        helppanel(tabbedPane);
        publicservers(tabbedPane);
        tabbedPane.setBackground(Color.YELLOW);
        tabbedPane.setFocusable(false);
        tabbedPane.setTabPlacement(2);

    }


    public void chatroompanel(JTabbedPane tabbedPane) throws IOException, ParseException {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("IP", "IP");
        hashMap.put("Port", "Port");
        hashMap.put("Username", "JUsername");
        JSONObject config = new JSONObject(hashMap);
        File file = new File("Config.json");
        if (!file.exists()) {
            FileWriter writer = new FileWriter(file);
            writer.write(config.toJSONString());
            writer.flush();
            writer.close();
        } else {
            System.out.println("ConfigExist");
        }

        boolean connected = false;
        Object cofobj = new JSONParser().parse(new FileReader(file));
        JSONObject jcof = (JSONObject) cofobj;
        String IP = (String) jcof.get("IP");
        String PORT = (String) jcof.get("Port");
        String Username = (String) jcof.get("Username");

        JPanel panel = new JPanel();
        tabbedPane.add(panel, loc[0]);
        panel.setBackground(new Color(8303193));
        JTextArea jTextArea = new JTextArea();
        DefaultCaret caret = (DefaultCaret) jTextArea.getCaret();
        caret.setUpdatePolicy(2);
        jTextArea.setBackground(new Color(8303193));
        jTextArea.setEditable(false);
        jTextArea.setSelectedTextColor(Color.cyan);
        final JTextField msg = new JTextField();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(jTextArea), "Center");
        panel.add(msg, "South");
        JPanel panel2 = new JPanel();
        tabbedPane.add(panel2, loc[1]);
        panel2.setBackground(new Color(8303193));
        JButton button = new JButton(loc[2]);
        panel2.setLayout(new GridLayout(0, 1));
        JTextField ip = new JTextField(IP, 20);
        JTextField port = new JTextField(PORT, 10);
        JTextField username = new JTextField(Username);
        JCheckBox privat = new JCheckBox(loc[3]);
        panel2.add(button);
        panel2.add(ip);
        panel2.add(port);
        panel2.add(username);
        panel2.add(privat);
        Packets packets = new Packets();
        button.addActionListener(actionEvent -> {
            hashMap.replace("IP", ip.getText());
            hashMap.replace("Port", port.getText());
            hashMap.replace("Username", username.getText());
            JSONObject configu = new JSONObject(hashMap);
            File file1 = new File("Config.json");
            FileWriter fileWriter;
            try {
                fileWriter = new FileWriter(file1);
                fileWriter.write(configu.toJSONString());
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                if (client != null) {
                    client.close();
                }
                if (get_token() != null) {
                    final_connect(ip, port, jTextArea);
                } else {
                    Registration registration = new Registration(ip.getText(), Integer.parseInt(port.getText()));
                    registration.Show_registration_menu();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(Frame.getFrames()[0], "Error " + e, "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        msg.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    try {
                        client.send(Packets.send_packet(msg.getText(), get_token()));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    msg.setText("");
                }
            }
        });
    }

    void final_connect(JTextField ip, JTextField port, JTextArea jTextArea) throws IOException {
        this.client = new Client(ip.getText(), Integer.parseInt(port.getText()), jTextArea);
        Reader reader = new Reader(client.socket, jTextArea);
    }
String token = null;
    String get_token() throws IOException {
        try {
            if (token == null) {
                BufferedReader reader = new BufferedReader(new FileReader("token.txt"));
                token = reader.readLine();
                reader.close();
                return token;
            }
        } catch (IOException ex) {
            return null;
        }
        return token;
    }

    void objectDestroy(Object o) {
        o = null;
        System.gc();
    }

    public void helppanel(JTabbedPane tabbedPane) {
        JPanel panel3 = new JPanel();
        tabbedPane.add(panel3, loc[6]);
        panel3.setBackground(new Color(8303193));
        JLabel label = new JLabel(loc[7]);
        panel3.add(label);
    }

    public void publicservers(JTabbedPane tabbedPane) {
        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout());
        panel4.setBackground(new Color(8303193));
        tabbedPane.add(panel4, loc[8]);

    }

    // Локализация
    public static int choose = 3;

    public String[] choosedloc() throws IOException, ParseException {
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
        choose = JOptionPane.showOptionDialog(null, "Choose your language", "Click a Button", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
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

    private static Map<String, Object> getObjectMap() {
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

    private static Map<String, Object> getStringObjectMap() {
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

    public static void main(String[] args) throws IOException, ParseException {
        Main_Graphics frame = new Main_Graphics();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(11796355));
    }

}