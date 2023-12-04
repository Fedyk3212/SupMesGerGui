package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JTextArea;

public class ChatClient {
    private Socket socket;

    private Reader readr;

    private PrintWriter writer;

    public ChatClient(String host, int port, JTextArea jTextArea) throws IOException {
        this.socket = new Socket(host, port);
        this.readr = new Reader(this.socket, jTextArea);
        this.writer = new PrintWriter(this.socket.getOutputStream(), true);
        this.readr.start();
    }

    public void close() throws IOException {
        this.readr.interrupt();
        this.socket.close();
    }

    public void send(String msg) {
        this.writer.println(msg);
    }
}
