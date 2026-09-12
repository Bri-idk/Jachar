import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //mensaje de saludo
        System.out.println("Hi");
        //ruta de prueba para inicio del proyecto
        /*
        * Path es como una clase con metodos dentro usar Path no requiere usar un new Path, ya que no es una funcion, usamos el metodo Path.of() <- Esto obtiene el path a abrir (podriamos hacerlo dinamico obteniendo el path directamente del user!)
        */
        Path location = Path.of("test/nota.txt");
        //texto generico para consola
        System.out.print("Ingresa el texto que quieres guardar: ");
        //*escaner para la entrada de texto
        Scanner entradaUsr = new Scanner(System.in);
        //guardamos el texto
        String text = entradaUsr.nextLine();

        //!Estructura necesaria para poder usar archivos de la maquina en Java, writer seria nuestro item con los metodos dentro, la estructura de Files necesita de un parametro PATH (literalmente unpath donde trabajar). Un Charset (este caso uso UTF-8). Y metodos Standar para cuando se abre o se usa el metodo (ejemplo: CREATE -> crea el archivo si no existe, APPEND -> incerta el contenido al archivo existente). Files necesita estar en un try ya que es necesaria la recoleccion del error en caso de no poder abri el archivo
        try (var writer = Files.newBufferedWriter(location, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(text);
            writer.newLine();
            writer.write("test");
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

}