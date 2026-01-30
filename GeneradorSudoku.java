/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegosudoku;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import java.util.*;

public class GeneradorSudoku {
    public static boolean llenarTablero(int[][] matriz) {
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                if (matriz[f][c] == 0) {
                    List<Integer> nums = obtenerNumerosAleatorios();
                    for (int num : nums) {
                        if (esValido(matriz, f, c, num)) {
                            matriz[f][c] = num;
                            if (llenarTablero(matriz)) return true;
                            matriz[f][c] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean esValido(int[][] m, int f, int c, int n) {
        for (int i = 0; i < 9; i++) if (m[f][i] == n || m[i][c] == n) return false;
        int bf = (f / 3) * 3, bc = (c / 3) * 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (m[bf + i][bc + j] == n) return false;
        return true;
    }

    private static List<Integer> obtenerNumerosAleatorios() {
        List<Integer> lista = new ArrayList<>();
        for (int i = 1; i <= 9; i++) lista.add(i);
        Collections.shuffle(lista);
        return lista;
    }

    public static void removerNumeros(int[][] matriz, int cantidad) {
        Random rand = new Random();
        int eliminados = 0;
        while (eliminados < cantidad) {
            int f = rand.nextInt(9);
            int c = rand.nextInt(9);
            if (matriz[f][c] != 0) {
                matriz[f][c] = 0;
                eliminados++;
            }
        }
    }
}