package edu.unilibre.gestion;

import edu.unilibre.datos.Bicicleta;
import edu.unilibre.datos.Color;
import edu.unilibre.datos.Pago;
import edu.unilibre.datos.RegistroPago;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static edu.unilibre.datos.Pago.NEQUI;
import static org.junit.jupiter.api.Assertions.*;

class GestorParqueaderoTest {

    private GestorParqueadero servicio;

    @BeforeEach
    void setUp() {
        // Se crea un gestor totalmente nuevo y limpio antes de cada prueba
        servicio = new GestorParqueadero();
    }

    //adicionar bicicleta con espacios libre
    @Test
    void adicionarBicicletaLibreTest() {
        boolean resultado = servicio.adicionarBicicleta(123, Color.AZUL, 123);
        assertEquals(true, resultado, "La bicleta no se pudo asignar");
    }

    //adicionar bicicleta con parametros nulos
    @Test
    void adicionarBicicletaNulosTest() {
        boolean resultado = servicio.adicionarBicicleta(0, Color.AZUL, 123);
        assertEquals(false, resultado, "el serial debe tener datos erroneos");
        boolean resultado2 = servicio.adicionarBicicleta(123, null, 123);
        assertEquals(false, resultado2, "el color debe estar nulo");
        boolean resultado4 = servicio.adicionarBicicleta(123, Color.AZUL, 0);
        assertEquals(false, resultado4, "la identificacion del propietario debe tener datos erroneos");
    }

    //adicionar bicicleta con parqueadero lleno
    @Test
    void adicionarBicicletaLlenoTest() {
        // Llenar exactamente los 20 cupos del parqueadero limpio
        for (int i = 0; i < 21; i++) {
            servicio.adicionarBicicleta(i, Color.AZUL, 100 + i);
        }

        // Intentar agregar la bicicleta número 21
        boolean resultado = servicio.adicionarBicicleta(999, Color.AZUL, 999);

        assertEquals(false,resultado, "El parqueadero debería estar lleno");
    }

    //sacar bicicleta registrada
    @Test
    //public boolean registrarSalida(int idpropietario, double tarifa)
    void registrarSalidaTest(){
        //registro
        this.servicio.adicionarBicicleta(124, Color.AZUL, 123);
        RegistroPago resultado = this.servicio.registrarSalida(123, NEQUI);
        assertNotNull(resultado, "La cicla no esta registrada");
    }

    //sacar bicicleta valores nulos
    @Test
    void registrarSalidaIdNuloTest(){
        //sacar bicicleta que no existe
        RegistroPago resultado = this.servicio.registrarSalida(0, NEQUI);
        assertNull( resultado, "La cicla no debe estar registrada");

        //Pago no nulo
        RegistroPago resultado2 = this.servicio.registrarSalida(123, null);
        assertNull( resultado2, "El pago no debe ser valido");
    }

    //sacar bicicleta id no coincidentes
    @Test
    void registrarSalidaIdNoCoincideTest() {
        this.servicio.adicionarBicicleta(1, Color.AZUL, 123);

        RegistroPago resultado = this.servicio.registrarSalida(999, NEQUI);

        assertNull(resultado, "Las identificaciones no deben coincidir");
    }

    @Test
    void generarReporteTest() {
        servicio.adicionarBicicleta(102, Color.AZUL, 456);
        this.servicio.registrarSalida(456, NEQUI);
        String reporte = servicio.generarReporte();
        System.out.println(reporte);

        // Validar que el reporte contenga los datos esperados
        assertNotNull(reporte, "El reporte no debe ser nulo");
    }
    
    @Test
    void metodoPagoTest(){
        assertDoesNotThrow(() -> {
            this.servicio.metodoPago();
        }, "El método de opciones de pago debe ejecutarse sin fallar");
    }

    @Test
    void seleccionPagoTest(){
        Pago pagoSeleccionado = this.servicio.seleccionarPago(1);
        assertEquals(NEQUI, pagoSeleccionado);
    }

    @Test
    void seleccionPagoErroneoTest(){
        Pago pagoSeleccionado = this.servicio.seleccionarPago(-5);
        assertNotEquals(NEQUI, pagoSeleccionado);
    }
}