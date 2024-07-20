package NamelessDev.localisation;

import NamelessDev.useful.Logger;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

public class UnpackLangFiles {

    static String TargetPath = "ConfigsAndLoc/localisation/";
    static String respath = "./default_lang/";
    public static void unpack() throws IOException, URISyntaxException {
        ArrayList<String> localPaths = new ArrayList<>();
        ArrayList<InputStream> streamArrayList = new ArrayList<>();
        ArrayList<String> innerPaths = pathList();
        for (int i = 0; i != innerPaths.size(); i++) {
            streamArrayList.add(UnpackLangFiles.class.getResourceAsStream(innerPaths.get(i)));
        }
        for (int i = 0; i != innerPaths.size(); i++) {
            localPaths.add(innerPaths.get(i).substring(innerPaths.get(i).lastIndexOf("default_lang/")).replace("default_lang/", TargetPath));
        }
        int cnt = 0;
        File file = new File(TargetPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        for (int i = 0; i != streamArrayList.size(); i++) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[512];
            while (true) {
                assert streamArrayList.get(i) != null;
                if ((nRead = streamArrayList.get(i).read(data, 0, data.length)) == -1) break;
                buffer.write(data, 0, nRead);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(localPaths.get(cnt));
            fileOutputStream.write(buffer.toByteArray());
            cnt++;
        }
        Logger.Log(UnpackLangFiles.class, "Localisation files unpacked");
    }

    static FileSystem fileSystem = null;

    public static ArrayList<String> pathList() throws IOException, URISyntaxException {
        ArrayList<String> pathList = new ArrayList<>();
        URI uri = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(respath)).toURI();
        Path myPath;
        myPath = Paths.get(uri);
        Stream<Path> walk = Files.walk(myPath, 1);
        for (Iterator<Path> it = walk.iterator(); it.hasNext(); ) {
            String path = it.next().toString();
            if (path.endsWith(".lang") || path.endsWith(".txt")) {
                pathList.add(path.substring(path.lastIndexOf("/default_lang")));
            }
        }
        return pathList;
    }
}
