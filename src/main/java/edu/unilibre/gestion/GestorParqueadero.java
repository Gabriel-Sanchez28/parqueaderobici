package edu.unilibre.gestion;

import edu.unilibre.datos.Bicicleta;
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
    private LocalDateTime horaSalida;

    //

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
    public boolean registrarSalida(int idpropietario){
        horaSalida = LocalDateTime.now();
        boolean regisSalida = false;
        if (idpropietario <= 0){
            System.out.println("Ingrese una identificacion valida");
            return regisSalida = false;
        }
        for (int i = 0; i < listaBicicletas.length; i++) {
            if (listaBicicletas[i] != null) {
                if (listaBicicletas[i].recibirIdpropietario().recibirIdentificacion() == idpropietario) {
                    Bicicleta salida = listaBicicletas[i];
                    LocalDateTime horaEntrada = listaBicicletas[i].recibirHoraEntrada();
                    Duration duracion = Duration.between(horaEntrada, horaSalida);
                    long minutosTotales = duracion.toMinutes();
                    listaBicicletas[i] = null;
                    double valorPagar = minutosTotales * tarifa;
                    totalIngresos = totalIngresos + valorPagar;
                    regisSalida = true;
                    return  regisSalida;
                }
            }
        }
        return regisSalida;
    }
    //registrar pago

    //generar reporte
}
