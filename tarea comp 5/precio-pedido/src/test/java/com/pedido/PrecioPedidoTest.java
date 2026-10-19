package com.pedido;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
class PrecioPedidoTest {
 // Para comparar decimales (double) usamos una tolerancia
 private static final double DELTA = 1e-6;
 @Test
 @DisplayName("Devuelve -1 si precioUnitario <= 0")
 void devuelveMenosUnoSiPrecioInvalido() {
 double total = PrecioPedido.calcularTotal(0, 5, "NORMAL",
null);
 assertEquals(-1, total, DELTA);
 }
 @Test
 @DisplayName("Devuelve -1 si unidades <= 0")
 void devuelveMenosUnoSiUnidadesInvalidas() {
 double total = PrecioPedido.calcularTotal(10, 0, "NORMAL",
null);
 assertEquals(-1, total, DELTA);
 }
 @ParameterizedTest(name = "[{index}] p={0}, u={1}, cliente={2},cupon={3} => esperado={4}")
 @CsvSource(value = {
 "10, 5, NORMAL, NULL, 50.0",
 "10, 9, NORMAL, NULL, 90.0",
 "10, 10, NORMAL, NULL, 88.0",
 "20, 2, VIP, NULL, 38.0",
 "10, 10, VIP, NULL, 83.0",
 "10, 10, VIP, DESC20, 80.0",
 "9.99, 10, NORMAL, DESC20, 87.912",
 "12.4, 8, VIP, DESC20, 94.24"
 }, nullValues = "NULL")
 @DisplayName("Casos principales: particiones, límites y combinaciones")
 void casosPrincipales(double precioUnitario, int unidades,
String cliente, String cupon, double esperado) {
 double total = PrecioPedido.calcularTotal(precioUnitario,
unidades, cliente, cupon);
 assertEquals(esperado, total, DELTA);
 }
}
