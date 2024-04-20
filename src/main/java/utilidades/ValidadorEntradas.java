package utilidades;

public class ValidadorEntradas {
    public static boolean esRutaValida(String ruta) {
        return ruta != null && !ruta.isEmpty();
    }

    public static boolean esNumeroEntero(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

