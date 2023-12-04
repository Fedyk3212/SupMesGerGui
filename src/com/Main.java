package com;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main extends JFrame {
    private ChatClient client;

    public final String[] loc = choosedloc();
    public Main() throws IOException, ParseException {
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

    public void chatroompanel(JTabbedPane tabbedPane) {

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
        JTextField ip = new JTextField("IP", 20);
        JTextField port = new JTextField("PORT", 10);
        JTextField username = new JTextField("Username");
        JCheckBox privat = new JCheckBox(loc[3]);
        panel2.add(button);
        panel2.add(ip);
        panel2.add(port);
        panel2.add(username);
        panel2.add(privat);

        msg.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == 10) {
                    Main.this.client.send(msg.getText());
                    msg.setText("");
                }
            }
        });
        button.addActionListener(actionEvent -> {
            int porrtp;
            try {
                porrtp = Integer.parseInt(port.getText());
            } catch (Exception e) {
                porrtp = 65433;
            }
            String ipp = ip.getText();
            if (Objects.equals(ipp, "IP")){ipp = "127.0.0.1";}
            if (this.client != null)
                try {
                    this.client.close();
                    jTextArea.selectAll();
                    jTextArea.replaceSelection("");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            try {
                this.client = new ChatClient(ipp, porrtp, jTextArea);
                this.client.send("/nick " + username.getText());
                JOptionPane.showMessageDialog(panel2, loc[4], "Succes", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(panel2, e.getMessage(), loc[5], JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public void helppanel(JTabbedPane tabbedPane) {
        JPanel panel3 = new JPanel();
        tabbedPane.add(panel3, loc[6]);
        panel3.setBackground(new Color(8303193));
        JLabel label = new JLabel(loc[7]);
        panel3.add(label);
    }
    public void publicservers(JTabbedPane tabbedPane){
        JPanel panel4 = new JPanel();
        JButton update = new JButton();
        panel4.setLayout(new GridLayout());
        panel4.setBackground(new Color(8303193));
        tabbedPane.add(panel4, loc[8]);

    }
    public Object createbutton(){
        return null;
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
        if (!file1.exists()){
            FileWriter writer1 = new FileWriter(file1);
            writer1.write(engobj.toJSONString());
            writer1.flush();
            writer1.close();
        }
        else {
            System.out.println("existEng");
        }
            Object obj = new JSONParser().parse(new FileReader(file));
            Object obj2 = new JSONParser().parse(new FileReader(file1));
            JSONObject jeng = (JSONObject) obj2;
            JSONObject jru = (JSONObject) obj;
        String[] options = {"English", "Русский"};
        choose = JOptionPane.showOptionDialog(null,"Choose your language", "Click a Button", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        String chatroom = null;
        String settgs = null;
        String Bind = null;
        String Pivatemode = null;
        String succes = null;
        String Fail = null;
        String help = null;
        String helpdesc = null;
        String publicservers = null;
        if (choose == 1) {
            chatroom = (String) jru.get("Chatroom");
            settgs = (String) jru.get("Setting");
            Bind = (String) jru.get( "Bind");
            Pivatemode = (String) jru.get("Private mode");
            succes = (String) jru.get("Succes");
            Fail = (String) jru.get("Fail");
            help = (String) jru.get("Help");
            helpdesc = (String) jru.get("HelpDesc");
            publicservers = (String) jru.get("PublicServer");
        }else if (choose == 0){
            chatroom = (String) jeng.get("Chatroom");
            settgs = (String) jeng.get("Setting");
            Bind = (String) jeng.get( "Bind");
            Pivatemode = (String) jeng.get("Private mode");
            succes = (String) jeng.get("Succes");
            Fail = (String) jeng.get("Fail");
            help = (String) jeng.get("Help");
            helpdesc = (String) jeng.get("HelpDesc");
            publicservers = (String) jeng.get("PublicServer");
        }
        else {
            chatroom = (String) jeng.get("Chatroom");
            settgs = (String) jeng.get("Setting");
            Bind = (String) jeng.get( "Bind");
            Pivatemode = (String) jeng.get("Private mode");
            succes = (String) jeng.get("Succes");
            Fail = (String) jeng.get("Fail");
            help = (String) jeng.get("Help");
            helpdesc = (String) jeng.get("HelpDesc");
            publicservers = (String) jeng.get("PublicServer");
        }
        return new String[]{chatroom, settgs, Bind, Pivatemode, succes, Fail, help,helpdesc,publicservers};
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
        hashMapru.put("Chatroom","Чат комната");
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
        Main frame = new Main();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(11796355));
    }

}
