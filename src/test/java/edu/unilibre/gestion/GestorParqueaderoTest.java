package edu.unilibre.gestion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class GestorParqueaderoTest {

    private GestorParqueadero servicio = new GestorParqueadero();

    @Test
    void adicionarBicicletatest() {
        boolean resultado = servicio.adicionarBicicleta(123, "amarillo", 123);

        assertEquals(true, resultado);
    }
}