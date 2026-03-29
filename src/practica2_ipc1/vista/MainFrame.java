/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2_ipc1.vista;

import practica2_ipc1.graficos.ChartManager;
import practica2_ipc1.hilos.SortingThread;
import practica2_ipc1.interfaces.SortingListener;
import practica2_ipc1.util.ArrayUtils;
import practica2_ipc1.util.Constantes;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Gio
 */
public class MainFrame extends JFrame {

    private ControlPanel controlPanel;
    private VisualizationPanel visualizationPanel;
    private ComparisonPanel comparisonPanel;
    private ChartManager chartManager;
    private ChartManager chartManagerIterativo;
    private ChartManager chartManagerRecursivo;
    private SortingThread sortingThread;
    private SortingThread sortingThreadIterativo;
    private SortingThread sortingThreadRecursivo;
    private JTabbedPane tabbedPane;

    public MainFrame() {
        inicializar();
        configurarEventos();
    }

    private void inicializar() {
        setTitle("Visualizador de Algoritmos de Ordenamiento");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        controlPanel = new ControlPanel();
        controlPanel.setPreferredSize(new Dimension(300, getHeight()));
        add(controlPanel, BorderLayout.WEST);

        visualizationPanel = new VisualizationPanel();
        comparisonPanel = new ComparisonPanel();

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Visualización", visualizationPanel);
        tabbedPane.addTab("Comparación", comparisonPanel);
        add(tabbedPane, BorderLayout.CENTER);

        chartManager = new ChartManager();
        chartManager.mostrarGraficaEnPanel(visualizationPanel.getPanelGrafica());

        chartManagerIterativo = new ChartManager();
        chartManagerIterativo.mostrarGraficaEnPanel(comparisonPanel.getPanelGraficaIterativo());

        chartManagerRecursivo = new ChartManager();
        chartManagerRecursivo.mostrarGraficaEnPanel(comparisonPanel.getPanelGraficaRecursivo());

        int[] arregloInicial = {};
        chartManager.actualizarGrafica(arregloInicial);
        chartManagerIterativo.actualizarGrafica(ArrayUtils.copiarArreglo(arregloInicial));
        chartManagerRecursivo.actualizarGrafica(ArrayUtils.copiarArreglo(arregloInicial));

    }

    private void configurarEventos() {
        controlPanel.getBtnIniciar().addActionListener(e -> iniciarProcesoSegunPestana());
        controlPanel.getBtnDetener().addActionListener(e -> detenerTodo());
        controlPanel.getBtnReiniciar().addActionListener(e -> reiniciarTodo());
        controlPanel.getBtnAleatorio().addActionListener(e -> generarDatosAleatorios());

        controlPanel.getCmbAlgoritmo().addActionListener(e -> actualizarVarianteSegunAlgoritmo());
        actualizarVarianteSegunAlgoritmo();

        comparisonPanel.getBtnPausarIterativo().addActionListener(e -> alternarPausaIterativo());
        comparisonPanel.getBtnPausarRecursivo().addActionListener(e -> alternarPausaRecursivo());
    }

    private void iniciarProcesoSegunPestana() {

    int indice = tabbedPane.getSelectedIndex();
    String titulo = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());

