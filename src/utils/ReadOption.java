package utils;

import utils.UserInput;

public class ReadOption {
    public static int leerOpcion(String mensaje){
        while(true){
            System.out.print(mensaje);
            String opcion = UserInput.entradaUsr.nextLine().trim();

            try {
                return Integer.parseInt(opcion);
            } catch (NumberFormatException e){
                System.out.println("El valor obtenido no es valido.\nRevisa las opciones disponibles!");
            }

        }

    }
}
