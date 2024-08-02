package NamelessDev.network;

import NamelessDev.Graphics.panels.SettingPanel;
import NamelessDev.cryptography.AesCrypterDecrypter;
import NamelessDev.cryptography.RSACrypterDecrypter;
import NamelessDev.localisation.Localisation;
import NamelessDev.network.serverpackets.PacketParser;
import NamelessDev.resource.Succefuls;
import NamelessDev.resource.exception.DisplayException;
import NamelessDev.resource.exception.EthrenetException;
import NamelessDev.resource.exception.LoginException;
import NamelessDev.useful.Logger;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServiceSocket {
    private static Socket serviceSocket;
    private static PrintWriter servicewriter;
    private static BufferedReader servicereciver;

    public ServiceSocket(String ip, int port) throws IOException, EthrenetException {
        try {
            serviceSocket = new Socket();
            serviceSocket.connect(new InetSocketAddress(ip, port));
            serviceSocket.setSoTimeout(3000);
        } catch (RuntimeException e) {
            throw new EthrenetException();
        }
        servicereciver = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream(), StandardCharsets.UTF_8));
        servicewriter = new PrintWriter(serviceSocket.getOutputStream(), true);
        Logger.Log(ServiceSocket.class, "Service Socket Created");
    }

    public void sendRegReq(String login, String password) throws Exception {
        Logger.Log(ServiceSocket.class, "Try to send Reg req");
        servicewriter.println(Packets.registerPacket(login, password));
        String answer = servicereciver.readLine();
        if (answer.equals("Error")) {
            DisplayException.displayStringException(Localisation.getLocalisedName("ErrorRegistrationTile"),Localisation.getLocalisedName("ErrorRegistration"));
        } else if (answer.equals("OK")) {
            Succefuls.RegisterSucces();
        }
        closeServiceSocket();
    }

    public void sendLoginReq(String login, String password) throws Exception {
        Logger.Log(ServiceSocket.class, "Try to send login req");
        servicewriter.println(Packets.loginPacket(login, password));
        String answer;
        answer = servicereciver.readLine();
        closeServiceSocket();
        if (answer.equals("Error")) {
            throw new LoginException();
        } else {
            Succefuls.LoginSucces();
            Token.writeTokenFile(answer);
            SettingPanel.connectButton.doClick();
        }
    }

    public void sendTokenValid() throws Exception {
        Logger.Log(ServiceSocket.class, "Try to send Token valid");
        servicewriter.println(Packets.pingToken());
        String answer;
        answer = servicereciver.readLine();
        closeServiceSocket();
        if (answer.equals("Error")) {
           Token.delTokenFile();
        }

    }

    public boolean sendKeyCHeckRequest() throws Exception {
        Logger.Log(ServiceSocket.class, "Try to send Check Key Request");
        servicewriter.println(Packets.checkKey());
        String answer = servicereciver.readLine();
        closeServiceSocket();
        if (answer.equals("ERROR")){
            Logger.Log(ServiceSocket.class, "Key Request Status " + answer);
            NetworkVariables.setAESKEY(null);
            NetworkVariables.setSALT(null);
            return false;
        } else if (answer.equals("OK")) {
            Logger.Log( ServiceSocket.class,"Key check request status " + answer);
            return true;
        }
        return false;
    }

    public void sendPublicKey() throws Exception {
        Logger.Log(ServiceSocket.class, "Try to send Public Key");
        servicewriter.println(Packets.pingKey());
        String answer = servicereciver.readLine();
        closeServiceSocket();
        JSONObject jsonObject = PacketParser.parse(answer);
        String somestrg = (String) jsonObject.get("AKY");
        NetworkVariables.setAESKEY(RSACrypterDecrypter.Decrypt(somestrg));
        NetworkVariables.setSALT((String) jsonObject.get("SALT"));
        AesCrypterDecrypter.initCiphers(NetworkVariables.getAESKEY(), NetworkVariables.getSALT());
    }


    private static void closeServiceSocket() throws IOException {
        serviceSocket.close();
        servicereciver.close();
        servicewriter.close();
        Logger.Log(ServiceSocket.class, "Service Socket Closed");
    }

}