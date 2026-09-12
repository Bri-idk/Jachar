import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Scanner;

public class Main {
    //*escaner para la entrada de texto
    private static final Scanner entradaUsr = new Scanner(System.in);

    public static void main(String[] args) {

        /* *
        *mensaje de saludo y solicitud de nombre del archivo, en realidad no es necesario solicitar el .txt, puedo usar la concadenacion (como ya se esta haciendo xd, un poco silly de mi parte) */
        System.out.println("Hi\nPor favor ingresa el nombre del archivo en el que quieres trabajar: ");

        String usrLocation = entradaUsr.nextLine();
        //ruta de prueba para inicio del proyecto
        /*
        * Path es como una clase con metodos dentro usar Path no requiere usar un new Path, ya que no es una funcion, usamos el metodo Path.of() <- Esto obtiene el path a abrir (podriamos hacerlo dinamico obteniendo el path directamente del user!)
        */


        Path location = Path.of("test/" + usrLocation + ".txt");


        //guardamos el texto

        int opcion;
        boolean run = true;

        while (run){
            System.out.print("Opciones disponibles\n1 - Leer\n2 - Escribir\n");
            opcion = leerOpcion("Ingresa una opcion: ");
            switch (opcion) {
                case 1 -> {
                    leector(location);
                    run = false;
                }

                case 2 -> {
                    //texto generico para consola
                    System.out.print("Ingresa el texto que quieres guardar: ");
                    String text = entradaUsr.nextLine();
                    escritor(location, text);
                    run = false;
                }
                default -> System.out.print("Opcion no valida");
            }

        }

    }

    public static int leerOpcion(String mensaje){
        while(true){
            System.out.print(mensaje);
            String opcion = entradaUsr.nextLine().trim();

            try {
                return Integer.parseInt(opcion);
            } catch (NumberFormatException e){
                System.out.println("El valor obtenido no es valido.\nRevisa las opciones disponibles!");
            }

        }

    }

    public static void escritor(Path location, String text){
        /*!Estructura necesaria para poder usar archivos de la maquina en Java, writer seria nuestro item con los metodos dentro, la estructura de Files necesita de un parametro PATH (literalmente unpath donde trabajar). Un Charset (este caso uso UTF-8). Y metodos Standar para cuando se abre o se usa el metodo (ejemplo: CREATE -> crea el archivo si no existe, APPEND -> incerta el contenido al archivo existente). Files necesita estar en un try ya que es necesaria la recoleccion del error en caso de no poder abri el archivo
         */
        try (var writer = Files.newBufferedWriter(location, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(text);
            writer.newLine();
            writer.write("test");
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void leector(Path location){
        try  {
            String reader = Files.readString(location);
            System.out.print(reader);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

}