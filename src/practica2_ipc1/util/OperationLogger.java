/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2_ipc1.util;

/**
 *
 * @author Gio
 */
public class OperationLogger {
    private int comparaciones;
    private int intercambios;
    private int llamadasRecursivas;

    private long tiempoInicio;
    private long tiempoFin;

    public OperationLogger() {
        reiniciar();
    }

    public void reiniciar() {
        comparaciones = 0;
        intercambios = 0;
        llamadasRecursivas = 0;
        tiempoInicio = 0;
        tiempoFin = 0;
    }

    public void incrementarComparaciones() {
        comparaciones++;
    }

    public void incrementarIntercambios() {
        intercambios++;
    }

    public void incrementarLlamadasRecursivas() {
        llamadasRecursivas++;
    }

    public void iniciarTiempo() {
        tiempoInicio = System.currentTimeMillis();
    }

    public void finalizarTiempo() {
        tiempoFin = System.currentTimeMillis();
    }

    public long getTiempoTotal() {
        return tiempoFin - tiempoInicio;
    }

    public int getComparaciones() {
        return comparaciones;
    }

    public int getIntercambios() {
        return intercambios;
    }

    public int getLlamadasRecursivas() {
        return llamadasRecursivas;
    }
}
