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
public class BubbleSortIterativo {

    private OperationLogger logger;
    private long tiempoInicio;

    public BubbleSortIterativo() {
        logger = new OperationLogger();
    }

    public void ordenar(int[] arreglo, boolean ascendente, int velocidad, SortingListener listener, SortingThread hilo) {
        logger.reiniciar();
        logger.iniciarTiempo();
        tiempoInicio = System.currentTimeMillis();

        int n = arreglo.length;

        for (int i = 0; i < n - 1 && hilo.isEjecutando(); i++) {
            for (int j = 0; j < n - i - 1 && hilo.isEjecutando(); j++) {

                logger.incrementarComparaciones();

                if (listener != null) {
                    listener.alComparar(arreglo, j, j + 1);
                    listener.alRegistrarLog("Comparando posiciones " + j + " y " + (j + 1));
                    listener.alActualizarEstadisticas(
                            logger.getComparaciones(),
                            logger.getIntercambios(),
                            logger.getLlamadasRecursivas(),
                            System.currentTimeMillis() - tiempoInicio
                    );
                }

                hilo.pausarSegunVelocidad();

                boolean debeIntercambiar;

                if (ascendente) {
                    debeIntercambiar = arreglo[j] > arreglo[j + 1];
                } else {
                    debeIntercambiar = arreglo[j] < arreglo[j + 1];
                }

                if (debeIntercambiar) {
                    int temp = arreglo[j];
                    arreglo[j] = arreglo[j + 1];
                    arreglo[j + 1] = temp;

                    logger.incrementarIntercambios();

                    if (listener != null) {
                        listener.alIntercambiar(arreglo, j, j + 1);
                        listener.alRegistrarLog("Intercambio en posiciones " + j + " y " + (j + 1));
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
        }

        logger.finalizarTiempo();

        if (listener != null) {
            listener.alActualizarArreglo(arreglo);
            listener.alRegistrarLog("Bubble Sort Iterativo finalizado.");
            listener.alActualizarEstadisticas(
                    logger.getComparaciones(),
                    logger.getIntercambios(),
                    logger.getLlamadasRecursivas(),
                    logger.getTiempoTotal()
            );
        }
    }
}
    