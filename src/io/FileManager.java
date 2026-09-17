package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileManager {
    public static boolean writer(Path location, String text){
        try {
            Files.writeString(location, text);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static Optional<String> reader(Path location){
        try  {
            return Optional.of(Files.readString(location));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
