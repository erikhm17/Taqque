/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.taqque.indice.arbolbmas;

/**
 *
 * @author hernan
 */
public class ArbolBMas {

    private int n;
    private Nodo raiz;

    public ArbolBMas(int n) {
        this.n = n;
        raiz = new Nodo(n);
    }

    public Nodo buscar(int valor) {
        Nodo c = buscarNodo(valor);
        // si hay un valor de de la clave ki en C tal que ki=V
        // entonces el puntero Pi conduce al registro o cajon deseado.
        if (c.existeClave(valor)) {
            return c;
        } else // no existe ningun registro con el valor de la clave k
        {
            return null;
        }
    }

    public Nodo buscarNodo(int valor) {
        Nodo c = raiz;
        while (!esHoja(c)) {
            // ki minimo valor de la clave de busqueda, si lo hay, mayor que Valor buscado
            int i = c.posClave(valor);

            if (i == -1) // no hay tal valor ki 
            {
                // buscar en el nodo señalado por el último puntero
                // m - último puntero
                int m = c.getM();
                // hacer que ahora C sea el puntero que se apunta desde pm
                c = (Nodo) c.getPunteros()[m];
            } else // existe el ki
            {
                c = (Nodo) c.getPunteros()[i];
            }
        }
        return c;
    }

    public boolean esHoja(Nodo nodo) {
        Object[] punteros = nodo.getPunteros();
        if (punteros[0] instanceof Nodo) {
            return false;
        }
        return true;
    }

    public void insertar(int k, Object p) {
        // Hallar el nodo hoja L que debe contener el valor de la clave k
        Nodo l = buscarNodo(k);
        // Si L tiene menos de n-1 valores de clave
        if (l.getnK() + 1 < n - 1) {
            insertarEnHoja(l, k, p);
        } else // L ya tiene n-1 valores de la clave, debemos dividirlo
        {
            // Crear un nodo L'
            Nodo lPrima = new Nodo(n);
            // Copiar L.P1 .... L.Kn-1 a un bloque de memoria T que pueda almacenar n pares (puntero y valor clave)
            Nodo t = new Nodo(n + 1);
            l.copiarEntradasPunteros(t, 0, l.getM());
            l.copiarEntradasClaves(t, 0, l.getnK());
            insertarEnHoja(t, k, p);
            lPrima.getPunteros()[n-1] = l.getPunteros()[n-1];
            l.getPunteros()[n-1] = lPrima;
            l.borrarEntradas();
            int ini = 0;
            int fin = Math.round((t.getN()) / 2);
            t.copiarEntradasPunteros(l, ini, fin-1);
            t.copiarEntradasClaves(l, ini, fin-1);
            
            t.copiarEntradasPunteros(lPrima, fin, t.getM());
            t.copiarEntradasClaves(lPrima, fin, t.getnK());
                    
            int kPrima = lPrima.getClaves()[0];
            insertarEnPadre(l, kPrima, lPrima);
        }
    }

    public void insertarEnHoja(Nodo l, int k, Object p) {
        int k0 = l.getClaves()[0];
        // Si k < L.K1 la primera clave de L, insertar p,k antes de L.P1
        if (k < k0 || k0 == -1) {
            l.insertarClave(p, k, 0);
        } else {
            int pos = l.posClave(k);
            System.out.println("Pos: "+pos);
            l.insertarClave(p, k, pos+1);
        }

    }
    public void insertarEnPadre(Nodo N, int k, Nodo nPrima) {
        if (N == raiz) // si n es la raíz
        {
            Nodo r = new Nodo(n);
            r.getPunteros()[0] = N;
            r.getClaves()[0] = k;
            r.getPunteros()[1] = nPrima;
            r.setM(2);
            raiz = r;
            return;
        }
    }
    

    public static void main(String[] args) {
        ArbolBMas arbol = new ArbolBMas(4);
        arbol.insertar(2, null);
        arbol.insertar(3, null);
        arbol.insertar(5, null);
        arbol.insertar(7, null);
        System.out.println(arbol.raiz);
    }
}