
import java.nio.file.*;
import io.FileManager;
import utils.UserInput;
import utils.ReadOption;

public class Main {
    //*escaner para la entrada de texto


    public static void main(String[] args) {
        int opcion;
        boolean run = true;

        while (run){
            /* *
             *mensaje de saludo y solicitud de nombre del archivo, en realidad no es necesario solicitar el .txt, puedo usar la concadenacion (como ya se esta haciendo xd, un poco silly de mi parte) */
            System.out.print("Por favor ingresa el nombre del archivo en el que quieres trabajar: ");

            String usrLocation = UserInput.entradaUsr.nextLine();
            //ruta de prueba para inicio del proyecto
            /*
             * Path es como una clase con metodos dentro usar Path no requiere usar un new Path, ya que no es una funcion, usamos el metodo Path.of() <- Esto obtiene el path a abrir (podriamos hacerlo dinamico obteniendo el path directamente del user!)
             */


            Path location = Path.of("test/" + usrLocation + ".txt");
            System.out.print("Opciones disponibles\n1 - Leer\n2 - Escribir\n3 - Editar\n4 - Salir");
            opcion = ReadOption.leerOpcion("Ingresa una opcion: ");
            switch (opcion) {
                case 1 -> {
                    System.out.print(FileManager.reader(location));
                    opcion = ReadOption.leerOpcion("\nQuieres continuar? (0 para no, 1 para si)");
                    run = continuar(opcion);
                }

                case 2 -> {
                    //texto generico para consola
                    System.out.print("Ingresa el texto que quieres guardar: ");
                    String text = UserInput.entradaUsr.nextLine();
                    FileManager.writer(location, text);
                    opcion = ReadOption.leerOpcion("\nQuieres continuar? (0 para no, 1 para si)");
                    run = continuar(opcion);
                }
                case 3 ->{
                    FileManager.edit(location);
                    opcion = ReadOption.leerOpcion("\nQuieres continuar? (0 para no, 1 para si)");
                    run = continuar(opcion);
                }
                case 4 -> {
                    System.out.print("Hasta luego!");
                    run = false;
                }
                default -> System.out.print("Opcion no valida");
            }

        }

    }


    public static boolean continuar(int opcion){
        if(opcion != 1){
            System.out.print("Hasta luego!");
            return false;
        }else{
            return true;
        }
    }

}