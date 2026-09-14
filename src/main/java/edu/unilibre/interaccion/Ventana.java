package edu.unilibre.interaccion;
import edu.unilibre.datos.Pago;
import edu.unilibre.datos.Propietario;
import edu.unilibre.gestion.GestorParqueadero;

import java.util.Scanner;

public class Ventana {
    static void main() {
        Scanner teclado = new Scanner(System.in);
        GestorParqueadero gestor = new GestorParqueadero();
        int opcion = 0;
        boolean validacion = false;
        System.out.println("-------------------------------------------------");
        System.out.println("|        Menu Parqueadero de Bicicletas         |");
        System.out.println("-------------------------------------------------");
        System.out.println("1. Registrar una bicicleta");
        System.out.println("2. Sacar bicicleta");
        System.out.println("3. Generar reporte");

        while (!validacion) {
            System.out.println("Ingrese una opcion: ");
            if(teclado.hasNextInt()) {
                opcion = teclado.nextInt();
                if (opcion >= 1 && opcion <= 3) {
                    validacion = true;
                } else {
                    System.out.println("¡Opcion no valida!");
                    System.out.println("");
                }
            }else {
                System.out.println("¡Opcion no valida!");
                System.out.println("");
                teclado.next();
            }

        }
        switch (opcion){
            case 1:
                System.out.println("Ingrese el serial de la bicicleta");
                int serial = teclado.nextInt();
                teclado.nextLine();
                System.out.println("Ingrese el color de la bicicleta");
                String color = teclado.nextLine();

                System.out.println("Ingrese su nombre");
                String nombre = teclado.nextLine();

                System.out.println("Ingrese su numero de identificacion");
                int id = teclado.nextInt();
                Propietario propietario = new Propietario(id, nombre);
                boolean agregar = gestor.adicionarBicicleta(serial, color, propietario.recibirIdentificacion());
                System.out.println("!Bicicleta registrada exitosamente¡");
        }
    }
}
