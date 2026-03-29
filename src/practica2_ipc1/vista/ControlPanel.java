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
public class ControlPanel extends JPanel {

    private JTextField txtDatos;
    private JComboBox<String> cmbAlgoritmo;
    private JComboBox<String> cmbVariante;
    private JComboBox<String> cmbOrden;
    private JComboBox<String> cmbVelocidad;

    private JButton btnIniciar;
    private JButton btnDetener;
    private JButton btnReiniciar;
    private JButton btnAleatorio;

    public ControlPanel() {
        inicializarComponentes();
        configurarPanel();
        agregarComponentes();
    }

    private void inicializarComponentes() {
        txtDatos = new JTextField();

        cmbAlgoritmo = new JComboBox<>(new String[]{
                "Bubble Sort",
                "Shell Sort",
                "Quick Sort"
        });

        cmbVariante = new JComboBox<>(new String[]{
                "Iterativo",
                "Recursivo"
        });

        cmbOrden = new JComboBox<>(new String[]{
                "Ascendente",
                "Descendente"
        });

        cmbVelocidad = new JComboBox<>(new String[]{
                "Lenta",
                "Media",
                "Rápida"
        });

        btnIniciar = new JButton("Iniciar");
        btnDetener = new JButton("Detener");
        btnReiniciar = new JButton("Reiniciar");
        btnAleatorio = new JButton("Aleatorio");

        btnDetener.setEnabled(false);
    }

    private void configurarPanel() {
        setLayout(new GridLayout(12, 1, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Controles"));
    }

    private void agregarComponentes() {
        add(new JLabel("Datos:"));
        add(txtDatos);

        add(new JLabel("Algoritmo:"));
        add(cmbAlgoritmo);

        add(new JLabel("Variante:"));
        add(cmbVariante);

        add(new JLabel("Orden:"));
        add(cmbOrden);

        add(new JLabel("Velocidad:"));
        add(cmbVelocidad);

        add(btnIniciar);
        add(btnDetener);
        add(btnReiniciar);
        add(btnAleatorio);
    }

    // GETTERS

    public JTextField getTxtDatos() {
        return txtDatos;
    }

    public JComboBox<String> getCmbAlgoritmo() {
        return cmbAlgoritmo;
    }

    public JComboBox<String> getCmbVariante() {
        return cmbVariante;
    }

    public JComboBox<String> getCmbOrden() {
        return cmbOrden;
    }

    public JComboBox<String> getCmbVelocidad() {
        return cmbVelocidad;
    }

    public JButton getBtnIniciar() {
        return btnIniciar;
    }

    public JButton getBtnDetener() {
        return btnDetener;
    }

    public JButton getBtnReiniciar() {
        return btnReiniciar;
    }

    public JButton getBtnAleatorio() {
        return btnAleatorio;
    }
}