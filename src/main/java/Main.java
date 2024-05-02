import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import indexacion.Archivo;
import indexacion.Indexador;
import algoritmos.Busqueda;
import mapas.AsociacionDatos;
import algoritmos.Ordenacion;
import utilidades.Transaccion;
import utilidades.ComparadorTransaccion;

public class Main {
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JPanel panelIndexacion;
    private JPanel panelMapas;
    private JPanel panelAnalisis;
    private JTextField campoDirectorio;
    private JButton botonSeleccionarDirectorio;
    private JButton botonIndexar;
    private JButton botonVisualizar;
    private JButton botonBuscar;
    private JButton botonSalir;

    private List<Archivo> archivosIndexados;
    private JPanel panelArchivosIndexados;
    private AsociacionDatos<String, Archivo> asociacionArchivos;
    private AsociacionDatos<Integer, String> asociacionNumeros;
    private TreeSet<String> nombresArchivosOrdenados;
    private TreeSet<Transaccion> transaccionesOrdenadas;
    private AsociacionDatos<String, Transaccion> asociacionTransacciones;

    public Main() {
        frame = new JFrame("Sistema de Gestión y Análisis de Datos Multidimensionales");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        panelIndexacion = new JPanel();
        panelIndexacion.setLayout(new FlowLayout());

        campoDirectorio = new JTextField(20);
        botonSeleccionarDirectorio = new JButton("Seleccionar Directorio");
        botonIndexar = new JButton("Indexar archivos");
        botonVisualizar = new JButton("Visualizar archivos indexados");
        botonBuscar = new JButton("Buscar");
        botonSalir = new JButton("Salir");
        asociacionArchivos = new AsociacionDatos<>();
        asociacionNumeros = new AsociacionDatos<>();
        nombresArchivosOrdenados = new TreeSet<>();
        asociacionTransacciones = new AsociacionDatos<>();
        transaccionesOrdenadas = new TreeSet<>(new ComparadorTransaccion());

        botonSeleccionarDirectorio.addActionListener(e -> seleccionarDirectorio());

        botonIndexar.addActionListener(e -> {
            String rutaDirectorio = campoDirectorio.getText();
            if (rutaDirectorio.isEmpty()) {
                mostrarMensaje("Debe seleccionar un directorio.");
            } else {
                mostrarMensaje("Indexando archivos en " + rutaDirectorio);
                indexarArchivos(rutaDirectorio);
            }
        });

        botonVisualizar.addActionListener(e -> mostrarArchivosIndexados());

        botonBuscar.addActionListener(e -> buscarArchivo());

        botonSalir.addActionListener(e -> salir());

        panelIndexacion.add(campoDirectorio);
        panelIndexacion.add(botonSeleccionarDirectorio);
        panelIndexacion.add(botonIndexar);
        panelIndexacion.add(botonVisualizar);
        panelIndexacion.add(botonBuscar);
        panelIndexacion.add(botonSalir);

        tabbedPane.addTab("Indexación y Modificación de Archivos", panelIndexacion);

        // Crear el panel para la pestaña de Mapas y Asociación de Datos
        panelMapas = new JPanel();
        panelMapas.setLayout(new FlowLayout());

        JTextField campoNumero = new JTextField(20);
        JTextField campoTexto = new JTextField(20);
        JButton botonAsociar = new JButton("Asociar");
        botonAsociar.addActionListener(e -> {
            try {
                int numero = Integer.parseInt(campoNumero.getText());
                String texto = campoTexto.getText();
                asociarNumeroTexto(numero, texto);
            } catch (NumberFormatException ex) {
                mostrarMensaje("Debe ingresar un número válido.");
            }
        });

        JTextField campoClave = new JTextField(20);
        JButton botonBuscarAsociacion = new JButton("Buscar Asociación");
        botonBuscarAsociacion.addActionListener(e -> {
            try {
                int clave = Integer.parseInt(campoClave.getText());
                buscarAsociacion(clave);
            } catch (NumberFormatException ex) {
                mostrarMensaje("Debe ingresar un número válido.");
            }
        });

        panelMapas.add(new JLabel("Número:"));
        panelMapas.add(campoNumero);
        panelMapas.add(new JLabel("Texto:"));
        panelMapas.add(campoTexto);
        panelMapas.add(botonAsociar);
        panelMapas.add(new JLabel("Clave:"));
        panelMapas.add(campoClave);
        panelMapas.add(botonBuscarAsociacion);

        tabbedPane.addTab("Mapas y Asociación de Datos", panelMapas);

        // Crear el panel para la pestaña de Análisis y Organización de Información
        panelAnalisis = new JPanel();
        panelAnalisis.setLayout(new FlowLayout());

        JTextField campoOrdenar = new JTextField(20);
        JButton botonOrdenar = new JButton("Ordenar");
        botonOrdenar.addActionListener(e -> {
            String criterio = campoOrdenar.getText();
            if (criterio.isEmpty()) {
                mostrarMensaje("Debe ingresar un criterio de ordenación.");
            } else {
                mostrarMensaje("Ordenando transacciones por " + criterio);
                ordenarTransacciones(criterio);
            }
        });

        JTextField campoBuscarRegistro = new JTextField(20);
        JButton botonBuscarRegistro = new JButton("Buscar Registro");
        botonBuscarRegistro.addActionListener(e -> {
            String clave = campoBuscarRegistro.getText();
            if (clave.isEmpty()) {
                mostrarMensaje("Debe ingresar una clave.");
            } else {
                mostrarMensaje("Buscando registro para " + clave);
                buscarRegistro(clave);
            }
        });

        panelAnalisis.add(new JLabel("Criterio de Ordenación:"));
        panelAnalisis.add(campoOrdenar);
        panelAnalisis.add(botonOrdenar);
        panelAnalisis.add(new JLabel("Clave de Registro:"));
        panelAnalisis.add(campoBuscarRegistro);
        panelAnalisis.add(botonBuscarRegistro);

        tabbedPane.addTab("Análisis y Organización de Información", panelAnalisis);

        frame.getContentPane().add(tabbedPane, BorderLayout.NORTH);

        archivosIndexados = new ArrayList<>();
        panelArchivosIndexados = new JPanel();
        panelArchivosIndexados.setLayout(new BoxLayout(panelArchivosIndexados, BoxLayout.Y_AXIS));
        frame.getContentPane().add(panelArchivosIndexados, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    private void seleccionarDirectorio() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int opcion = fileChooser.showOpenDialog(frame);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            File directorioSeleccionado = fileChooser.getSelectedFile();
            campoDirectorio.setText(directorioSeleccionado.getAbsolutePath());
        }
    }

