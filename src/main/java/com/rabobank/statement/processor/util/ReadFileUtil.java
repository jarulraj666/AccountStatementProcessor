package com.rabobank.statement.processor.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadFileUtil {
    public static String readFileAsString(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        // Read the file contents as a byte array
        byte[] fileBytes = Files.readAllBytes(path);
        // Convert the byte array to a String
        return new String(fileBytes);
    }
}
