package NamelessDev.Graphics.panels;

import NamelessDev.Graphics.MainGraphics;
import NamelessDev.client.Client;
import NamelessDev.network.Packets;
import NamelessDev.network.Token;
import NamelessDev.network.filters.SendFilter;
import NamelessDev.resource.exception.DisplayException;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.nio.charset.StandardCharsets;


public class ChatRoomPanel extends JPanel {
    private static final JTextArea jTextArea = new JTextArea();
    private static int maxMessageLimit = 450;

    public static void setMaxMessageLImit(int limit) {
        maxMessageLimit = limit;
    }
    public ChatRoomPanel(){
        setBackground(MainGraphics.defaultPanelColor);
        setLayout(new BorderLayout());
        add(new JScrollPane(ChatTextArea()), "Center");
        add(messageTextField(), "South");
    }

    JTextArea ChatTextArea(){
        DefaultCaret caret = (DefaultCaret) jTextArea.getCaret();
        caret.setUpdatePolicy(2);
        jTextArea.setBackground(MainGraphics.defaultPanelColor);
        jTextArea.setEditable(false);  
        jTextArea.setFont(new Font("TRUETYPE_FONT", Font.BOLD, 20));
        jTextArea.setForeground(new Color(0xFFFFFF));
        jTextArea.setSelectedTextColor(Color.cyan);
        jTextArea.setLineWrap(true);
        return jTextArea;
    }

    JTextField messageTextField(){
        JTextField msg = new JTextField();
        msg.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    try {
                        int filtStat = SendFilter.filter((msg.getText()), maxMessageLimit, -1);
                        if (filtStat == 2) {
                            Client.client.send(Packets.sendPacket(new String(msg.getText().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), Token.getToken()));
                            msg.setText("");
                        }
                        if (filtStat != 1) {
                            msg.setText("");
                        }
                    } catch (Exception ex) {
                        DisplayException.display(ex);
                    }
                }
            }
        });
        return msg;
    }
    public static void clearTextArea() {
        jTextArea.selectAll();
        jTextArea.setText("");
    }
    public static JTextArea getTextArea(){
        return jTextArea;
    }
}
