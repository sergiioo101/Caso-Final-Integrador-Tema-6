package utilidades;

import java.util.Collections;
import java.util.List;

public class ManejadorDatos {
    public static <T extends Comparable<? super T>> void ordenarLista(List<T> lista) {
        Collections.sort(lista);
    }

    public static void imprimirLista(List<?> lista) {
        for (Object elemento : lista) {
            System.out.println(elemento);
        }
    }
}
