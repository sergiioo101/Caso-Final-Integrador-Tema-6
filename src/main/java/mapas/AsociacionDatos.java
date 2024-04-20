package mapas;

import java.util.HashMap;
import java.util.Map;

public class AsociacionDatos<K, V> {
    private Map<K, V> mapa;

    public AsociacionDatos() {
        mapa = new HashMap<>();
    }

    public void asociar(K clave, V valor) {
        mapa.put(clave, valor);
    }

    public V obtenerValor(K clave) {
        return mapa.get(clave);
    }

    public boolean contieneClave(K clave) {
        return mapa.containsKey(clave);
    }

    public void eliminarAsociacion(K clave) {
        mapa.remove(clave);
    }

    public void mostrarAsociaciones() {
        for (Map.Entry<K, V> entry : mapa.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
