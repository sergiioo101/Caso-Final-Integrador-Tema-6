import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
    private JButton botonVisualizar;
    private JButton botonBuscar;
    private JButton botonSalir;

    private List<Archivo> archivosIndexados;
    private JPanel panelArchivosIndexados;

    public Main() {
        frame = new JFrame("Sistema de Gestión y Análisis de Datos Multidimensionales");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        campoDirectorio = new JTextField(20);
        botonSeleccionarDirectorio = new JButton("Seleccionar Directorio");
        botonIndexar = new JButton("Indexar archivos");
        botonVisualizar = new JButton("Visualizar archivos indexados");
        botonBuscar = new JButton("Buscar");
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

        botonVisualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarArchivosIndexados();
            }
        });

        botonBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarArchivo();
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
        panel.add(botonVisualizar);
        panel.add(botonBuscar);
        panel.add(botonSalir);

        frame.getContentPane().add(panel, BorderLayout.NORTH);

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

    private void indexarArchivos(String rutaDirectorio) {
        Indexador indexador = new Indexador();
        indexador.indexarDirectorio(rutaDirectorio);
        archivosIndexados = indexador.getArchivosIndexados();
        Collections.sort(archivosIndexados);
        mostrarMensaje("Archivos indexados correctamente. Cantidad de archivos indexados: " + archivosIndexados.size());
        JOptionPane.showMessageDialog(frame, "Ruta del directorio indexado:\n" + rutaDirectorio);
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

    private void buscarArchivo() {
        String nombreArchivo = JOptionPane.showInputDialog(frame, "Ingrese el nombre del archivo a buscar:");
        if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
            int indice = -1;
            for (int i = 0; i < archivosIndexados.size(); i++) {
                if (archivosIndexados.get(i).getNombre().equals(nombreArchivo)) {
                    indice = i;
                    break;
                }
            }
            if (indice != -1) {
                mostrarMensaje("El archivo " + nombreArchivo + " se encuentra en la lista de archivos indexados.");
            } else {
                mostrarMensaje("El archivo " + nombreArchivo + " no se encuentra en la lista de archivos indexados.");
            }
        }
    }

    private void agregarArchivoUI(final Archivo archivo) {
        final JPanel panelArchivo = new JPanel(new BorderLayout());
        final JLabel labelArchivo = new JLabel(archivo.toString());
        final JLabel labelHorario = new JLabel("Horario: " + archivo.getHorario()); // Asumiendo que 'horario' es un campo accesible en Archivo
        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarArchivo(archivo);
                panelArchivosIndexados.remove(panelArchivo);
                frame.revalidate();
                frame.repaint();
            }
        });
        panelArchivo.add(labelArchivo, BorderLayout.CENTER);
        panelArchivo.add(labelHorario, BorderLayout.NORTH); // Mostrar el horario encima del nombre del archivo
        panelArchivo.add(botonEliminar, BorderLayout.EAST);
        panelArchivosIndexados.add(panelArchivo);
    }


    private void eliminarArchivo(Archivo archivo) {
        archivosIndexados.remove(archivo);
        boolean eliminado = new File(archivo.getRuta()).delete();
        if (!eliminado) {
            mostrarMensaje("No se pudo eliminar el archivo: " + archivo.getRuta());
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











