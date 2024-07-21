package NamelessDev.client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    Socket socket;
    public static Client client;
    private Reader readr;

    private PrintWriter writer;

    public Client() {}

    public Client(String host, int port, JTextArea jTextArea) throws IOException {
        this.socket = new Socket(host, port);
        JOptionPane.showMessageDialog(Frame.getFrames()[0], "Successful connection", "Success", JOptionPane.INFORMATION_MESSAGE);
        this.readr = new Reader(this.socket, jTextArea);
        this.writer = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8), true);
        this.readr.start();
    }


    public void close() throws IOException {
        this.readr.interrupt();
        this.socket.close();
        client = null;
    }

    public static void finalConnect(JTextField ip, JTextField port, JTextArea jTextArea) throws IOException {
        Client client = new Client(ip.getText(), Integer.parseInt(port.getText()), jTextArea);
        Reader reader = new Reader(client.socket, jTextArea);
        Client.client = client;
    }

    public void send(String packet) {
        this.writer.println(packet);
    }
}
