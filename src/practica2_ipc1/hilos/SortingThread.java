 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2_ipc1.hilos;

import practica2_ipc1.algoritmos.BubbleSortIterativo;
import practica2_ipc1.algoritmos.BubbleSortRecursivo;
import practica2_ipc1.algoritmos.QuickSort;
import practica2_ipc1.algoritmos.ShellSort;
import practica2_ipc1.interfaces.SortingListener;
/**
 *
 * @author Gio
 */
public class SortingThread extends Thread {
    
    private int[] arreglo;
    private String algoritmo;
    private String variante;
    private boolean ascendente;
    private int velocidad;
    private SortingListener listener;
    private volatile boolean ejecutando;
    private volatile boolean pausado;

    public SortingThread(int[] arreglo, String algoritmo, String variante, boolean ascendente, int velocidad, SortingListener listener) {
        this.arreglo = arreglo;
        this.algoritmo = algoritmo;
        this.variante = variante;
        this.ascendente = ascendente;
        this.velocidad = velocidad;
        this.listener = listener;
        this.ejecutando = true;
        this.pausado = false;
    }

    @Override
    public void run() {
        try {
            if ("Bubble Sort".equals(algoritmo)) {
                if ("Iterativo".equals(variante)) {
                    BubbleSortIterativo bubbleIterativo = new BubbleSortIterativo();
                    bubbleIterativo.ordenar(arreglo, ascendente, velocidad, listener, this);
                } else if ("Recursivo".equals(variante)) {
                    BubbleSortRecursivo bubbleRecursivo = new BubbleSortRecursivo();
                    bubbleRecursivo.ordenar(arreglo, ascendente, velocidad, listener, this);
                }
            } else if ("Shell Sort".equals(algoritmo)) {
                ShellSort shellSort = new ShellSort();
                shellSort.ordenar(arreglo, ascendente, velocidad, listener, this);
            } else if ("Quick Sort".equals(algoritmo)) {
                QuickSort quickSort = new QuickSort();
                quickSort.ordenar(arreglo, ascendente, velocidad, listener, this);
            }

            if (listener != null && ejecutando) {
                listener.alFinalizar(arreglo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isEjecutando() {
        return ejecutando;
    }

    public void detenerEjecucion() {
        ejecutando = false;
        reanudarEjecucion();
    }

    public synchronized void pausarEjecucion() {
        pausado = true;
    }

    public synchronized void reanudarEjecucion() {
        pausado = false;
        notifyAll();
    }

    public boolean isPausado() {
        return pausado;
    }

    private synchronized void esperarSiEstaPausado() {
        while (pausado && ejecutando) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public void pausarSegunVelocidad() {
        esperarSiEstaPausado();

        try {
            Thread.sleep(velocidad);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}