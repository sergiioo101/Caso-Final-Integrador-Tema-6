import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import indexacion.Indexador;
import visualizacion.Visualizador;

public class Main {
    private JFrame frame;
    private JPanel panel;
    private JLabel labelDirectorio;
    private JTextField campoDirectorio;
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

        labelDirectorio = new JLabel("Directorio:");
        campoDirectorio = new JTextField(20);
        botonIndexar = new JButton("Indexar archivos");
        botonVisualizar = new JButton("Visualizar archivos indexados");
        botonSalir = new JButton("Salir");

        botonIndexar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String rutaDirectorio = campoDirectorio.getText();
                if (rutaDirectorio.isEmpty()) {
                    mostrarMensaje("Debe ingresar una ruta de directorio.");
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

        panel.add(labelDirectorio);
        panel.add(campoDirectorio);
        panel.add(botonIndexar);
        panel.add(botonVisualizar);
        panel.add(botonSalir);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        indexador = new Indexador();
        visualizador = new Visualizador();
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

