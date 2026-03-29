/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2_ipc1.algoritmos;

import practica2_ipc1.hilos.SortingThread;
import practica2_ipc1.interfaces.SortingListener;
import practica2_ipc1.util.OperationLogger;
/**
 *
 * @author Gio
 */
public class QuickSort {

    private OperationLogger logger;
    private long tiempoInicio;

    public QuickSort() {
        logger = new OperationLogger();
    }

    public void ordenar(int[] arreglo, boolean ascendente, int velocidad, SortingListener listener, SortingThread hilo) {
        logger.reiniciar();
        logger.iniciarTiempo();
        tiempoInicio = System.currentTimeMillis();

        quickSort(arreglo, 0, arreglo.length - 1, ascendente, listener, hilo);

        logger.finalizarTiempo();

        if (listener != null) {
            listener.alActualizarArreglo(arreglo);
            listener.alRegistrarLog("Quick Sort finalizado.");
            listener.alActualizarEstadisticas(
                    logger.getComparaciones(),
                    logger.getIntercambios(),
                    logger.getLlamadasRecursivas(),
                    logger.getTiempoTotal()
            );
        }
    }

    private void quickSort(int[] arreglo, int low, int high, boolean ascendente, SortingListener listener, SortingThread hilo) {
        if (!hilo.isEjecutando()) {
            return;
        }

        logger.incrementarLlamadasRecursivas();

        if (low < high) {
            int pi = partition(arreglo, low, high, ascendente, listener, hilo);

            quickSort(arreglo, low, pi - 1, ascendente, listener, hilo);
            quickSort(arreglo, pi + 1, high, ascendente, listener, hilo);
        }
    }

    private int partition(int[] arreglo, int low, int high, boolean ascendente, SortingListener listener, SortingThread hilo) {
        int pivote = arreglo[high];
        int i = low - 1;

        if (listener != null) {
            listener.alRegistrarLog("Nuevo pivote en posición " + high + ": " + pivote);
        }

        for (int j = low; j < high && hilo.isEjecutando(); j++) {
            logger.incrementarComparaciones();

            if (listener != null) {
                listener.alComparar(arreglo, j, high);
                listener.alRegistrarLog("Comparando posición " + j + " con pivote en posición " + high);
                listener.alActualizarEstadisticas(
                        logger.getComparaciones(),
                        logger.getIntercambios(),
                        logger.getLlamadasRecursivas(),
                        System.currentTimeMillis() - tiempoInicio
                );
            }

            hilo.pausarSegunVelocidad();

            boolean condicion;

            if (ascendente) {
                condicion = arreglo[j] < pivote;
            } else {
                condicion = arreglo[j] > pivote;
            }

            if (condicion) {
                i++;

                int temp = arreglo[i];
                arreglo[i] = arreglo[j];
                arreglo[j] = temp;

                logger.incrementarIntercambios();

                if (listener != null) {
                    listener.alIntercambiar(arreglo, i, j);
                    listener.alRegistrarLog("Intercambio entre posiciones " + i + " y " + j);
                    listener.alActualizarArreglo(arreglo);
                    listener.alActualizarEstadisticas(
                            logger.getComparaciones(),
                            logger.getIntercambios(),
                            logger.getLlamadasRecursivas(),
                            System.currentTimeMillis() - tiempoInicio
                    );
                }

                hilo.pausarSegunVelocidad();
            }
        }

        int temp = arreglo[i + 1];
        arreglo[i + 1] = arreglo[high];
        arreglo[high] = temp;

        logger.incrementarIntercambios();

        if (listener != null) {
            listener.alIntercambiar(arreglo, i + 1, high);
            listener.alRegistrarLog("Colocando pivote en su posición final: " + (i + 1));
            listener.alActualizarArreglo(arreglo);
            listener.alActualizarEstadisticas(
                    logger.getComparaciones(),
                    logger.getIntercambios(),
                    logger.getLlamadasRecursivas(),
                    System.currentTimeMillis() - tiempoInicio
            );
        }

        hilo.pausarSegunVelocidad();

        return i + 1;
    }
}
