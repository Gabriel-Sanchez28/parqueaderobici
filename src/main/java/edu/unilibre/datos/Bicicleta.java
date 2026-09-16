package edu.unilibre.datos;

import java.time.LocalDateTime;

public class Bicicleta {
    private int serial;
    private Color color;
    private Propietario idPropietario;
    private LocalDateTime horaEntrada;

    public int recibirSerial() {
        return serial;
    }

    public Color recibirColor() {
        return color;
    }

    public Propietario recibirIdpropietario() {
        return idPropietario;
    }

    public LocalDateTime recibirHoraEntrada() {
        return horaEntrada;
    }


    public void modificarSerial(int serial) {
        this.serial = serial;
    }

    public void modificarColor(Color color) {
        this.color = color;
    }

    public void modificarIdpropietario(Propietario idpropietario) {
        this.idPropietario = idpropietario;
    }


    public Bicicleta (int serial, Color color, Propietario idpropietario, LocalDateTime horaEntrada){
        this.serial = serial;
        this.color = color;
        this.idPropietario = idpropietario;
        this.horaEntrada = horaEntrada;
    }
}
