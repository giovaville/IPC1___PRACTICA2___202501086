/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2_ipc1.vista;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Gio
 */
public class ComparisonPanel extends JPanel {

    private JPanel panelGraficaIterativo;
    private JPanel panelGraficaRecursivo;

    private JTextArea txtLogIterativo;
    private JTextArea txtLogRecursivo;

    private JLabel lblComparacionesIterativo;
    private JLabel lblIntercambiosIterativo;
    private JLabel lblTiempoIterativo;

    private JLabel lblComparacionesRecursivo;
    private JLabel lblIntercambiosRecursivo;
    private JLabel lblLlamadasRecursivasRecursivo;
    private JLabel lblTiempoRecursivo;

    private JButton btnPausarIterativo;
    private JButton btnPausarRecursivo;

    public ComparisonPanel() {
        inicializarComponentes();
        configurarPanel();
        agregarComponentes();
    }

    private void inicializarComponentes() {
        panelGraficaIterativo = new JPanel(new BorderLayout());
        panelGraficaIterativo.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panelGraficaRecursivo = new JPanel(new BorderLayout());
        panelGraficaRecursivo.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        txtLogIterativo = new JTextArea();
        txtLogIterativo.setEditable(false);
        txtLogIterativo.setLineWrap(true);
        txtLogIterativo.setWrapStyleWord(true);

        txtLogRecursivo = new JTextArea();
        txtLogRecursivo.setEditable(false);
        txtLogRecursivo.setLineWrap(true);
        txtLogRecursivo.setWrapStyleWord(true);

        lblComparacionesIterativo = new JLabel("Comparaciones: 0");
        lblIntercambiosIterativo = new JLabel("Intercambios: 0");
        lblTiempoIterativo = new JLabel("Tiempo: 0 ms");

        lblComparacionesRecursivo = new JLabel("Comparaciones: 0");
        lblIntercambiosRecursivo = new JLabel("Intercambios: 0");
        lblLlamadasRecursivasRecursivo = new JLabel("Llamadas recursivas: 0");
        lblTiempoRecursivo = new JLabel("Tiempo: 0 ms");

        btnPausarIterativo = new JButton("Pausar Iterativo");
        btnPausarRecursivo = new JButton("Pausar Recursivo");

        btnPausarIterativo.setEnabled(false);
        btnPausarRecursivo.setEnabled(false);
    }

    private void configurarPanel() {
        setLayout(new GridLayout(1, 2, 15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    private void agregarComponentes() {
        add(crearPanelLado(
                "Bubble Sort Iterativo",
                panelGraficaIterativo,
                txtLogIterativo,
                lblComparacionesIterativo,
                lblIntercambiosIterativo,
                null,
                lblTiempoIterativo,
                btnPausarIterativo
        ));

        add(crearPanelLado(
                "Bubble Sort Recursivo",
                panelGraficaRecursivo,
                txtLogRecursivo,
                lblComparacionesRecursivo,
                lblIntercambiosRecursivo,
                lblLlamadasRecursivasRecursivo,
                lblTiempoRecursivo,
                btnPausarRecursivo
        ));
    }

    private JPanel crearPanelLado(String titulo, JPanel panelGrafica, JTextArea txtLog,
                                  JLabel lblComparaciones, JLabel lblIntercambios,
                                  JLabel lblLlamadas, JLabel lblTiempo, JButton boton) {

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        panelPrincipal.add(panelGrafica, BorderLayout.CENTER);

        JScrollPane scrollLog = new JScrollPane(txtLog);

        JPanel panelEstadisticas = new JPanel(new GridLayout(5, 1, 5, 5));
        panelEstadisticas.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
        panelEstadisticas.add(lblComparaciones);
        panelEstadisticas.add(lblIntercambios);
        if (lblLlamadas != null) {
            panelEstadisticas.add(lblLlamadas);
        } else {
            panelEstadisticas.add(new JLabel(""));
        }
        panelEstadisticas.add(lblTiempo);
        panelEstadisticas.add(boton);

        JPanel panelInferior = new JPanel(new GridLayout(1, 2, 10, 10));
        panelInferior.add(scrollLog);
        panelInferior.add(panelEstadisticas);

        panelInferior.setPreferredSize(new Dimension(500, 220));
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        return panelPrincipal;
    }

    public JPanel getPanelGraficaIterativo() {
        return panelGraficaIterativo;
    }

    public JPanel getPanelGraficaRecursivo() {
        return panelGraficaRecursivo;
    }

    public JButton getBtnPausarIterativo() {
        return btnPausarIterativo;
    }

    public JButton getBtnPausarRecursivo() {
        return btnPausarRecursivo;
    }

    public void limpiarTodo() {
        txtLogIterativo.setText("");
        txtLogRecursivo.setText("");

        lblComparacionesIterativo.setText("Comparaciones: 0");
        lblIntercambiosIterativo.setText("Intercambios: 0");
        lblTiempoIterativo.setText("Tiempo: 0 ms");

        lblComparacionesRecursivo.setText("Comparaciones: 0");
        lblIntercambiosRecursivo.setText("Intercambios: 0");
        lblLlamadasRecursivasRecursivo.setText("Llamadas recursivas: 0");
        lblTiempoRecursivo.setText("Tiempo: 0 ms");

        btnPausarIterativo.setText("Pausar Iterativo");
        btnPausarRecursivo.setText("Pausar Recursivo");
        btnPausarIterativo.setEnabled(false);
        btnPausarRecursivo.setEnabled(false);
    }

    public void agregarLogIterativo(String mensaje) {
        txtLogIterativo.append(mensaje + "\n");
    }

    public void agregarLogRecursivo(String mensaje) {
        txtLogRecursivo.append(mensaje + "\n");
    }

    public void actualizarEstadisticasIterativo(int comparaciones, int intercambios, long tiempo) {
        lblComparacionesIterativo.setText("Comparaciones: " + comparaciones);
        lblIntercambiosIterativo.setText("Intercambios: " + intercambios);
        lblTiempoIterativo.setText("Tiempo: " + tiempo + " ms");
    }

    public void actualizarEstadisticasRecursivo(int comparaciones, int intercambios, int llamadasRecursivas, long tiempo) {
        lblComparacionesRecursivo.setText("Comparaciones: " + comparaciones);
        lblIntercambiosRecursivo.setText("Intercambios: " + intercambios);
        lblLlamadasRecursivasRecursivo.setText("Llamadas recursivas: " + llamadasRecursivas);
        lblTiempoRecursivo.setText("Tiempo: " + tiempo + " ms");
    }
}