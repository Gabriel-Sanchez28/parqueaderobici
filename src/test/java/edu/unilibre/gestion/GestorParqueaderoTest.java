package edu.unilibre.gestion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GestorParqueaderoTest {

    private GestorParqueadero servicio;

    @BeforeEach
    void setUp() {
        // Se crea un gestor totalmente nuevo y limpio antes de cada prueba
        servicio = new GestorParqueadero();
    }

    @Test
    void adicionarBicicletaLibreTest() {
        boolean resultado = servicio.adicionarBicicleta(123, "amarillo", 123);
        assertEquals(true, resultado, "La bicleta no se pudo asignar");
    }

    @Test
    void adicionarBicicletaLlenoTest() {
        // Llenar exactamente los 20 cupos del parqueadero limpio
        for (int i = 0; i < 20; i++) {
            servicio.adicionarBicicleta(i, "color", 100 + i);
        }

        // Intentar agregar la bicicleta número 21
        boolean resultado2 = servicio.adicionarBicicleta(999, "rojo", 999);

        assertEquals(false,resultado2, "El parqueadero debería estar lleno y retornar false");
    }
    @Test
    //public boolean registrarSalida(int idpropietario, double tarifa)
    void registrarSalidaTest(){
        //registro
        boolean bicicleta = this.servicio.adicionarBicicleta(124, "amarillo", 123);

        boolean resultado = this.servicio.registrarSalida(123);
        assertEquals(true, resultado, "La cicla no esta registrada");
    }
}