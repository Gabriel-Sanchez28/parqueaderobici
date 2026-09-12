package edu.unilibre.datos;

public class Propietario {
    private int identificacion;
    private String nombre;

    public int recibirIdentificacion() {
        return identificacion;
    }

    public String recibirNombre() {
        return nombre;
    }

    public void modificarIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public void modificarNombre(String nombre) {
        this.nombre = nombre;
    }

    public Propietario (int idPropietario, String nombre){
        this.identificacion = identificacion;
        this.nombre = this.nombre;
    }
}
