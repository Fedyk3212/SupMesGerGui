package Fedyk3212.User_System;

import Fedyk3212.Client.Client;
import Fedyk3212.Net_Work.Packets;
import Fedyk3212.Net_Work.ServiceSocket;
import Fedyk3212.Resources.Errors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Registration implements IRegistration {
    private JFrame frame;
    private Client client;
    private String ip;
    private int port;
    public JTextField login, password;
    public Registration(String ip, int port) {
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
            } catch (IOException e) {
                Errors.Show_Ethernet_error();
                throw new RuntimeException(e);
            }
        });
        jButtons[1].addActionListener(actionEvent -> {
            try {
                Send_registration_request();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        frame.setVisible(true);
    }

    public void Send_registration_request() throws IOException {
        ServiceSocket serviceSocket = new ServiceSocket(ip, port);
        try {
            ServiceSocket.sendRegReq(login.getText(), password.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void Send_login_request() throws IOException {
        ServiceSocket serviceSocket = new ServiceSocket(ip, port);
        try {
            ServiceSocket.sendLoginReq(login.getText(), password.getText());
            frame.setVisible(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}