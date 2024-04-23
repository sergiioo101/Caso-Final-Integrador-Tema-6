import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import indexacion.Archivo;
import indexacion.Indexador;
import algoritmos.Busqueda;

public class Main {
    private JFrame frame;
    private JPanel panel;
    private JTextField campoDirectorio;
    private JButton botonSeleccionarDirectorio;
    private JButton botonIndexar;
    private JButton botonBuscar;
    private JButton botonVisualizar;
    private JButton botonSalir;

    private List<Archivo> archivosIndexados;

    public Main() {
        frame = new JFrame("Sistema de Gestión y Análisis de Datos Multidimensionales");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        campoDirectorio = new JTextField(20);
        botonSeleccionarDirectorio = new JButton("Seleccionar Directorio");
        botonIndexar = new JButton("Indexar archivos");
        botonBuscar = new JButton("Buscar archivo por nombre");
        botonVisualizar = new JButton("Visualizar archivos indexados");
        botonSalir = new JButton("Salir");

        botonSeleccionarDirectorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seleccionarDirectorio();
            }
        });

        botonIndexar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String rutaDirectorio = campoDirectorio.getText();
                if (rutaDirectorio.isEmpty()) {
                    mostrarMensaje("Debe seleccionar un directorio.");
                } else {
                    mostrarMensaje("Indexando archivos en " + rutaDirectorio);
                    indexarArchivos(rutaDirectorio);
                }
            }
        });

        botonBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarArchivo();
            }
        });

        botonVisualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarArchivosIndexados();
            }
        });

        botonSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });

        panel.add(campoDirectorio);
        panel.add(botonSeleccionarDirectorio);
        panel.add(botonIndexar);
        panel.add(botonBuscar);
        panel.add(botonVisualizar);
        panel.add(botonSalir);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        archivosIndexados = new ArrayList<>();
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

    private void indexarArchivos(String rutaDirectorio) {
        File directorio = new File(rutaDirectorio);
        if (!directorio.exists() || !directorio.isDirectory()) {
            mostrarMensaje("La ruta especificada no es un directorio válido.");
            return;
        }
        archivosIndexados.clear();
        indexarDirectorio(directorio);
        mostrarMensaje("Archivos indexados correctamente.");
    }

    private void indexarDirectorio(File directorio) {
        File[] archivos = directorio.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile()) {
                    archivosIndexados.add(new Archivo(archivo.getAbsolutePath()));
                } else if (archivo.isDirectory()) {
                    indexarDirectorio(archivo);
                }
            }
        }
    }

    private void buscarArchivo() {
        String nombreArchivo = JOptionPane.showInputDialog(frame, "Ingrese el nombre del archivo a buscar:");
        if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
            int indice = Busqueda.busquedaBinaria(archivosIndexados.toArray(new Archivo[0]), new Archivo(nombreArchivo));
            if (indice != -1) {
                mostrarMensaje("El archivo '" + nombreArchivo + "' fue encontrado en la posición " + indice);
            } else {
                mostrarMensaje("El archivo '" + nombreArchivo + "' no fue encontrado.");
            }
        }
    }

    private void mostrarArchivosIndexados() {
        if (archivosIndexados.isEmpty()) {
            mostrarMensaje("No hay archivos indexados para mostrar.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Archivo archivo : archivosIndexados) {
                sb.append(archivo.toString()).append("\n");
            }
            mostrarMensaje(sb.toString());
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








