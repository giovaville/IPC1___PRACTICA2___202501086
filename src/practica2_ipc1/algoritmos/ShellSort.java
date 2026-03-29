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
public class ShellSort {

    private OperationLogger logger;
    private long tiempoInicio;

    public ShellSort() {
        logger = new OperationLogger();
    }

    public void ordenar(int[] arreglo, boolean ascendente, int velocidad, SortingListener listener, SortingThread hilo) {
        logger.reiniciar();
        logger.iniciarTiempo();
        tiempoInicio = System.currentTimeMillis();

        int n = arreglo.length;

        for (int gap = n / 2; gap > 0 && hilo.isEjecutando(); gap /= 2) {
            for (int i = gap; i < n && hilo.isEjecutando(); i++) {
                int temp = arreglo[i];
                int j = i;

                while (j >= gap && hilo.isEjecutando()) {
                    logger.incrementarComparaciones();

                    if (listener != null) {
                        listener.alComparar(arreglo, j, j - gap);
                        listener.alRegistrarLog("Comparando posiciones " + j + " y " + (j - gap) + " con gap " + gap);
                        listener.alActualizarEstadisticas(
                                logger.getComparaciones(),
                                logger.getIntercambios(),
                                logger.getLlamadasRecursivas(),
                                System.currentTimeMillis() - tiempoInicio
                        );
                    }

                    hilo.pausarSegunVelocidad();

                    boolean mover;

                    if (ascendente) {
                        mover = arreglo[j - gap] > temp;
                    } else {
                        mover = arreglo[j - gap] < temp;
                    }

                    if (!mover) {
                        break;
                    }

                    arreglo[j] = arreglo[j - gap];
                    logger.incrementarIntercambios();

                    if (listener != null) {
                        listener.alIntercambiar(arreglo, j, j - gap);
                        listener.alRegistrarLog("Moviendo valor de posición " + (j - gap) + " a posición " + j);
                        listener.alActualizarArreglo(arreglo);
                        listener.alActualizarEstadisticas(
                                logger.getComparaciones(),
                                logger.getIntercambios(),
                                logger.getLlamadasRecursivas(),
                                System.currentTimeMillis() - tiempoInicio
                        );
                    }

                    hilo.pausarSegunVelocidad();
                    j -= gap;
                }

                arreglo[j] = temp;

                if (listener != null) {
                    listener.alActualizarArreglo(arreglo);
                }
            }
        }

        logger.finalizarTiempo();

        if (listener != null) {
            listener.alActualizarArreglo(arreglo);
            listener.alRegistrarLog("Shell Sort finalizado.");
            listener.alActualizarEstadisticas(
                    logger.getComparaciones(),
                    logger.getIntercambios(),
                    logger.getLlamadasRecursivas(),
                    logger.getTiempoTotal()
            );
        }
    }
}

