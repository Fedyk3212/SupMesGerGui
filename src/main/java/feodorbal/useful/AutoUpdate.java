package feodorbal.useful;


import feodorbal.resource.exception.DisplayException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpConnectTimeoutException;

public class AutoUpdate {
    private static final URL url;

    static {
        try {
            url = new URL("https://github.com/feodorbal/Open-Chat/releases/latest/download/OpenChat.jar");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update() {
        Logger.Log(AutoUpdate.class, "Try to AutoUpdate");
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            checkStatus(connection.getResponseCode());
            writeUpdateToFIle(connection.getInputStream());
            System.exit(0);
        } catch (Exception e) {
            DisplayException.display(e);
        }
    }

    static void checkStatus(int response) throws HttpConnectTimeoutException {
        Logger.Log(AutoUpdate.class, "Response is " + response);
        if (response != 200) {
            throw new HttpConnectTimeoutException(String.valueOf(response));
        }
    }

    static void writeUpdateToFIle(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[2048];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        File file = new File("./OpenChat");
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        } else
            file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.close();
        fileOutputStream.write(buffer.toByteArray());
    }
}