    if ("Comparación".equals(titulo)) {
        iniciarComparacion();
    } else {
        iniciarOrdenamientoNormal();
    }
   }

    private void iniciarOrdenamientoNormal() {
        try {
            String texto = controlPanel.getTxtDatos().getText();
            int[] arreglo = ArrayUtils.convertirTextoAArreglo(texto);

            String algoritmo = (String) controlPanel.getCmbAlgoritmo().getSelectedItem();
            String variante = (String) controlPanel.getCmbVariante().getSelectedItem();
            String orden = (String) controlPanel.getCmbOrden().getSelectedItem();
            String velocidadTexto = (String) controlPanel.getCmbVelocidad().getSelectedItem();

            boolean ascendente = Constantes.ORDEN_ASCENDENTE.equals(orden);
            int velocidad = obtenerVelocidadEnMilisegundos(velocidadTexto);

            visualizationPanel.limpiarLog();
            visualizationPanel.reiniciarEstadisticas();
            chartManager.actualizarGrafica(arreglo);

            SortingListener listener = new SortingListener() {
                @Override
                public void alComparar(int[] arreglo, int indice1, int indice2) {
                    SwingUtilities.invokeLater(() ->
                            chartManager.actualizarGrafica(arreglo, indice1, indice2, -1)
                    );
                }

                @Override
                public void alIntercambiar(int[] arreglo, int indice1, int indice2) {
                    SwingUtilities.invokeLater(() ->
                            chartManager.actualizarGraficaIntercambio(arreglo, indice1, indice2)
                    );
                }

                @Override
                public void alActualizarArreglo(int[] arreglo) {
                    SwingUtilities.invokeLater(() ->
                            chartManager.actualizarGrafica(arreglo)
                    );
                }

                @Override
                public void alRegistrarLog(String mensaje) {
                    SwingUtilities.invokeLater(() ->
                            visualizationPanel.agregarLog(mensaje)
                    );
                }

                @Override
                public void alActualizarEstadisticas(int comparaciones, int intercambios, int llamadasRecursivas, long tiempo) {
                    SwingUtilities.invokeLater(() ->
                            visualizationPanel.actualizarEstadisticas(comparaciones, intercambios, llamadasRecursivas, tiempo)
                    );
                }

                @Override
                public void alFinalizar(int[] arreglo) {
                    SwingUtilities.invokeLater(() -> {
                        chartManager.actualizarGrafica(arreglo);
                        visualizationPanel.agregarLog("Ordenamiento finalizado.");
                        controlPanel.getBtnIniciar().setEnabled(true);
                        controlPanel.getBtnDetener().setEnabled(false);
                    });
                }
            };

            sortingThread = new SortingThread(arreglo, algoritmo, variante, ascendente, velocidad, listener);

            controlPanel.getBtnIniciar().setEnabled(false);
            controlPanel.getBtnDetener().setEnabled(true);

            sortingThread.start();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese solo números enteros separados por coma.",
                    "Error de entrada",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error de entrada",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void iniciarComparacion() {
        try {
            String texto = controlPanel.getTxtDatos().getText();
            int[] arregloOriginal = ArrayUtils.convertirTextoAArreglo(texto);

            int[] arregloIterativo = ArrayUtils.copiarArreglo(arregloOriginal);
            int[] arregloRecursivo = ArrayUtils.copiarArreglo(arregloOriginal);

            String orden = (String) controlPanel.getCmbOrden().getSelectedItem();
            String velocidadTexto = (String) controlPanel.getCmbVelocidad().getSelectedItem();

            boolean ascendente = Constantes.ORDEN_ASCENDENTE.equals(orden);
            int velocidad = obtenerVelocidadEnMilisegundos(velocidadTexto);

            comparisonPanel.limpiarTodo();
            chartManagerIterativo.actualizarGrafica(arregloIterativo);
            chartManagerRecursivo.actualizarGrafica(arregloRecursivo);

            SortingListener listenerIterativo = new SortingListener() {
                @Override
                public void alComparar(int[] arreglo, int indice1, int indice2) {
                    SwingUtilities.invokeLater(() ->
                            chartManagerIterativo.actualizarGrafica(arreglo, indice1, indice2, -1)
                    );
                }

                @Override
                public void alIntercambiar(int[] arreglo, int indice1, int indice2) {
                    SwingUtilities.invokeLater(() ->
                            chartManagerIterativo.actualizarGraficaIntercambio(arreglo, indice1, indice2)
                    );
                }

                @Override
                public void alActualizarArreglo(int[] arreglo) {
                    SwingUtilities.invokeLater(() ->
                            chartManagerIterativo.actualizarGrafica(arreglo)
                    );
                }

                @Override
                public void alRegistrarLog(String mensaje) {
                    SwingUtilities.invokeLater(() ->
                            comparisonPanel.agregarLogIterativo(mensaje)
                    );
                }

                @Override
                public void alActualizarEstadisticas(int comparaciones, int intercambios, int llamadasRecursivas, long tiempo) {
                    SwingUtilities.invokeLater(() ->
                            comparisonPanel.actualizarEstadisticasIterativo(comparaciones, intercambios, tiempo)
                    );
                }

                @Override
                public void alFinalizar(int[] arreglo) {
                    SwingUtilities.invokeLater(() -> {
                        chartManagerIterativo.actualizarGrafica(arreglo);
                        comparisonPanel.agregarLogIterativo("Bubble Sort Iterativo finalizado.");
                        verificarFinComparacion();
                    });
                }
            };

            SortingListener listenerRecursivo = new SortingListener() {
                @Override
                public void alComparar(int[] arreglo, int indice1, int indice2) {
                    SwingUtilities.invokeLater(() ->
                            chartManagerRecursivo.actualizarGrafica(arreglo, indice1, indice2, -1)
                    );
                }

                @Override
                public void alIntercambiar(int[] arreglo, int indice1, int indice2) {
                    SwingUtilities.invokeLater(() ->
                            chartManagerRecursivo.actualizarGraficaIntercambio(arreglo, indice1, indice2)
                    );
                }

                @Override
                public void alActualizarArreglo(int[] arreglo) {
                    SwingUtilities.invokeLater(() ->
                            chartManagerRecursivo.actualizarGrafica(arreglo)
                    );
                }

                @Override
                public void alRegistrarLog(String mensaje) {
                    SwingUtilities.invokeLater(() ->
                            comparisonPanel.agregarLogRecursivo(mensaje)
                    );
                }

                @Override
                public void alActualizarEstadisticas(int comparaciones, int intercambios, int llamadasRecursivas, long tiempo) {
                    SwingUtilities.invokeLater(() ->
                            comparisonPanel.actualizarEstadisticasRecursivo(comparaciones, intercambios, llamadasRecursivas, tiempo)
                    );
                }

                @Override
                public void alFinalizar(int[] arreglo) {
                    SwingUtilities.invokeLater(() -> {
                        chartManagerRecursivo.actualizarGrafica(arreglo);
                        comparisonPanel.agregarLogRecursivo("Bubble Sort Recursivo finalizado.");
                        verificarFinComparacion();
                    });
                }
            };

            sortingThreadIterativo = new SortingThread(
                    arregloIterativo,
                    "Bubble Sort",
                    "Iterativo",
                    ascendente,
                    velocidad,
                    listenerIterativo
            );

            sortingThreadRecursivo = new SortingThread(
                    arregloRecursivo,
                    "Bubble Sort",
                    "Recursivo",
                    ascendente,
                    velocidad,
                    listenerRecursivo
            );

            controlPanel.getBtnIniciar().setEnabled(false);
            controlPanel.getBtnDetener().setEnabled(true);
            comparisonPanel.getBtnPausarIterativo().setEnabled(true);
            comparisonPanel.getBtnPausarRecursivo().setEnabled(true);

            sortingThreadIterativo.start();
            sortingThreadRecursivo.start();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese solo números enteros separados por coma.",
                    "Error de entrada",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error de entrada",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verificarFinComparacion() {
        boolean finIterativo = sortingThreadIterativo == null || !sortingThreadIterativo.isAlive();
        boolean finRecursivo = sortingThreadRecursivo == null || !sortingThreadRecursivo.isAlive();

        if (finIterativo && finRecursivo) {
            controlPanel.getBtnIniciar().setEnabled(true);
            controlPanel.getBtnDetener().setEnabled(false);
            comparisonPanel.getBtnPausarIterativo().setEnabled(false);
            comparisonPanel.getBtnPausarRecursivo().setEnabled(false);
        }
    }

    private void alternarPausaIterativo() {
        if (sortingThreadIterativo == null) {
            return;
        }

        if (sortingThreadIterativo.isPausado()) {
            sortingThreadIterativo.reanudarEjecucion();
            comparisonPanel.getBtnPausarIterativo().setText("Pausar Iterativo");
            comparisonPanel.agregarLogIterativo("Iterativo reanudado.");
        } else {
            sortingThreadIterativo.pausarEjecucion();
            comparisonPanel.getBtnPausarIterativo().setText("Reanudar Iterativo");
            comparisonPanel.agregarLogIterativo("Iterativo pausado.");
        }
    }

    private void alternarPausaRecursivo() {
        if (sortingThreadRecursivo == null) {
            return;
        }

        if (sortingThreadRecursivo.isPausado()) {
            sortingThreadRecursivo.reanudarEjecucion();
            comparisonPanel.getBtnPausarRecursivo().setText("Pausar Recursivo");
            comparisonPanel.agregarLogRecursivo("Recursivo reanudado.");
        } else {
            sortingThreadRecursivo.pausarEjecucion();
            comparisonPanel.getBtnPausarRecursivo().setText("Reanudar Recursivo");
            comparisonPanel.agregarLogRecursivo("Recursivo pausado.");
        }
    }

    private void detenerTodo() {
        if (sortingThread != null && sortingThread.isAlive()) {
            sortingThread.detenerEjecucion();
            visualizationPanel.agregarLog("Ordenamiento detenido por el usuario.");
        }

        if (sortingThreadIterativo != null && sortingThreadIterativo.isAlive()) {
            sortingThreadIterativo.detenerEjecucion();
            comparisonPanel.agregarLogIterativo("Iterativo detenido por el usuario.");
        }

        if (sortingThreadRecursivo != null && sortingThreadRecursivo.isAlive()) {
            sortingThreadRecursivo.detenerEjecucion();
            comparisonPanel.agregarLogRecursivo("Recursivo detenido por el usuario.");
        }

        controlPanel.getBtnIniciar().setEnabled(true);
        controlPanel.getBtnDetener().setEnabled(false);
        comparisonPanel.getBtnPausarIterativo().setEnabled(false);
        comparisonPanel.getBtnPausarRecursivo().setEnabled(false);
    }

    private void reiniciarTodo() {
        detenerTodo();

        visualizationPanel.limpiarLog();
        visualizationPanel.reiniciarEstadisticas();
        comparisonPanel.limpiarTodo();

        int[] arregloVacio = new int[0];
        chartManager.actualizarGrafica(arregloVacio);
        chartManagerIterativo.actualizarGrafica(arregloVacio);
        chartManagerRecursivo.actualizarGrafica(arregloVacio);
    }

    private void generarDatosAleatorios() {
        int[] arregloAleatorio = ArrayUtils.generarArregloAleatorio(10, 1, 99);

        StringBuilder texto = new StringBuilder();
        for (int i = 0; i < arregloAleatorio.length; i++) {
            texto.append(arregloAleatorio[i]);
            if (i < arregloAleatorio.length - 1) {
                texto.append(", ");
            }
        }

        controlPanel.getTxtDatos().setText(texto.toString());
        chartManager.actualizarGrafica(arregloAleatorio);
        chartManagerIterativo.actualizarGrafica(ArrayUtils.copiarArreglo(arregloAleatorio));
        chartManagerRecursivo.actualizarGrafica(ArrayUtils.copiarArreglo(arregloAleatorio));
    }

    private int obtenerVelocidadEnMilisegundos(String velocidadTexto) {
        if ("Lenta".equals(velocidadTexto)) {
            return Constantes.VELOCIDAD_LENTA;
        } else if ("Media".equals(velocidadTexto)) {
            return Constantes.VELOCIDAD_MEDIA;
        } else {
            return Constantes.VELOCIDAD_RAPIDA;
        }
    }

    private void actualizarVarianteSegunAlgoritmo() {
        String algoritmoSeleccionado = (String) controlPanel.getCmbAlgoritmo().getSelectedItem();

        if ("Bubble Sort".equals(algoritmoSeleccionado)) {
            controlPanel.getCmbVariante().setEnabled(true);
        } else if ("Shell Sort".equals(algoritmoSeleccionado)) {
            controlPanel.getCmbVariante().setSelectedItem("Iterativo");
            controlPanel.getCmbVariante().setEnabled(false);
        } else if ("Quick Sort".equals(algoritmoSeleccionado)) {
            controlPanel.getCmbVariante().setSelectedItem("Recursivo");
            controlPanel.getCmbVariante().setEnabled(false);
        }
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public VisualizationPanel getVisualizationPanel() {
        return visualizationPanel;
    }

    public ComparisonPanel getComparisonPanel() {
        return comparisonPanel;
    }

    public ChartManager getChartManager() {
        return chartManager;
    }
}