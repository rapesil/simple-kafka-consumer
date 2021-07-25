package com.live.kafka.consumer.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public static void createFile() throws IOException {
        File file = new File("src/test/resources/test.txt");
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
}
