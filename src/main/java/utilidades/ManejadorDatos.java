package utilidades;

import java.util.Collections;
import java.util.List;

public class ManejadorDatos {
    public static void ordenarLista(List<? extends Comparable<?>> lista) {
        Collections.sort(lista);
    }

    public static void imprimirLista(List<?> lista) {
        for (Object elemento : lista) {
            System.out.println(elemento);
        }
    }
}
