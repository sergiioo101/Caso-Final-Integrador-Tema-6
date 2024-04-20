package visualizacion;

import java.util.List;

public class Visualizador {
    public void mostrarArchivos(List<String> archivos) {
        if (archivos.isEmpty()) {
            System.out.println("No hay archivos indexados para mostrar.");
        } else {
            for (String archivo : archivos) {
                System.out.println(archivo);
            }
        }
    }
}
