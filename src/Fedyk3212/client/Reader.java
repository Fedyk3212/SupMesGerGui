package Fedyk3212.client;

import Fedyk3212.cryptography.AesCrypterDecrypter;
import Fedyk3212.cryptography.CrypterDecrypter;
import Fedyk3212.network.serverpackets.PacketParser;
import Fedyk3212.resource.exception.DisplayException;
import Fedyk3212.resource.exception.NoregException;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Reader extends Thread {
    private Socket socket;

    private BufferedReader reader;

    private JTextArea jTextArea;

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
                    this.jTextArea.append("\n");
                    this.socket.close();
                    break;
                }
                JSONObject jsonObject = PacketParser.parse(data);
                String login = AesCrypterDecrypter.decrypt((String) jsonObject.get("L"));
                String message = AesCrypterDecrypter.decrypt((String) jsonObject.get("M"));
                this.jTextArea.append(login + message + "\n");
            }
        } catch (IOException e) {this.jTextArea.append("Send/Recive/Encrypt Error");} catch (Exception e) {
            DisplayException.display(e);
            throw new RuntimeException(e);
        }
    }
}