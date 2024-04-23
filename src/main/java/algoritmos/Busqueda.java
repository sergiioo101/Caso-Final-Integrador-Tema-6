package algoritmos;


import indexacion.Archivo;

public class Busqueda {
    public static int busquedaBinaria(Archivo[] arr, Archivo elemento) {
        int izquierda = 0;
        int derecha = arr.length - 1;

        while (izquierda <= derecha) {
            int medio = (izquierda + derecha) / 2;
            int comparacion = arr[medio].compareTo(elemento);

            if (comparacion == 0) {
                return medio;
            } else if (comparacion < 0) {
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }
        return -1; // Si no se encuentra el elemento
    }
}

