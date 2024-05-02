package utilidades;

import java.util.Comparator;

public class ComparadorTransaccion implements Comparator<Transaccion> {
    @Override
    public int compare(Transaccion t1, Transaccion t2) {
        // Primero, compara por nombre de producto
        int comparacionNombre = t1.getNombreProducto().compareTo(t2.getNombreProducto());
        if (comparacionNombre != 0) {
            return comparacionNombre;
        }

        // Si los nombres de los productos son iguales, compara por cantidad
        int comparacionCantidad = Integer.compare(t1.getCantidad(), t2.getCantidad());
        if (comparacionCantidad != 0) {
            return comparacionCantidad;
        }

        // Si las cantidades son iguales, compara por precio
        return Double.compare(t1.getPrecio(), t2.getPrecio());
    }
}
