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
public class VisualizationPanel extends JPanel {

    private JPanel panelGrafica;
    private JTextArea txtAreaLog;
    private JScrollPane scrollLog;
    private JPanel panelEstadisticas;

    private JLabel lblTituloGrafica;
    private JLabel lblTituloLog;
    private JLabel lblTituloEstadisticas;

    private JLabel lblComparaciones;
    private JLabel lblIntercambios;
    private JLabel lblLlamadasRecursivas;
    private JLabel lblTiempo;

    private JLabel lblValorComparaciones;
    private JLabel lblValorIntercambios;
    private JLabel lblValorLlamadasRecursivas;
    private JLabel lblValorTiempo;

    public VisualizationPanel() {
        inicializarComponentes();
        configurarPanel();
        agregarComponentes();
    }

    private void inicializarComponentes() {
        panelGrafica = new JPanel();
        panelGrafica.setLayout(new BorderLayout());
        panelGrafica.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        lblTituloGrafica = new JLabel("Área de visualización del arreglo");
        lblTituloGrafica.setHorizontalAlignment(SwingConstants.CENTER);
        lblTituloGrafica.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblPlaceholderGrafica = new JLabel("Aquí se mostrará la gráfica de barras");
        lblPlaceholderGrafica.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlaceholderGrafica.setFont(new Font("Arial", Font.PLAIN, 16));

        panelGrafica.add(lblTituloGrafica, BorderLayout.NORTH);
        panelGrafica.add(lblPlaceholderGrafica, BorderLayout.CENTER);

        lblTituloLog = new JLabel("Log de operaciones");
        lblTituloLog.setFont(new Font("Arial", Font.BOLD, 16));

        txtAreaLog = new JTextArea();
        txtAreaLog.setEditable(false);
        txtAreaLog.setLineWrap(true);
        txtAreaLog.setWrapStyleWord(true);
        txtAreaLog.setText("Aquí aparecerán las comparaciones, intercambios y pasos del algoritmo.");
        scrollLog = new JScrollPane(txtAreaLog);

        lblTituloEstadisticas = new JLabel("Estadísticas");
        lblTituloEstadisticas.setFont(new Font("Arial", Font.BOLD, 16));

        panelEstadisticas = new JPanel();
        panelEstadisticas.setLayout(new GridLayout(4, 2, 8, 8));
        panelEstadisticas.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        lblComparaciones = new JLabel("Comparaciones:");
        lblIntercambios = new JLabel("Intercambios:");
        lblLlamadasRecursivas = new JLabel("Llamadas recursivas:");
        lblTiempo = new JLabel("Tiempo:");

        lblValorComparaciones = new JLabel("0");
        lblValorIntercambios = new JLabel("0");
        lblValorLlamadasRecursivas = new JLabel("0");
        lblValorTiempo = new JLabel("0 ms");

        panelEstadisticas.add(lblComparaciones);
        panelEstadisticas.add(lblValorComparaciones);
        panelEstadisticas.add(lblIntercambios);
        panelEstadisticas.add(lblValorIntercambios);
        panelEstadisticas.add(lblLlamadasRecursivas);
        panelEstadisticas.add(lblValorLlamadasRecursivas);
        panelEstadisticas.add(lblTiempo);
        panelEstadisticas.add(lblValorTiempo);
    }

    private void configurarPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    private void agregarComponentes() {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelGrafica, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new GridLayout(1, 2, 10, 10));

        JPanel panelLogCompleto = new JPanel(new BorderLayout(5, 5));
        panelLogCompleto.add(lblTituloLog, BorderLayout.NORTH);
        panelLogCompleto.add(scrollLog, BorderLayout.CENTER);

        JPanel panelEstadisticasCompleto = new JPanel(new BorderLayout(5, 5));
        panelEstadisticasCompleto.add(lblTituloEstadisticas, BorderLayout.NORTH);
        panelEstadisticasCompleto.add(panelEstadisticas, BorderLayout.CENTER);

        panelInferior.add(panelLogCompleto);
        panelInferior.add(panelEstadisticasCompleto);

        add(panelSuperior, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        panelSuperior.setPreferredSize(new Dimension(700, 350));
        panelInferior.setPreferredSize(new Dimension(700, 220));
    }

    public JPanel getPanelGrafica() {
        return panelGrafica;
    }

    public JTextArea getTxtAreaLog() {
        return txtAreaLog;
    }

    public JLabel getLblValorComparaciones() {
        return lblValorComparaciones;
    }

    public JLabel getLblValorIntercambios() {
        return lblValorIntercambios;
    }

    public JLabel getLblValorLlamadasRecursivas() {
        return lblValorLlamadasRecursivas;
    }

    public JLabel getLblValorTiempo() {
        return lblValorTiempo;
    }

    public void agregarLog(String mensaje) {
        txtAreaLog.append("\n" + mensaje);
    }

    public void limpiarLog() {
        txtAreaLog.setText("");
    }

    public void actualizarEstadisticas(int comparaciones, int intercambios, int llamadasRecursivas, long tiempo) {
        lblValorComparaciones.setText(String.valueOf(comparaciones));
        lblValorIntercambios.setText(String.valueOf(intercambios));
        lblValorLlamadasRecursivas.setText(String.valueOf(llamadasRecursivas));
        lblValorTiempo.setText(tiempo + " ms");
    }

    public void reiniciarEstadisticas() {
        lblValorComparaciones.setText("0");
        lblValorIntercambios.setText("0");
        lblValorLlamadasRecursivas.setText("0");
        lblValorTiempo.setText("0 ms");
    }
}
