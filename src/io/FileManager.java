package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {
    public static boolean writer(Path location, String text){
        /*!Estructura necesaria para poder usar archivos de la maquina en Java, writer seria nuestro item con los metodos dentro, la estructura de Files necesita de un parametro PATH (literalmente unpath donde trabajar). Un Charset (este caso uso UTF-8). Y metodos Standar para cuando se abre o se usa el metodo (ejemplo: CREATE -> crea el archivo si no existe, APPEND -> incerta el contenido al archivo existente). Files necesita estar en un try ya que es necesaria la recoleccion del error en caso de no poder abri el archivo
         */
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
