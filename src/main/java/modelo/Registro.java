package modelo;

import java.util.HashMap;
import java.util.Map;

public class Registro {
    private Map<String, Object> datos;

    public Registro() {
        datos = new HashMap<>();
    }

    public void agregarDato(String clave, Object valor) {
        datos.put(clave, valor);
    }

    public Object obtenerDato(String clave) {
        return datos.get(clave);
    }

    public boolean contieneClave(String clave) {
        return datos.containsKey(clave);
    }

    @Override
    public String toString() {
        return "Registro{" +
                "datos=" + datos +
                '}';
    }
}


