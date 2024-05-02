package utilidades;

import java.util.Comparator;

public class Transaccion implements Comparable<Transaccion>, Comparator<Transaccion> {
    private String id;
    private String nombreProducto;
    private int cantidad;
    private double precio;

    public Transaccion(String id, String nombreProducto, int cantidad, double precio) {
        this.id = id;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public int compareTo(Transaccion otraTransaccion) {
        int comparacionNombre = this.nombreProducto.compareTo(otraTransaccion.getNombreProducto());
        if (comparacionNombre != 0) {
            return comparacionNombre;
        } else {
            return Integer.compare(this.cantidad, otraTransaccion.getCantidad());
        }
    }

    @Override
    public int compare(Transaccion t1, Transaccion t2) {
        // Implementa tu lógica de comparación aquí
        // Por ejemplo, para ordenar por el nombre del producto:
        return t1.getNombreProducto().compareTo(t2.getNombreProducto());
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Producto: " + nombreProducto + ", Cantidad: " + cantidad + ", Precio: " + precio;
    }
}

