package feodorbal.graphics.panels;

import feodorbal.graphics.MainGraphics;
import feodorbal.client.Client;
import feodorbal.config.Configs;
import feodorbal.localisation.Localisation;
import feodorbal.network.NetworkVariables;
import feodorbal.network.ServiceSocket;
import feodorbal.network.Token;
import feodorbal.useful.Logger;
import feodorbal.usersytem.AuthenticationWindows;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class SettingPanel extends JPanel {
    public static JButton connectButton, changeLanguageButton, disconnectButton;
    static {
        try {
            disconnectButton = new JButton(Localisation.getLocalisedName("DisconnectButton"));
            changeLanguageButton = new JButton(Localisation.getLocalisedName("ChangeLanguageButton"));
            connectButton = new JButton(Localisation.getLocalisedName("ConnectButton"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String IP, PORT;
    static Configs configs;

    static {
        try {
            configs = new Configs();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            IP = configs.readByName("IP");
            PORT = configs.readByName("Port");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JTextField ip = new JTextField(IP, 20);
    public static JTextField port = new JTextField(PORT, 10);


    public SettingPanel() {
        makeSettingPanel();
    }

    void makeSettingPanel() {
        setBackground(MainGraphics.defaultPanelColor);
        setLayout(new GridLayout(0, 1));
        add(getConnectButton());
        add(ip);
        add(port);
        add(getChangeLanguageButton());
        add(getDisconnectButton());
    }

    JButton getConnectButton() {
        connectButton.addActionListener(actionEvent -> {
            try {
                var serviceSocket = new ServiceSocket(ip.getText(), Integer.parseInt(port.getText()));
                if (NetworkVariables.getAESKEY() == null) {
                    serviceSocket.sendPublicKey();
                }
                serviceSocket = new ServiceSocket(ip.getText(), Integer.parseInt(port.getText()));
                if (!serviceSocket.sendKeyCHeckRequest()) {
                    serviceSocket = new ServiceSocket(ip.getText(), Integer.parseInt(port.getText()));
                    serviceSocket.sendPublicKey();
                }
                configs.changeByName("IP", ip.getText());
                configs.changeByName("Port", port.getText());
                File tokenfile = new File(Token.path);
                if (tokenfile.exists()) {
                    serviceSocket = new ServiceSocket(ip.getText(), Integer.parseInt(port.getText()));
                    serviceSocket.sendTokenValid();
                    if (Client.client != null) {
                        Client.client.close();
                        ChatRoomPanel.clearTextArea();
                    }

                }
                if (Token.getToken() != null) {
                    Client.finalConnect(ip, port, ChatRoomPanel.getTextArea());
                } else {
                    AuthenticationWindows registration = new AuthenticationWindows(ip.getText(), Integer.parseInt(port.getText()));
                    registration.Show_registration_menu();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(Frame.getFrames()[0], "Error " + e, "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        return connectButton;
    }

    JButton getDisconnectButton() {
        disconnectButton.addActionListener(actionEvent -> {
            try {
                Client.client.close();
                ChatRoomPanel.clearTextArea();
            } catch (Exception e) {
                Logger.Log(SettingPanel.class, "Disconnection Failed");
            }
        });
        return disconnectButton;
    }

    JButton getChangeLanguageButton() {
        changeLanguageButton.addActionListener(actionEvent -> {
            try {
                Localisation.changeLanguage();
            } catch (IOException | ParseException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
        Logger.Log(SettingPanel.class, "Change Language Button Pressed");
        return changeLanguageButton;
    }
}
