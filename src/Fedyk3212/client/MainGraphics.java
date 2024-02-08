package Fedyk3212.client;

import Fedyk3212.config.Configs;
import Fedyk3212.cryptography.AesCrypterDecrypter;
import Fedyk3212.cryptography.KeyGen;
import Fedyk3212.network.Packets;
import Fedyk3212.network.ServiceSocket;
import Fedyk3212.resource.exception.DisplayException;
import Fedyk3212.usersytem.Registration;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static Fedyk3212.config.Localisation.choosedloc;

public class MainGraphics extends JFrame {
    public static String Pubkey;
    public static String Prikey;
    public static String ServerPublicKey;
    public static String AESKEY;
    public static String SALT;

    public static final String[] loc;

    static {
        File file = new File("Configs.properties");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Configs.makeProperties();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static {
        try {
            loc = choosedloc();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private Client client;

    public MainGraphics() throws IOException {
        super("Open_Chat_Made_by_fedyk_3213");
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(900, 400));
        getContentPane().add(tabbedPane);
        chatroomPanel(tabbedPane);
        helpPanel(tabbedPane);
        publicServers(tabbedPane);
        tabbedPane.setBackground(Color.YELLOW);
        tabbedPane.setFocusable(false);
        tabbedPane.setTabPlacement(2);

    }

    public static JButton button = new JButton(loc[2]);

    public void chatroomPanel(JTabbedPane tabbedPane) throws IOException {
        String IP = Configs.readByName("IP");
        String PORT = Configs.readByName("Port");
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

        panel2.setLayout(new GridLayout(0, 1));
        JTextField ip = new JTextField(IP, 20);
        JTextField port = new JTextField(PORT, 10);
        JCheckBox privat = new JCheckBox(loc[3]);
        panel2.add(button);
        panel2.add(ip);
        panel2.add(port);
        panel2.add(privat);
        Packets packets = new Packets();

        button.addActionListener(actionEvent -> {
            try {
                ServiceSocket socket = new ServiceSocket(ip.getText(), Integer.parseInt(port.getText()));
                ServiceSocket.sendPublicKey();
            } catch (Exception e) {
                DisplayException.display(e);
            }
            try {
                Configs.changeByName("IP", ip.getText());
                Configs.changeByName("Port", port.getText());
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
            File file3 = new File("token.txt");
            if (file3.exists()) {
                try {
                    ServiceSocket socket = new ServiceSocket(ip.getText(), Integer.parseInt(port.getText()));
                    if (MainGraphics.ServerPublicKey == null) {
                        ServiceSocket.sendPublicKey();
                    }
                    ServiceSocket.SendTokenValid();
                } catch (Exception e) {
                    DisplayException.display(e);
                }
            }
            try {
                if (client != null) {
                    client.close();
                    clearTextArea(jTextArea);
                }
                if (MainGraphics.getToken() != null) {
                    finalConnect(ip, port, jTextArea);
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
                        client.send(Packets.sendPacket(msg.getText(), MainGraphics.getToken()));
                    } catch (Exception ex) {
                        DisplayException.display(ex);
                    }
                    msg.setText("");
                }
            }
        });
    }

    void finalConnect(JTextField ip, JTextField port, JTextArea jTextArea) throws IOException {
        this.client = new Client(ip.getText(), Integer.parseInt(port.getText()), jTextArea);
        Reader reader = new Reader(client.socket, jTextArea);
    }

    static String token = null;

    public static void remToken() {
        token = null;
    }

    public static String getToken() throws IOException {
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

    void clearTextArea(JTextArea jTextArea) {
        jTextArea.selectAll();
        jTextArea.setText("");
    }

    public void helpPanel(JTabbedPane tabbedPane) {
        JPanel panel3 = new JPanel();
        tabbedPane.add(panel3, loc[6]);
        panel3.setBackground(new Color(8303193));
        JLabel label = new JLabel(loc[7]);
        panel3.add(label);
    }

    public void publicServers(JTabbedPane tabbedPane) {
        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout());
        panel4.setBackground(new Color(8303193));
        tabbedPane.add(panel4, loc[8]);
    }

    public static void main(String[] args) throws IOException, ParseException, NoSuchAlgorithmException, InterruptedException {
        AesCrypterDecrypter aesCrypterDecrypter = new AesCrypterDecrypter();
        KeyGen keyGen = new KeyGen();
        KeyGen.setPubKey();
        KeyGen.setPrivKey();
        MainGraphics frame = new MainGraphics();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(11796355));
    }

}