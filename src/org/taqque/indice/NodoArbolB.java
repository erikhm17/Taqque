/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.taqque.indice;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author hernan
 */
public class NodoArbolB {
    
    // Pares de punteros y claves 
    private List<ParPunteroClave> entradas;
    // último puntero del nodo
    private NodoArbolB pN;
    private int n; // numero total de punteros en el nodo
    // punteros
    private int m=0; // numero actual de punteros
    
    /*
     * Crea un nodo con n punteros y n-1 valores clave
     */
    public NodoArbolB(int n)
    {
        this.n=n;
        this.m=0;
        entradas = new ArrayList<ParPunteroClave>();
        for (int i=0;i<n-1;i++)
        {
            Object p= null; // puntero
            int k= -1; // valor clave
            ParPunteroClave par = new ParPunteroClave(p,k);
            entradas.add(par);
        }
        pN=null;
    }

    public List<ParPunteroClave> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<ParPunteroClave> entradas) {
        this.entradas = entradas;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
    public NodoArbolB getpN() {
        return pN;
    }

    public void setpN(NodoArbolB pN) {
        this.pN = pN;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Nodo: [");
        for (ParPunteroClave par : this.entradas) {
            sb.append(par.toString());
        }
        sb.append("(").append(pN).append(")");
        sb.append("]");
        return sb.toString();

    }
}
