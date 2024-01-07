package Fedyk3212.Net_Work;

import Fedyk3212.Client.Main_Graphics;
import Fedyk3212.Resources.Errors;
import Fedyk3212.Resources.Succefuls;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServiceSocket {
    private Socket socket;
    private static PrintWriter servicewriter;
    private static BufferedReader servicereciver;

    public ServiceSocket(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
        servicereciver = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        servicewriter = new PrintWriter(socket.getOutputStream(), true);
    }

    public static void sendRegReq(String login, String password) throws IOException {
        servicewriter.println(Packets.register_packet(login, password));
        String answer = servicereciver.readLine();
        if (answer.equals("Error")) {
            Errors.Show_registration_error();
        } else if (answer.equals("OK")) {
            Succefuls.RegisterSucces();
        }
        System.out.println(answer);
    }

    public static void sendLoginReq(String login, String password) throws IOException {
        servicewriter.println(Packets.login_packet(login, password));
        String answer = servicereciver.readLine();
        if (answer.equals("Error")) {
            Errors.Show_login_error();
        } else {
            Succefuls.LoginSucces();
            File file = new File("token.txt");
            Writer writer = new FileWriter(file);
            writer.write(answer);
            writer.flush();
            writer.close();
        }
    }
    public static void SendTokenValid() throws IOException {
        servicewriter.println(Packets.ping_token());
        String answer = null;
        while (answer == null){
            answer = servicereciver.readLine();
        }
        if (answer.equals("Error")){
            File file = new File("token.txt");
            if (file.delete()){
                System.out.println("File deleted");
                Main_Graphics.RemToken();
            }
            else {
                System.out.println("File not found");
            }
        }
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
