package com.tienda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraEnvioTest {

    // Tolerancia para comparar doubles
    private static final double DELTA = 0.001;

    // ─── Tests de validación (assertThrows) ───────────────────────────────────

    @Test
    @DisplayName("Lanza excepción si el peso es 0 o negativo")
    void lanzaExcepcionPesoInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
            CalculadoraEnvio.calcularCoste(0, 100, TipoEnvio.ESTANDAR, false, null, false)
        );
    }

    @Test
    @DisplayName("Lanza excepción si la distancia es 0 o negativa")
    void lanzaExcepcionDistanciaInvalida() {
        assertThrows(IllegalArgumentException.class, () ->
            CalculadoraEnvio.calcularCoste(5, 0, TipoEnvio.ESTANDAR, false, null, false)
        );
    }

    @Test
    @DisplayName("Lanza excepción si el tipo de envío es null")
    void lanzaExcepcionTipoNull() {
        assertThrows(IllegalArgumentException.class, () ->
            CalculadoraEnvio.calcularCoste(5, 100, null, false, null, false)
        );
    }

    // ─── Test parametrizado ───────────────────────────────────────────────────

    @ParameterizedTest(name = "[{index}] peso={0}, dist={1}, tipo={2}, fragil={3}, promo={4}, premium={5} → {6}€")
    @CsvSource(value = {
        // Caso 1: base < 5 → aplica mínimo 5€
        "0.5,  10, ESTANDAR, false, NULL, false, 5.00",
        // Caso 2: base exactamente 5€ (sin redondeos)
        "1.0,  50, ESTANDAR, false, NULL, false, 5.00",
        // Caso 3: frágil añade 2€
        "1.0,  50, ESTANDAR, true,  NULL, false, 7.00",
        // Caso 4: EXPRESS multiplica x1.6
        "5.0, 100, EXPRESS,  false, NULL, false, 14.40",
        // Caso 5: promo ENVIO10 (base >= 20)
        "20.0, 100, ESTANDAR, false, ENVIO10, false, 18.90",
        // Caso 6: promo GRATIS con ESTANDAR y base >= 30
        "25.0, 200, ESTANDAR, false, GRATIS, false, 0.00",
        // Caso 7: promo GRATIS NO aplica con EXPRESS
        "20.0, 200, EXPRESS,  false, GRATIS, false, 41.60",
        // Caso 8: cliente premium con base >= 50 → gratis
        "30.0, 200, EXPRESS,  false, NULL,   true,  0.00",
        // Caso 9: cliente premium con base < 50 → NO es gratis
        "5.0,  50, ESTANDAR,  false, NULL,   true,  6.50",
        // Caso 10: frágil + EXPRESS combinados
        "5.0, 100, EXPRESS,   true,  NULL,   false, 17.60"
    }, nullValues = "NULL")
    @DisplayName("Casos parametrizados de calcularCoste")
    void calcularCosteParametrizado(double pesoKg, int distanciaKm, TipoEnvio tipo,
                                    boolean fragil, String codigoPromo,
                                    boolean clientePremium, double esperado) {
        // Act
        double real = CalculadoraEnvio.calcularCoste(pesoKg, distanciaKm, tipo,
                                                      fragil, codigoPromo, clientePremium);
        // Assert
        assertEquals(esperado, real, DELTA);
    }
}