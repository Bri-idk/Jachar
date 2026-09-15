package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {
    public static boolean writer(Path location, String text){
        try {
            Files.writeString(location, text);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String reader(Path location){
        try  {
            return Files.readString(location);
        } catch (IOException e) {
            return "Error: " + e;
        }
    }
}
