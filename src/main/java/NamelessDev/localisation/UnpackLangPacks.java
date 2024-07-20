package NamelessDev.localisation;

import NamelessDev.config.WorkWithProperties.PropertiesHandler;
import NamelessDev.useful.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static NamelessDev.localisation.Localisation.langsPath;

public class UnpackLangPacks {
    static String TARGETPATH = "ConfigsAndLoc/localisation/";

    static void unpack() throws IOException {
        if (detect()) {
            ArrayList<ZipInputStream> validZips = validate();
            String[] rightLangPacks = new String[validZips.size()];

            HashMap<String, Object>[] hashMaps = new HashMap[validZips.size()];
            for (int i = 0; i < validZips.size(); i++) {
                hashMaps[i] = getLangPackParameters(validZips.get(i));
                boolean file = new File(TARGETPATH + ".temp.txt").delete();
            }
            for (int i = 0; i < rightLangPacks.length; i++) {
                rightLangPacks[i] = validZips.toString();
            }
        }
    }

    private static boolean detect() {
        String[] options = getPosibleLangPackPathes();
        return options.length != 0;
    }

    private static String[] getPosibleLangPackPathes() {
        File[] files = new File(langsPath).listFiles();
        ArrayList<String> options = new ArrayList<>();
        for (int i = 0; i != Objects.requireNonNull(files).length; i++) {
            if (files[i].toString().endsWith(".zip")) {
                options.add(files[i].toString().substring(files[i].toString().lastIndexOf("localisation")).replace("localisation/", ""));
            }
        }
        String[] optionsArray = new String[options.size()];
        for (int i = 0; i != options.size(); i++) {
            optionsArray[i] = options.get(i);
        }
        return optionsArray;
    }

    private static boolean validateFileStruct(ZipInputStream zipInputStream) throws IOException {
        ArrayList<String> foundedNames = new ArrayList<>();
        ZipEntry entry;
        while ((entry = zipInputStream.getNextEntry()) != null) {
            foundedNames.add(entry.getName());
        }
        for (int i = 0; i != 2; i++) {
            if (!foundedNames.get(i).startsWith("default_lang/") && !foundedNames.get(i).startsWith("icons/") && !foundedNames.get(i).startsWith("desc.properties")) {
                Logger.Log(UnpackLangPacks.class, "File structure not validated");
                return false;
            }
        }
        zipInputStream.closeEntry();
        zipInputStream.close();
        Logger.Log(UnpackLangPacks.class, "File Structure validated");
        return true;
    }

    private static ArrayList<ZipInputStream> validate() {
        String[] options = getPosibleLangPackPathes();
        ArrayList<ZipInputStream> validZips = new ArrayList<>();
        try {
            ZipInputStream[] zipInputStreams = new ZipInputStream[options.length];
            for (int i = 0; i < options.length; i++) {
                zipInputStreams[i] = new ZipInputStream(Files.newInputStream(Paths.get(TARGETPATH + options[i])));
            }
            for (int i = 0; i < zipInputStreams.length; i++) {
                if (validateFileStruct(zipInputStreams[i])) {
                    validZips.add(new ZipInputStream(Files.newInputStream(Paths.get(TARGETPATH + options[i]))));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return validZips;
    }

    private static HashMap<String, Object> getLangPackParameters(ZipInputStream zipInputStream) throws IOException {
        File file = new File(TARGETPATH + ".temp.txt");
        file.delete();
        FileOutputStream fileOutputStream = new FileOutputStream(TARGETPATH + ".temp.txt");
        PropertiesHandler description = new PropertiesHandler(TARGETPATH + ".temp.txt");
        ZipEntry entry;
        do {
            entry = zipInputStream.getNextEntry();
            assert entry != null;
            if (entry.getName().equals("desc.properties")) {
                break;
            }
        } while (zipInputStream.getNextEntry() != null);
        byte[] buffer = new byte[512];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int nRead = 0;
        while (true) {
            nRead = zipInputStream.read(buffer, 0, buffer.length);
            if (nRead == -1){break;}
            byteArrayOutputStream.write(buffer, 0, nRead);
        }
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.flush();
        fileOutputStream.close();
        zipInputStream.close();
        description.fillDefParam(new String[]{"Name","Author","Description","Version"}, "Nope");
        return description.readAll();
    }
}

