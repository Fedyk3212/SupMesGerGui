package NamelessDev.useful;

import java.util.ArrayList;

public class ChatStringFormatter {

    public static String format(String msg) {
        String fmsg = lineWrap(msg, 30);
        return fmsg;
    }

    private static String lineWrap(String msg, int maxLineLenght) {
        ArrayList<String> tempList = new ArrayList<>();
        return "das";
    }

    public static void main(String[] args) {
        format("231231231231232312312312312323123123123123  123213 21321321321 312 312 321312321");
    }
}
