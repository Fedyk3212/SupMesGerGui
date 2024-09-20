package feodorbal.usersytem;

import feodorbal.client.Client;
import feodorbal.localisation.Localisation;
import feodorbal.network.ServiceSocket;
import feodorbal.network.filters.LoginRegFilter;
import feodorbal.resource.exception.DisplayException;
import feodorbal.resource.exception.EthrenetException;
import feodorbal.useful.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AuthenticationWindows implements IRegistration {
    private final JFrame frame;
    private Client client;
    private final String ip;
    private final int port;
    public JTextField login, password;
    public AuthenticationWindows(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        login = new JTextField();
        password = new JTextField();
        this.frame = new JFrame(Localisation.getLocalisedName("AuthenticationWindowTile"));
        frame.setSize(300, 300);
        frame.setLayout(new GridLayout(0, 1));
        frame.setLocation(1,1);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(0x6DEAC0));
    }

    @Override
    public void Show_registration_menu() throws IOException {
        Logger.Log(AuthenticationWindows.class, "Showing_registration_menu");
        JButton[] jButtons = new JButton[2];
        jButtons[0] = new JButton(Localisation.getLocalisedName("AuthenticationWindowLoginButton"));
        jButtons[1] = new JButton(Localisation.getLocalisedName("AuthenticationWindowRegistrationButton"));

        for (JButton button : jButtons) {
            button.setSize(60, 30);
        }
        jButtons[0].setVisible(true);
        login.setVisible(true);
        password.setVisible(true);

        frame.add(new JLabel(Localisation.getLocalisedName("AuthenticationWindowLoginButton")));
        frame.add(login);
        frame.add(new JLabel(Localisation.getLocalisedName("AuthenticationWindowRegistrationButton")));
        frame.add(password);
        frame.add(jButtons[1]);
        frame.add(jButtons[0]);

        jButtons[0].addActionListener(actionEvent -> {
            try {
                Send_login_request();
            } catch (Exception e) {
                Logger.Log(AuthenticationWindows.class, "Exception " + e);
                DisplayException.display(e);
                throw new RuntimeException(e);
            }
        });
        jButtons[1].addActionListener(actionEvent -> {
            try {
                Send_registration_request();
            } catch (Exception e) {
                Logger.Log(AuthenticationWindows.class, "Exception " + e);
                throw new RuntimeException(e);
            }
        });
        frame.setVisible(true);
    }

    public void Send_registration_request() {
        Logger.Log(AuthenticationWindows.class, "Try to Sending registration request from menu");
        try {
            if (LoginRegFilter.filter(login.getText(), 30, 4) == 2 & LoginRegFilter.filter(password.getText(), 30, 4) == 2) {
                var serviceSocket = new ServiceSocket(ip, port);
                serviceSocket.sendRegReq(login.getText(), password.getText());
            }
        } catch (Exception e) {
            Logger.Log(AuthenticationWindows.class, "Exception " + e);
            DisplayException.display(e);
        }
    }

    @Override
    public void Send_login_request() throws IOException, EthrenetException {
        Logger.Log(AuthenticationWindows.class, "Try to Sending login request from menu");

        try {
            if (LoginRegFilter.filter(login.getText(), 30, -1) == 2 & LoginRegFilter.filter(password.getText(), 30, -1) == 2) {
                var serviceSocket = new ServiceSocket(ip, port);
                serviceSocket.sendLoginReq(login.getText(), password.getText());
                frame.setVisible(false);
            }
        } catch (Exception e) {
            Logger.Log(AuthenticationWindows.class, "Exception " + e);
            DisplayException.display(e);
        }
    }
}