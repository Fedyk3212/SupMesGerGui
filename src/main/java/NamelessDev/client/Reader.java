package NamelessDev.client;

import NamelessDev.cryptography.AesCrypterDecrypter;
import NamelessDev.network.serverpackets.PacketParser;
import NamelessDev.resource.exception.DisplayException;
import NamelessDev.resource.exception.NoregException;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Reader extends Thread {
    private final Socket socket;

    private BufferedReader reader;

    private final JTextArea jTextArea;

    public Reader(Socket socket, JTextArea jTextArea) {
        this.jTextArea = jTextArea;
        this.socket = socket;
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        }catch (Exception e){
            DisplayException.display(e);
        }

    }

    public void run() {
        try {
            while (true) {
                String data = this.reader.readLine();
                if (Objects.equals(data, "NOREG")){
                    this.socket.close();
                    throw new NoregException();
                }
                if (data == null) {
                    assert this.jTextArea != null;
                    this.jTextArea.append("\n");
                    this.socket.close();
                    break;
                }
                try {
                    JSONObject jsonObject = PacketParser.parse(data);
                    String login = AesCrypterDecrypter.decrypt((String) jsonObject.get("L"));
                    String message = AesCrypterDecrypter.decrypt((String) jsonObject.get("M"));
                    this.jTextArea.append(login + " " + message + "\n");
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {

        }
    }
}