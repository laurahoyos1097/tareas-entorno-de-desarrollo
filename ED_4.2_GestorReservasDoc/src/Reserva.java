/** Reserva de sala - duración - uso de proyector
 * @since 1.0
 */
public class Reserva {
    private String solicitante;
    private String sala;
    private int horas;
    private boolean proyector;

    /** Crea una reserva nueva validando los datos
        * @param solicitantes Nombre de quien realiza la reserva
        * @param sala Nombre de la sala reservada
        * @param horas Número de horas de la reserva (debe ser > 0)
        * @param proyector Se requiere o no proyector
        * @throws IllegalArgumentException Datos inválidos
        */
    public Reserva(String solicitante, String sala, int horas, boolean proyector) {
       // validación de datos
        if (solicitante == null || solicitante.isBlank()) {
            throw new IllegalArgumentException("El solicitante es obligatorio.");
        }
        if (sala == null || sala.isBlank()) {
            throw new IllegalArgumentException("La sala es obligatoria.");
        }
        if (horas <= 0) {
            throw new IllegalArgumentException("Las horas deben ser mayores que cero.");
        }
        this.solicitante = solicitante;
        this.sala = sala;
        this.horas = horas;
        this.proyector = proyector;
    }

    /** Calcula el coste total en función de las horas reservadas y si incluye o no proyector
     */
    public double calcularImporte() {
        double precioBase = horas * 12.5;
        // se añade coste adicional si incluye proyector
        if (proyector) {
            precioBase += 8;
        }
        return precioBase;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public String getSala() {
        return sala;
    }

    public int getHoras() {
        return horas;
    }

    public boolean isProyector() {
        return proyector;
    }
}
