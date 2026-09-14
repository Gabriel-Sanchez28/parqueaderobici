package edu.unilibre.gestion;

import edu.unilibre.datos.Bicicleta;
import edu.unilibre.datos.Pago;
import edu.unilibre.datos.Propietario;
import java.time.Duration;
import java.time.LocalDateTime;

public class GestorParqueadero {
    private final int cupos = 20;
    private final double tarifa = 10.0;
    private double totalIngresos;
    private int contadorBicicletas;
    private int bicicletasActivas;
    private Bicicleta[] listaBicicletas = new Bicicleta[cupos];
    //private Pago pago;
    private LocalDateTime horaSalida;

    //registrar bicicleta
    public boolean adicionarBicicleta(int serial, String color, int idPropietario) {
        boolean registroBici = false;
        //verificacion datos ingresados
        if (bicicletasActivas >= cupos) {
            System.out.println("Los cupos estan llenos");
            return registroBici;
        }
        if (serial<=0){
            System.out.println("Ingrese un serial valido");
            return registroBici;
        } else if (color == null || color == "") {
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
    public boolean registrarSalida(int idpropietario, Pago pago) {
        horaSalida = LocalDateTime.now();
        boolean regisSalida = false;

        //validacion de parametros
        if (idpropietario <= 0){
            System.out.println("Ingrese una identificacion valida");
            return regisSalida = false;
        }
        if (pago == null) {
            System.out.println("Debe seleccionar un método de pago válido");
            return regisSalida = false;
        }

        for (int i = 0; i < listaBicicletas.length; i++) {
            if (listaBicicletas[i] != null) {
                if (listaBicicletas[i].recibirIdpropietario().recibirIdentificacion() == idpropietario) {
                    Bicicleta salida = listaBicicletas[i];

                    //contenedor de hora entrada verdadero
                    //LocalDateTime horaEntrada = listaBicicletas[i].recibirHoraEntrada();
                    //hora de entrada prueba
                    LocalDateTime horaEntrada = LocalDateTime.of(2026,9,12, 11, 30);
                    Duration duracion = Duration.between(horaEntrada, horaSalida);
                    long minutosTotales = duracion.toMinutes();

                    double valorPagar = minutosTotales * tarifa;
                    System.out.println("La biccileta se registro correctamente");
                    System.out.println("-------- Factura ----------");
                    //System.out.println("Propietario " + listaBicicletas[i].recibirIdpropietario().recibirNombre());
                    System.out.println("Identificacion " + listaBicicletas[i].recibirIdpropietario().recibirIdentificacion());
                    System.out.println("Serial " + listaBicicletas[i].recibirSerial());
                    System.out.println("Color " + listaBicicletas[i].recibirColor());
                    System.out.println("Metodo de pago: " + pago);
                    System.out.println("Duracion " + minutosTotales);
                    System.out.println("El valor a pagar es de: " + valorPagar);
                    System.out.println("----------------------------------");

                    bicicletasActivas--;
                    listaBicicletas[i] = null;
                    totalIngresos = totalIngresos + valorPagar;
                    return  regisSalida = true;
                }
            }
        }
        return regisSalida;
    }

    //generar reporte
    public String generarReporte(){
        String reporte = "--- REPORTE DEL PARQUEADERO ---\n" +
                "Total de bicicletas ingresadas en el día " + contadorBicicletas + "\n" +
                "Total de ingresos realizados " + totalIngresos;
        return reporte;
    }

    public void metodoPago(){
        System.out.println("|           Opciones de Pago          |");
        for (Pago pago : Pago.values()){
            System.out.println("- "+ pago);
        }
    }
}
