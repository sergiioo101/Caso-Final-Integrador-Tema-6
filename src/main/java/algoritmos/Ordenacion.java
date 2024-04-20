package algoritmos;

import java.util.Arrays;

public class Ordenacion {
    public static void quickSort(Comparable[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(Comparable[] arr, int izquierda, int derecha) {
        if (izquierda >= derecha) {
            return;
        }

        Comparable pivot = arr[(izquierda + derecha) / 2];
        int indice = particion(arr, izquierda, derecha, pivot);

        quickSort(arr, izquierda, indice - 1);
        quickSort(arr, indice, derecha);
    }

    private static int particion(Comparable[] arr, int izquierda, int derecha, Comparable pivot) {
        while (izquierda <= derecha) {
            while (arr[izquierda].compareTo(pivot) < 0) {
                izquierda++;
            }
            while (arr[derecha].compareTo(pivot) > 0) {
                derecha--;
            }
            if (izquierda <= derecha) {
                intercambiar(arr, izquierda, derecha);
                izquierda++;
                derecha--;
            }
        }
        return izquierda;
    }

    private static void intercambiar(Comparable[] arr, int i, int j) {
        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void mostrarArray(Comparable[] arr) {
        System.out.println(Arrays.toString(arr));
    }
}
