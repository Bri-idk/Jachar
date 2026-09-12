package io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import utils.ReadOption;
import utils.UserInput;

public class FileManager {
    public static void writer(Path location, String text){
        /*!Estructura necesaria para poder usar archivos de la maquina en Java, writer seria nuestro item con los metodos dentro, la estructura de Files necesita de un parametro PATH (literalmente unpath donde trabajar). Un Charset (este caso uso UTF-8). Y metodos Standar para cuando se abre o se usa el metodo (ejemplo: CREATE -> crea el archivo si no existe, APPEND -> incerta el contenido al archivo existente). Files necesita estar en un try ya que es necesaria la recoleccion del error en caso de no poder abri el archivo
         */
        try (var writer = Files.newBufferedWriter(location, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(text);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static String reader(Path location){
        try  {
            return Files.readString(location);
        } catch (IOException e) {
            return "Error: " + e;
        }
    }
    public static void edit(Path location){
        try {
            List<String> lineas = Files.readAllLines(location);
            System.out.println("Lineas disponibles para editar: ");
            for(int i = 0; i < lineas.size(); i ++){
                System.out.println("Linea "+ i + " " + lineas.get(i));
            }
            int opcion = ReadOption.leerOpcion("Elige una linea para editar: ");
            System.out.print("Ingresa el texto nuevo: ");
            String texto = UserInput.entradaUsr.nextLine();
            lineas.set(opcion, texto);
            Files.write(location, lineas);
        }catch(IOException e){
            System.out.print("Error: "+ e);
        }
    }
}
