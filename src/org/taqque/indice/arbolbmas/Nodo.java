/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.taqque.indice.arbolbmas;

/**
 *
 * @author hernan
 */
public class Nodo {

    private int n; // numero total de punteros del nodo
    private int m = -1; //último puntero
    private int nK = -1; // última clave
    private Object[] punteros;
    private int[] claves;

    public Nodo(int n) {
        this.n = n;
        punteros = new Object[n];
        for (int i = 0; i < n; i++) {
            punteros[i] = null;
        }
        claves = new int[n - 1];
        for (int j = 0; j < n - 1; j++) {
            claves[j] = -1;
        }
    }
    /*
     * ki minimo valor de la clave de busqueda, si lo hay, mayor que Valor clave buscado
     */

    public int posClave(int valor) {
        int pos = -1;
        for (int i = 0; i <= nK; i++) {
            if (claves[i] != -1) {
                if (claves[i] < valor) {
                    pos = i;
                } else {
                    break;
                }
            }
        }
        return pos;
    }

    public boolean existeClave(int valor) {
        for (int i = 0; i < nK; i++) {
            if (claves[i] == valor) {
                return true;
            }
        }
        return false;
    }

    public void insertarClave(Object p, int k, int pos) {
        desplazarEntradas(pos);
        punteros[pos] = p;
        m++;
        claves[pos] = k;
        nK++;
    }

    private void desplazarEntradas(int pos) {
        for (int i = nK; i >= pos; i--) {
            int k = claves[i];
            claves[i + 1] = k;
        }
        for (int i = m; i >= pos; i--) {
            Object p = punteros[i];
            punteros[i + 1] = p;
        }
    }

    public void copiarEntradasPunteros(Nodo t, int ini, int fin) {
        int j=0;
        for (int i = ini; i <= fin; i++) {
            t.getPunteros()[j++] = punteros[i];
            t.setM(t.getM() + 1);
        }
    }

    public void copiarEntradasClaves(Nodo t, int ini, int fin) {
        int j=0;
        for (int i = ini; i <= fin; i++) {
            t.getClaves()[j++] = claves[i];
            t.setnK(t.getnK() + 1);
        }
    }

    public void borrarEntradas() {
        for (int i = 0; i <= m; i++) {
            punteros[i] = null;
        }
        for (int i = 0; i <= nK; i++) {
            claves[i] = -1;
        }
    }

    public int[] getClaves() {
        return claves;
    }

    public void setClaves(int[] claves) {
        this.claves = claves;
    }

    public Object[] getPunteros() {
        return punteros;
    }

    public void setPunteros(Object[] punteros) {
        this.punteros = punteros;
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

    public int getnK() {
        return nK;
    }

    public void setnK(int nK) {
        this.nK = nK;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < n; i++) {
            sb.append("(");
            sb.append(punteros[i] != null ? punteros[i] : "null");
            if (n > i + 1) {
                sb.append("|");
                sb.append(claves[i] != -1 ? claves[i] : "null");
                sb.append(")");
            }
        }
        sb.append(")");
        sb.append("]");
        return sb.toString();
    }
}
