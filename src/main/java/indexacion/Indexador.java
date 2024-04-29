package indexacion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Indexador {
    private List<Archivo> archivosIndexados;

    public Indexador() {
        archivosIndexados = new ArrayList<>();
    }

    public void indexarDirectorio(String rutaDirectorio) {
        File directorio = new File(rutaDirectorio);
        if (!directorio.isDirectory()) {
            System.out.println("La ruta especificada no es un directorio.");
            return;
        }
        indexarRecursivo(directorio);
    }

    private void indexarRecursivo(File directorio) {
        File[] archivos = directorio.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    indexarRecursivo(archivo);
                } else {
                    archivosIndexados.add(new Archivo(archivo.getAbsolutePath()));
                }
            }
        }
    }

    public List<Archivo> getArchivosIndexados() {
        return archivosIndexados;
    }
}


