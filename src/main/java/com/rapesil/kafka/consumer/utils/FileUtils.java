package com.rapesil.kafka.consumer.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    private static final String filename = "src/test/resources/test.txt";

    public static void createFile() throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    public static void writeInFile(String content, String filePath) {
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(content);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile() throws IOException {
        Path fileName = Path.of(filename);

        return Files.readString(fileName);
    }

    public static boolean fileExists() {
        return new File("src/test/resources/test.txt").exists();
    }

    public static void deleteFile() {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }
}
