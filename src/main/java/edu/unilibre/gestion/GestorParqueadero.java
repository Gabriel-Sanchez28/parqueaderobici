package edu.unilibre.gestion;

import edu.unilibre.datos.Bicicleta;
import edu.unilibre.datos.Propietario;

import java.time.Duration;
import java.time.LocalDateTime;


public class GestorParqueadero {
    private final int cupos = 20;
    private final double tarifa = 10;
    private double totalIngresos;
    private int contadorBicicletas;
    private int bicicletasActivas;
    private Bicicleta[] listaBicicletas = new Bicicleta[cupos];
    private LocalDateTime horaSalida;

    //

    public boolean adicionarBicicleta(int serial, String color, int idPropietario, LocalDateTime horaEntrada) {
        boolean registroBici = false;
        if (bicicletasActivas >= cupos) {
            return registroBici;
        }
        for (int i = 0; i < listaBicicletas.length; i++) {
            if (listaBicicletas[i] == null) {
                Propietario propietario = new Propietario(idPropietario, null);
                horaEntrada = LocalDateTime.now();
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
    public boolean registrarSalida(int idpropietario, LocalDateTime horaEntrada, double tarifa){
        horaSalida = LocalDateTime.now();
        boolean regisSalida = false;
        for (int i = 0; i < listaBicicletas.length; i++) {
            if (listaBicicletas[i].recibirIdpropietario().recibirIdentificacion() ==  idpropietario) {
                Bicicleta salida = listaBicicletas[i];
                Duration duracion = Duration.between(horaEntrada, horaSalida);
                long minutosTotales = duracion.toMinutes();
                listaBicicletas[i] = null;
                double valorPagar = minutosTotales * tarifa;
                totalIngresos = totalIngresos + valorPagar;
                regisSalida = true;
            }
        }
        return false;
    }
    //registrar pago

    //generar reporte
}
