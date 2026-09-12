package edu.unilibre.gestion;

import edu.unilibre.datos.Bicicleta;
import edu.unilibre.datos.Propietario;

public class GestorParqueadero {
    private final int cupos = 20;
    private final double tarifa = 10;
    private double totalIngresos;
    private int contadorBicicletas;
    private  int bicicletasActivas;
    private Bicicleta[] listaBicicletas = new Bicicleta[cupos];

    public boolean adicionarBicicleta(int serial, String color, int idPropietario){
        if (bicicletasActivas >= cupos){
            return false;
        }
        for (int i = 0; i < listaBicicletas.length; i++){
            if (listaBicicletas[i] == null) {
                Propietario propietario = new Propietario(idPropietario, null);
                contadorBicicletas++;
                bicicletasActivas++;
                return true;
            }
        }
        return false;
    }
}
