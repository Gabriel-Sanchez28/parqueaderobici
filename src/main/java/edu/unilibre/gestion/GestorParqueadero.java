package edu.unilibre.gestion;

import edu.unilibre.datos.*;

import java.time.Duration;
import java.time.LocalDateTime;

public class GestorParqueadero {
    private final int cupos = 20;
    private final double tarifa = 10.0;
    private double totalIngresos;
    private int contadorBicicletas;
    private int bicicletasActivas;
    private Bicicleta[] listaBicicletas = new Bicicleta[cupos];
    private LocalDateTime horaSalida;

    //registrar bicicleta
    public boolean adicionarBicicleta(int serial, Color color, int idPropietario) {
        boolean registroBici = false;
        //verificacion datos ingresados
        if (bicicletasActivas >= cupos) {
            System.out.println("Los cupos estan llenos");
            return registroBici;
        }
        if (serial<=0){
            System.out.println("Ingrese un serial valido");
            return registroBici;
        } else if (color == null) {
            System.out.println("Ingrese un color valido");
            return registroBici;
        } else if (idPropietario <= 0){
            System.out.println("Ingrese una identificación valida");
            return registroBici;
        }

        for (int i = 0; i < listaBicicletas.length; i++) {
            if (listaBicicletas[i] == null) {
                Propietario propietario = new Propietario(idPropietario, null);
                LocalDateTime horaEntrada = LocalDateTime.now();
                listaBicicletas[i] = new Bicicleta(serial, color, propietario, horaEntrada);


                contadorBicicletas++;
                bicicletasActivas++;
                registroBici = true;
                return registroBici;
            }
        }
        return registroBici;
    }

    //registrar salida
    public RegistroPago registrarSalida(int idpropietario, Pago pago) {
        horaSalida = LocalDateTime.now();
        RegistroPago registro = null;

        //validacion de parametros
        if (idpropietario <= 0){
            System.out.println("Ingrese una identificacion valida");
            return null;
        }
        if (pago == null) {
            System.out.println("Debe seleccionar un método de pago válido");
            return null;
        }

        for (int i = 0; i < listaBicicletas.length; i++) {
            if (listaBicicletas[i] != null) {
                if (listaBicicletas[i].recibirIdpropietario().recibirIdentificacion() == idpropietario) {
                    Bicicleta salida = listaBicicletas[i];
                    Propietario idPropietario = listaBicicletas[i].recibirIdpropietario();
                    //contenedor de hora entrada verdadero
                    LocalDateTime horaEntrada = listaBicicletas[i].recibirHoraEntrada();
                    //hora de entrada prueba
                    //LocalDateTime horaEntrada = LocalDateTime.of(2026,9,12, 11, 30);
                    Duration duracion = Duration.between(horaEntrada, horaSalida);
                    long minutosTotales = duracion.toMinutes();

                    double valorPagar = minutosTotales * tarifa;
                    registro =new RegistroPago(idPropietario, horaEntrada, horaSalida, pago, valorPagar);
                    /*System.out.println("La biccileta se registro correctamente");
                    System.out.println("-------- Factura ----------");
                    //System.out.println("Propietario " + listaBicicletas[i].recibirIdpropietario().recibirNombre());
                    System.out.println("Identificacion " + listaBicicletas[i].recibirIdpropietario().recibirIdentificacion());
                    System.out.println("Serial " + listaBicicletas[i].recibirSerial());
                    System.out.println("Color " + listaBicicletas[i].recibirColor());
                    System.out.println("Metodo de pago: " + pago);
                    System.out.println("Duracion " + minutosTotales);
                    System.out.println("El valor a pagar es de: " + valorPagar);
                    System.out.println("----------------------------------");*/

                    bicicletasActivas--;
                    listaBicicletas[i] = null;
                    totalIngresos = totalIngresos + valorPagar;
                    return  registro;
                }
            }
        }
        return registro;
    }

    //generar reporte
    public String generarReporte(){
        String reporte = "--- REPORTE DEL PARQUEADERO ---\n" +
                "Total de bicicletas ingresadas en el día " + contadorBicicletas + "\n" +
                "Total de ingresos realizados " + totalIngresos;
        return reporte;
    }
    public void listarColores(){
        System.out.println("|           Opciones de Color          |");
        int i= 0;
        for (Color color : Color.values()){
            i++;
            System.out.println(i + ". " + color);
        }
    }

    public Color seleccionarColor(int color){
        Color[] colores = Color.values();
        if (color<0){
            System.out.println("El color no es valido");
        }
        for (int i=0; i <= colores.length;i++){
            if (i == color-1){
                return colores[i];
            }
        }
        return null;
    }

    public void metodoPago(){
        System.out.println("|           Opciones de Pago          |");
        int i= 0;
        for (Pago pago : Pago.values()){
            i++;
            System.out.println(i + ". " + pago);
        }
    }
    public Pago seleccionarPago(int opcion){
        Pago[] opciones = Pago.values();
        if (opcion<0){
            System.out.println("La opcion no es valida");
        }
        for (int i=0; i <= opciones.length;i++){
            if (i == opcion-1){
                return opciones[i];
            }
        }
        return null;
    }
}
