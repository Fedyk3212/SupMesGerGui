package Fedyk3212.Client;

import Fedyk3212.Net_Work.Packets;
import Fedyk3212.Net_Work.ServiceSocket;
import com.sun.istack.internal.NotNull;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import javax.swing.*;

public class Client {
    Socket socket;
    private Reader readr;

    private PrintWriter writer;

    public Client(String host, int port, JTextArea jTextArea) throws IOException {
        this.socket = new Socket(host, port);
        JOptionPane.showMessageDialog(Frame.getFrames()[0], "Succesful connection", "Succes", JOptionPane.INFORMATION_MESSAGE);
        File file = new File("token.txt");
        this.readr = new Reader(this.socket, jTextArea);
        this.writer = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8), true);
        this.readr.start();
    }

    public void close() throws IOException {
        this.readr.interrupt();
        this.socket.close();
    }

    public void send(@NotNull String packet) {
        this.writer.println(packet);
    }
}
