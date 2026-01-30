/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegosudoku;

public class Celda {
    private int valor;
    private boolean esFija; // Si es true, el jugador no puede cambiarla

    public Celda(int valor, boolean esFija) {
        this.valor = valor;
        this.esFija = esFija;
    }

    public int getValor() { return valor; }
    
    public void setValor(int valor) {
        if (!esFija) {
            this.valor = valor;
        }
    }

    public boolean esFija() { return esFija; }

    @Override
    public String toString() {
        return (valor == 0) ? "." : String.valueOf(valor);
    }
}