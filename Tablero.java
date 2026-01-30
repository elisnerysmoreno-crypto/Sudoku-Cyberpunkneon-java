/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegosudoku;

public class Tablero {
    private Celda[][] matriz;

    public Tablero(int[][] mapaInicial) {
        matriz = new Celda[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int val = mapaInicial[i][j];
                matriz[i][j] = new Celda(val, val != 0);
            }
        }
    }

    public boolean colocarNumero(int fila, int col, int num) {
        if (num < 1 || num > 9) return false;
        if (esMovimientoValido(fila, col, num)) {
            matriz[fila][col].setValor(num);
            return true;
        }
        return false;
    }

    private boolean esMovimientoValido(int fila, int col, int num) {
        // Validar fila y columna
        for (int i = 0; i < 9; i++) {
            if (matriz[fila][i].getValor() == num || matriz[i][col].getValor() == num) {
                return false;
            }
        }

        // Validar subcuadrícula 3x3
        int filaInicio = (fila / 3) * 3;
        int colInicio = (col / 3) * 3;
        for (int i = filaInicio; i < filaInicio + 3; i++) {
            for (int j = colInicio; j < colInicio + 3; j++) {
                if (matriz[i][j].getValor() == num) return false;
            }
        }
        return true;
    }

    public void mostrar() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) System.out.println("------+-------+------");
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) System.out.print("| ");
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }
}