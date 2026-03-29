/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2_ipc1.graficos;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 *
 * @author Gio
 */
public class ChartManager {

    private DefaultCategoryDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private CustomBarRenderer renderer;

    public ChartManager() {
        dataset = new DefaultCategoryDataset();

        chart = ChartFactory.createBarChart(
                "Visualización del Arreglo",
                "Índice",
                "Valor",
                dataset
        );

        configurarGrafica();

        chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
    }

    private void configurarGrafica() {
        CategoryPlot plot = chart.getCategoryPlot();

        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.setOutlinePaint(Color.GRAY);

        renderer = new CustomBarRenderer();
        plot.setRenderer(renderer);

        chart.removeLegend();
    }

    public void mostrarGraficaEnPanel(JPanel panelContenedor) {
        panelContenedor.removeAll();
        panelContenedor.setLayout(new BorderLayout());
        panelContenedor.add(chartPanel, BorderLayout.CENTER);
        panelContenedor.revalidate();
        panelContenedor.repaint();
    }

    public void actualizarGrafica(int[] arreglo) {
        dataset.clear();

        if (arreglo == null) {
            return;
        }

        renderer.limpiarEstados();

        for (int i = 0; i < arreglo.length; i++) {
            dataset.addValue(arreglo[i], "Valores", String.valueOf(i));
        }
    }

    public void actualizarGrafica(int[] arreglo, int indice1, int indice2, int pivote) {
        dataset.clear();

        if (arreglo == null) {
            return;
        }

        renderer.limpiarEstados();
        renderer.setIndiceComparacion1(indice1);
        renderer.setIndiceComparacion2(indice2);
        renderer.setIndicePivote(pivote);

        for (int i = 0; i < arreglo.length; i++) {
            dataset.addValue(arreglo[i], "Valores", String.valueOf(i));
        }
    }

    public void actualizarGraficaIntercambio(int[] arreglo, int indice1, int indice2) {
        dataset.clear();

        if (arreglo == null) {
            return;
        }

        renderer.limpiarEstados();
        renderer.setIndiceIntercambio1(indice1);
        renderer.setIndiceIntercambio2(indice2);

        for (int i = 0; i < arreglo.length; i++) {
            dataset.addValue(arreglo[i], "Valores", String.valueOf(i));
        }
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }

    public JFreeChart getChart() {
        return chart;
    }

    public DefaultCategoryDataset getDataset() {
        return dataset;
    }

    private static class CustomBarRenderer extends BarRenderer {

        private int indiceComparacion1 = -1;
        private int indiceComparacion2 = -1;
        private int indiceIntercambio1 = -1;
        private int indiceIntercambio2 = -1;
        private int indicePivote = -1;

        public void setIndiceComparacion1(int indiceComparacion1) {
            this.indiceComparacion1 = indiceComparacion1;
        }

        public void setIndiceComparacion2(int indiceComparacion2) {
            this.indiceComparacion2 = indiceComparacion2;
        }

        public void setIndiceIntercambio1(int indiceIntercambio1) {
            this.indiceIntercambio1 = indiceIntercambio1;
        }

        public void setIndiceIntercambio2(int indiceIntercambio2) {
            this.indiceIntercambio2 = indiceIntercambio2;
        }

        public void setIndicePivote(int indicePivote) {
            this.indicePivote = indicePivote;
        }

        public void limpiarEstados() {
            indiceComparacion1 = -1;
            indiceComparacion2 = -1;
            indiceIntercambio1 = -1;
            indiceIntercambio2 = -1;
            indicePivote = -1;
        }

        @Override
        public Color getItemPaint(int fila, int columna) {
            if (columna == indiceIntercambio1 || columna == indiceIntercambio2) {
                return new Color(220, 53, 69);
            }

            if (columna == indicePivote) {
                return new Color(111, 66, 193);
            }

            if (columna == indiceComparacion1 || columna == indiceComparacion2) {
                return new Color(255, 193, 7);
            }

            return new Color(100, 149, 237);
        }
    }
}
