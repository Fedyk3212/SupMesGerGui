package Fedyk3212.usersytem;

import Fedyk3212.client.Client;
import Fedyk3212.network.ServiceSocket;
import Fedyk3212.resource.exception.DisplayException;
import Fedyk3212.resource.exception.EthrenetException;
import com.sun.istack.internal.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Registration implements IRegistration {
    private final JFrame frame;
    private Client client;
    private final String ip;
    private final int port;
    public JTextField login, password;
    public Registration(@NotNull String ip, int port) {
        this.ip = ip;
        this.port = port;
        login = new JTextField();
        password = new JTextField();
        this.frame = new JFrame("Login/Registration");
        frame.setSize(300, 300);
        frame.setLayout(new GridLayout(0, 1));
        frame.setLocation(1,1);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(0x6DEAC0));
    }

    @Override
    public void Show_registration_menu() {
        JButton[] jButtons = new JButton[2];
        jButtons[0] = new JButton("Login");
        jButtons[1] = new JButton("Registration");

        for (JButton button : jButtons) {
            button.setSize(60, 30);
        }
        jButtons[0].setVisible(true);
        login.setVisible(true);
        password.setVisible(true);

        frame.add(new JLabel("Login"));
        frame.add(login);
        frame.add(new JLabel("Password"));
        frame.add(password);
        frame.add(jButtons[1]);
        frame.add(jButtons[0]);
        jButtons[0].addActionListener(actionEvent -> {
            try {
                Send_login_request();
            } catch (Exception e) {
                DisplayException.display(e);
                throw new RuntimeException(e);
            }
        });
        jButtons[1].addActionListener(actionEvent -> {
            try {
                Send_registration_request();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        frame.setVisible(true);
    }

    public void Send_registration_request() throws IOException {
        try {
            ServiceSocket serviceSocket = new ServiceSocket(ip, port);
            ServiceSocket.sendRegReq(login.getText(), password.getText());
        } catch (Exception e) {
            DisplayException.display(e);
        }
    }

    @Override
    public void Send_login_request() throws IOException, EthrenetException {
        ServiceSocket serviceSocket = new ServiceSocket(ip, port);
        try {
            ServiceSocket.sendLoginReq(login.getText(), password.getText());
            frame.setVisible(false);
        } catch (Exception e) {
            DisplayException.display(e);
        }
    }
}