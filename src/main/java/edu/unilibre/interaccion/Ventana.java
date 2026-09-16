package edu.unilibre.interaccion;
import edu.unilibre.datos.Color;
import edu.unilibre.datos.Pago;
import edu.unilibre.datos.RegistroPago;
import edu.unilibre.datos.Propietario;
import edu.unilibre.gestion.GestorParqueadero;
import java.util.Scanner;

public class Ventana {
    static void main() {
        int opcion = 0;
        int retorno = 0;
        do {
            GestorParqueadero gestor = new GestorParqueadero();
            while (retorno == 0) {
                Scanner teclado = new Scanner(System.in);

                System.out.println("-------------------------------------------------");
                System.out.println("|        Menu Parqueadero de Bicicletas         |");
                System.out.println("-------------------------------------------------");
                System.out.println("1. Registrar una bicicleta");
                System.out.println("2. Sacar bicicleta");
                System.out.println("3. Generar reporte");
                System.out.println("4. Salir");

                if (teclado.hasNextInt()) {
                    opcion = teclado.nextInt();

                } else {
                    opcion = 0;
                }

                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese el serial de la bicicleta");
                        int serial = teclado.nextInt();
                        teclado.nextLine();
                        gestor.listarColores();
                        System.out.println("Seleccione el color de la bicicleta");
                        Color color = gestor.seleccionarColor(teclado.nextInt());
                        teclado.nextLine();

                        System.out.println("Ingrese su nombre");
                        String nombre = teclado.nextLine();

                        System.out.println("Ingrese su numero de identificacion");
                        int id = teclado.nextInt();
                        Propietario propietario = new Propietario(id, nombre);
                        boolean agregar = gestor.adicionarBicicleta(serial, color, propietario.recibirIdentificacion());
                        if (agregar==false){
                            System.out.println("!No se pudo agregar la bicicleta, intenta de nuevo¡");
                        }
                        System.out.println("");
                        System.out.println("!Bicicleta registrada exitosamente¡");
                        for (int i = 5; i>=0; i--){
                            // \r vuelve al inicio de la línea y sobreescribe lo que estaba antes
                            System.out.print("\rVolviendo al menu inicial en " + i);
                            System.out.flush();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        System.out.println("");
                        break;
                    case 2:
                        System.out.println("Ingrese su numero de identificacion");
                        int idRegistrada = teclado.nextInt();
                        teclado.nextLine();
                        gestor.metodoPago();
                        System.out.println("Seleccione un metodo de pago: ");
                        Pago pago = gestor.seleccionarPago(teclado.nextInt());

                        RegistroPago sacar = gestor.registrarSalida(idRegistrada, pago);
                        if (sacar == null) {
                            System.out.println("Ocurrio un error, verifica los datos ingresados");
                            break;
                        }
                        sacar.imprimirFactura();
                        for (int i = 5; i>=0; i--){
                            // \r vuelve al inicio de la línea y sobreescribe lo que estaba antes
                            System.out.print("\rVolviendo al menu inicial en " + i);
                            System.out.flush();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        System.out.println("");
                        break;
                    case 3:
                        String reporte = gestor.generarReporte();
                        System.out.println(reporte);
                        for (int i = 5; i>=0; i--){
                            // \r vuelve al inicio de la línea y sobreescribe lo que estaba antes
                            System.out.print("\rVolviendo al menu inicial en " + i);
                            System.out.flush();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        System.out.println("");
                        break;
                    case 4:
                        System.out.println("Gracias por usar nuestros servicios");
                        retorno = 1;
                        break;
                    default:
                        System.out.println("¡Opcion no valida1!");
                        System.out.println("");
                }
            }
        }while (opcion!=4);
    }
}
