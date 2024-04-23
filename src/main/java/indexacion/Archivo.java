package indexacion;

import java.io.File;
import java.util.Date;

public class Archivo implements Comparable<Archivo> {
    private String nombre;
    private String ruta;
    private long tamaño;
    private Date fechaModificacion;

    public Archivo(String ruta) {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            throw new IllegalArgumentException("El archivo especificado no existe.");
        }
        this.nombre = archivo.getName();
        this.ruta = archivo.getAbsolutePath();
        this.tamaño = archivo.length();
        this.fechaModificacion = new Date(archivo.lastModified());
    }

    public String getNombre() {
        return nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public long getTamaño() {
        return tamaño;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    @Override
    public int compareTo(Archivo otroArchivo) {
        return this.nombre.compareTo(otroArchivo.getNombre());
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Ruta: " + ruta + ", Tamaño: " + tamaño + " bytes, Fecha de Modificación: " + fechaModificacion;
    }
}


