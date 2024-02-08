package Fedyk3212.network;

import Fedyk3212.client.MainGraphics;
import Fedyk3212.cryptography.AesCrypterDecrypter;
import Fedyk3212.cryptography.CrypterDecrypter;
import Fedyk3212.network.serverpackets.PacketParser;
import Fedyk3212.resource.Errors;
import Fedyk3212.resource.Succefuls;
import Fedyk3212.resource.exception.EthrenetException;
import Fedyk3212.resource.exception.LoginException;
import Fedyk3212.resource.exception.RegistrationException;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServiceSocket {
    private Socket socket;
    private static PrintWriter servicewriter;
    private static BufferedReader servicereciver;

    public ServiceSocket(String ip, int port) throws IOException, EthrenetException {
        try {
            this.socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 5000);
        } catch (RuntimeException e) {
            throw new EthrenetException();
        }
        servicereciver = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        servicewriter = new PrintWriter(socket.getOutputStream(), true);
    }

    public static void sendRegReq(String login, String password) throws Exception {
        servicewriter.println(Packets.registerPacket(login, password));
        String answer = servicereciver.readLine();
        if (answer.equals("Error")) {
            throw new RegistrationException();
        } else if (answer.equals("OK")) {
            Succefuls.RegisterSucces();
        }
        System.out.println(answer);
    }

    public static void sendLoginReq(String login, String password) throws Exception {
        servicewriter.println(Packets.loginPacket(login, password));
        String answer = null;
        do {
            answer = servicereciver.readLine();
        } while (answer == null);
        if (answer.equals("Error")) {
            throw new LoginException();
        } else {
            Succefuls.LoginSucces();
            File file = new File("token.txt");
            Writer writer = new FileWriter(file);
            writer.write(AesCrypterDecrypter.decrypt(answer));
            writer.flush();
            writer.close();
            MainGraphics.button.doClick();
        }
    }

    public static void SendTokenValid() throws Exception {
        servicewriter.println(Packets.pingToken());
        String answer = null;
        while (answer == null) {
            answer = servicereciver.readLine();
        }
        if (answer.equals("Error")) {
            File file = new File("token.txt");
            if (file.delete()) {
                System.out.println("File deleted");
                MainGraphics.remToken();
            } else {
                System.out.println("File not found");
            }
        }
    }

    public static void sendPublicKey() throws Exception {
        servicewriter.println(Packets.pingKey());
        String answer = null;
        while (answer == null) {
            answer = servicereciver.readLine();
        }
        JSONObject jsonObject = PacketParser.parse(answer);
        String somestrg = (String) jsonObject.get("AKY");
        MainGraphics.AESKEY = CrypterDecrypter.Decrypt(somestrg);
        MainGraphics.SALT = (String) jsonObject.get("SALT");
        AesCrypterDecrypter.initCiphers(MainGraphics.AESKEY, MainGraphics.SALT);
    }

    public static void sendValidData(String data) throws IOException {
        servicewriter.println(data);
        String answer = null;
        while (answer == null) {
            answer = servicereciver.readLine();
        }
        if (answer.equals("Error")) {
            Errors.Show_validation_error();
        }
    }
}