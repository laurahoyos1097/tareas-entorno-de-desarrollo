package com.pedido;
public class PrecioPedido {
    public static double calcularTotal(double precioUnitario, int unidades, String cliente, String cupon) {
        if (precioUnitario <= 0 || unidades <= 0) {
            return -1;
        }
        double base = precioUnitario * unidades;
        double descuento = 0;
        if (unidades >= 10) {
            descuento += 0.12;
        }
        if ("VIP".equals(cliente)) {
            descuento += 0.05;
        }
        // Cupón especial: no acumulable
        if ("DESC20".equals(cupon) && base >= 100) {
            descuento = 0.20;
        }
        return base * (1 - descuento);
    }
}