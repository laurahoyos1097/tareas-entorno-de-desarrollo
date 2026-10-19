import java.util.ArrayList;
import java.util.List;
/** Gestiona, registra y calcula las reservas
 * @since 1.0
 */
public class GestorReservas {
    private List<Reserva> reservas = new ArrayList<>();
/** Registra una reserva nueva
 * @param reserva Reserva a registrar
 * @throws IllegalArgumentException
 */
    public void registrar(Reserva reserva) {
        if (reserva == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula.");
        }
        reservas.add(reserva);
    }
/** Número total de reservas registradas */
    public int getNumeroReservas() {
        return reservas.size();
    }
/** Indica si existe al menos una reserva con proyector */
    public double calcularImporteTotal() {
        double total = 0;
        for (Reserva reserva : reservas) {
            total += reserva.calcularImporte();
        }
        return total;
    }
/** Indica si existe al menos una reserva con proyector */
    public boolean existeReservaConProyector() {
        for (Reserva reserva : reservas) {
            if (reserva.isProyector()) {
                return true;
            }
        }
        return false;
    }
}
