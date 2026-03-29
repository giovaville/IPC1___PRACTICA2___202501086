/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica2_ipc1.util;

import java.util.Random;
/**
 *
 * @author Gio
 */
public class ArrayUtils {

    private static final Random random = new Random();

    public static int[] generarArregloAleatorio(int tamaño, int min, int max) {
        if (tamaño <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser mayor a 0");
        }

        int[] arreglo = new int[tamaño];

        for (int i = 0; i < tamaño; i++) {
            arreglo[i] = random.nextInt((max - min) + 1) + min;
        }

        return arreglo;
    }

    public static int[] copiarArreglo(int[] original) {
        if (original == null) {
            return null;
        }

        int[] copia = new int[original.length];

        for (int i = 0; i < original.length; i++) {
            copia[i] = original[i];
        }

        return copia;
    }

    public static void imprimirArreglo(int[] arreglo) {
        if (arreglo == null) {
            System.out.println("Arreglo null");
            return;
        }

        for (int i = 0; i < arreglo.length; i++) {
            System.out.print(arreglo[i] + " ");
        }

        System.out.println();
    }

    public static boolean esValido(int tamaño) {
        return tamaño > 0 && tamaño <= 1000;
    }
    public static int[] convertirTextoAArreglo(String texto) {
    if (texto == null || texto.trim().isEmpty()) {
        throw new IllegalArgumentException("Debe ingresar valores separados por coma.");
    }

    String[] partes = texto.split(",");
    int[] arreglo = new int[partes.length];

    for (int i = 0; i < partes.length; i++) {
        arreglo[i] = Integer.parseInt(partes[i].trim());
    }

    return arreglo;
}
}
