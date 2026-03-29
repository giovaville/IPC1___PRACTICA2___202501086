/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2_ipc1.interfaces;

/**
 *
 * @author Gio
 */
public interface SortingListener {

    void alComparar(int[] arreglo, int indice1, int indice2);

    void alIntercambiar(int[] arreglo, int indice1, int indice2);

    void alActualizarArreglo(int[] arreglo);

    void alRegistrarLog(String mensaje);

    void alActualizarEstadisticas(int comparaciones, int intercambios, int llamadasRecursivas, long tiempo);

    void alFinalizar(int[] arreglo);
}