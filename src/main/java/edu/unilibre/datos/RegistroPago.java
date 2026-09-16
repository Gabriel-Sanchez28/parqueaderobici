package edu.unilibre.datos;

import java.time.LocalDateTime;

public class RegistroPago {
    private Propietario idPropietario;
    private LocalDateTime horaSalida;
    private LocalDateTime horaEntrada;
    private Pago tipoPago;
    private double valorPagar;

    public Propietario recibirIdPropietario() {
        return idPropietario;
    }

    public LocalDateTime recibirHoraSalida() {
        return horaSalida;
    }

    public LocalDateTime recibirHoraEntrada() {
        return horaEntrada;
    }

    public Pago recibirTipoPago() {
        return tipoPago;
    }

    public double recibirValorPagar() {
        return valorPagar;
    }


    public void modificarIdPropietario(Propietario idPropietario) {
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


    public RegistroPago(Propietario idPropietario, LocalDateTime horaSalida, LocalDateTime horaEntrada, Pago tipoPago, double valorPagar) {
        this.idPropietario = idPropietario;
        this.horaSalida = horaSalida;
        this.horaEntrada = horaEntrada;
        this.tipoPago = tipoPago;
        this.valorPagar = valorPagar;
    }
}
