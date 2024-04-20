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
import java.util.List;

import indexacion.Indexador;
import visualizacion.Visualizador;

public class Main {
    private JFrame frame;
    private JPanel panel;
    private JTextField campoDirectorio;
    private JButton botonSeleccionarDirectorio;
    private JButton botonIndexar;
    private JButton botonVisualizar;
    private JButton botonSalir;

    private Indexador indexador;
    private Visualizador visualizador;

    public Main() {
        frame = new JFrame("Sistema de Gestión y Análisis de Datos Multidimensionales");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        campoDirectorio = new JTextField(20);
        botonSeleccionarDirectorio = new JButton("Seleccionar Directorio");
        botonIndexar = new JButton("Indexar archivos");
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
        panel.add(botonVisualizar);
        panel.add(botonSalir);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        indexador = new Indexador();
        visualizador = new Visualizador();
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
        indexador.indexarDirectorio(rutaDirectorio);
        mostrarMensaje("Archivos indexados correctamente.");
    }

    private void mostrarArchivosIndexados() {
        List<String> archivosIndexados = indexador.getArchivosIndexados();
        if (archivosIndexados.isEmpty()) {
            mostrarMensaje("No hay archivos indexados para mostrar.");
        } else {
            visualizador.mostrarArchivos(archivosIndexados);
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


