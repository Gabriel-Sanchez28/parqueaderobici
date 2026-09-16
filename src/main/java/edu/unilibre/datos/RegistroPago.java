package edu.unilibre.datos;

import edu.unilibre.datos.Propietario;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroPago {
    private int idPropietario;
    private LocalDateTime horaSalida;
    private LocalDateTime horaEntrada;
    private int serial;
    private Color color;
    private String nombre;
    private Pago tipoPago;
    private double valorPagar;


    public int recibirIdPropietario() {
        return idPropietario;
    }

    public LocalDateTime recibirHoraSalida() {
        return horaSalida;
    }

    public LocalDateTime recibirHoraEntrada() {
        return horaEntrada;
    }

    public int recibiSerial() {
        return serial;
    }

    public Color recibirColor() {
        return color;
    }

    public String recibirNombre() {
        return nombre;
    }

    public Pago recibirTipoPago() {
        return tipoPago;
    }

    public double recibirValorPagar() {
        return valorPagar;
    }


    public void modificarIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public void modificarHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public void modificarHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public void modificarTipoPago(Pago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public void modificarValorPagar(double valorPagar) {
        this.valorPagar = valorPagar;
    }

    public void imprimirFactura() {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            System.out.println("\n-------------------------------------------------");
            System.out.println("|              FACTURA DE PARQUEADERO           |");
            System.out.println("-------------------------------------------------");
            System.out.println("Nombre Propietario : " + recibirNombre());
            System.out.println("Identificación     : " + serial);
            System.out.println("Hora de Entrada    : " + recibirHoraEntrada().format(formato));
            System.out.println("Hora de Salida     : " + recibirHoraSalida().format(formato));
            System.out.println("Método de Pago     : " + recibirTipoPago());
            System.out.println("Valor a Pagar      : $" + valorPagar);
            System.out.println("-------------------------------------------------");
            System.out.println("¡Bicicleta retirada y registrada con éxito!");
            System.out.println("-------------------------------------------------\n");

    }
    public RegistroPago(int serial, Color color,String nombre, int idPropietario, LocalDateTime horaEntrada, LocalDateTime horaSalida, Pago tipoPago, double valorPagar) {
        this.serial = serial;
        this.color = color;
        this.nombre = nombre;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.tipoPago = tipoPago;
        this.valorPagar = valorPagar;
    }
}
