package edu.unilibre.datos;

public class Bicicleta {
    private int serial;
    private String color;
    private Propietario idPropietario;

    public int getSerial() {
        return serial;
    }

    public String getColor() {
        return color;
    }

    public Propietario getIdpropietario() {
        return idPropietario;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setIdpropietario(Propietario idpropietario) {
        this.idPropietario = idpropietario;
    }

    public Bicicleta (int serial, String color, Propietario idpropietario){
        this.serial = serial;
        this.color = color;
        this.idPropietario = idpropietario;
    }
}
