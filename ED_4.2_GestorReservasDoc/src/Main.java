/** Ejecuta el sistema de reservas
 * @since 1.0
 */
public class Main {
    public static void main(String[] args) {
        /** Crea el gestor de reservas, registra ejemplos de solicitantes y muestra resultados por consoal
         * @param args Argumentos de línea de comandos
         */
        GestorReservas gestor = new GestorReservas();
        gestor.registrar(new Reserva("Ana Pérez", "Aula Norte", 2, true));
        gestor.registrar(new Reserva("Luis Martín", "Laboratorio 2", 3, false));
        System.out.println("Total de reservas: " + gestor.getNumeroReservas());
        System.out.println("Importe acumulado: " + gestor.calcularImporteTotal());
        System.out.println("Hay aula con proyector: " + gestor.existeReservaConProyector());
    }
}
