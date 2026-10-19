package com.tienda;
public class CalculadoraEnvio {
 public static double calcularCoste(double pesoKg, int distanciaKm, TipoEnvio tipo,
 boolean fragil, String codigoPromo, boolean
clientePremium) {
 if (pesoKg <= 0 || distanciaKm <= 0 || tipo == null) {
 throw new IllegalArgumentException("Datos de envío inválidos");
 }
 double base = (pesoKg * 0.80) + (distanciaKm * 0.05);
 if (base < 5.00) base = 5.00;
 if (fragil) base += 2.00;
 if (tipo == TipoEnvio.EXPRESS) base = base * 1.60;
 if (clientePremium && base >= 50.00) base = 0;
 if (codigoPromo != null && !codigoPromo.isBlank()) {
 if ("GRATIS".equals(codigoPromo) && base >= 30.00 && tipo !=
TipoEnvio.EXPRESS) {
 base = 0;
 } else if ("ENVIO10".equals(codigoPromo) && base >= 20.00) {
 base = base * 0.90;
 }
 }
 return base;
 }
}