    private void mostrarArchivosIndexados() {
        panelArchivosIndexados.removeAll();
        StringBuilder rutasArchivos = new StringBuilder();
        for (Archivo archivo : archivosIndexados) {
            JLabel labelArchivo = new JLabel(archivo.toString());
            panelArchivosIndexados.add(labelArchivo);
            rutasArchivos.append(archivo.getRuta()).append("\n");
        }
        JOptionPane.showMessageDialog(frame, "Rutas de los archivos:\n" + rutasArchivos.toString());
        panelArchivosIndexados.revalidate();
        panelArchivosIndexados.repaint();
    }

    private void indexarArchivos(String rutaDirectorio) {
        Indexador indexador = new Indexador();
        indexador.indexarDirectorio(rutaDirectorio);
        archivosIndexados = indexador.getArchivosIndexados();
        Ordenacion.quickSort(archivosIndexados.toArray(new Archivo[0])); // Ordena los archivos indexados
        for (Archivo archivo : archivosIndexados) {
            asociacionArchivos.asociar(archivo.getNombre(), archivo);
            nombresArchivosOrdenados.add(archivo.getNombre()); // Agrega el nombre del archivo al TreeSet
        }
        mostrarMensaje("Archivos indexados y ordenados correctamente. Cantidad de archivos indexados: " + archivosIndexados.size());
    }

    private void buscarArchivo() {
        String nombreArchivo = JOptionPane.showInputDialog(frame, "Ingrese el nombre del archivo a buscar:");
        if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
            Archivo archivoBuscado = new Archivo(nombreArchivo); // Use the correct constructor
            int indice = Busqueda.busquedaBinaria(archivosIndexados.toArray(new Archivo[0]), archivoBuscado);
            if (indice != -1) {
                mostrarMensaje("El archivo " + nombreArchivo + " se encuentra en la lista de archivos indexados.");
            } else {
                mostrarMensaje("El archivo " + nombreArchivo + " no se encuentra en la lista de archivos indexados.");
            }
        }
    }

    private void asociarNumeroTexto(int numero, String texto) {
        asociacionNumeros.asociar(numero, texto);
        mostrarMensaje("El número " + numero + " ha sido asociado con el texto '" + texto + "'.");
    }

    private void buscarAsociacion(int clave) {
        if (asociacionNumeros.contieneClave(clave)) {
            String textoAsociado = asociacionNumeros.obtenerValor(clave);
            mostrarMensaje("La clave " + clave + " está asociada con el texto: '" + textoAsociado + "'.");
        } else {
            mostrarMensaje("No se encontró ninguna asociación para la clave " + clave);
        }
    }

    private void ordenarTransacciones(String criterio) {
        // Aquí debes implementar la lógica para ordenar las transacciones según el criterio ingresado
        // Esto dependerá de cómo estés manejando tus datos y de qué clases ya hayas creado para este propósito
    }

    private void buscarRegistro(String clave) {
        if (asociacionTransacciones.contieneClave(clave)) {
            Transaccion transaccion = asociacionTransacciones.obtenerValor(clave);
            mostrarMensaje("La clave " + clave + " está asociada con la transacción: " + transaccion.toString());
        } else {
            mostrarMensaje("No se encontró ningún registro para la clave " + clave);
        }
    }

    private void salir() {
        int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea salir?", "Salir", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje);
    }

    public static void main(String[] args) {
        new Main();
    }
}











