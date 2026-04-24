package com.restaurant;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    public static void appendToFile(String filename, String text) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            bw.write(text);
            bw.newLine();
        }
    }
}
