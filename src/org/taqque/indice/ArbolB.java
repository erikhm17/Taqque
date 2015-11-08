/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.taqque.indice;

/**
 *
 * @author hernan
 */
public class ArbolB {
//    // árbol B+ con n=constante
//    int n;
//    NodoArbolB raiz;
//    /*
//     * Crear un árbol B+ con un nodo Raíz Vacio 
//     */
//    public ArbolB(int n) {
//        this.n = n;
//        raiz = new NodoArbolB(n);
//    }
//
//    public void insertar(int k, Object p) {
//        // Hallar el nodo hoja L que debe contener el valor de la clave k
//        NodoArbolB l = buscarNodo(k);
//        // Si L tiene menos de n-1 valores de clave
//        if (l.entradas.get(n - 2).clave == -1) {
//            insertarEnHoja(l, k, p);
//        } else // L ya tiene n-1 valores de la clave, debemos dividirlo
//        {
//            // Crear un nodo L'
//            NodoArbolB lPrima = new NodoArbolB(n);
//            // Copiar L.P1 .... L.Kn-1 a un bloque de memoria T que pueda almacenar n pares (puntero y valor clave)
//            NodoArbolB t = new NodoArbolB(n + 1);
//            copiarTodasLasEntradas(l, t);
//            insertarEnHoja(t, k, p);
//            lPrima.pn = l.pn;
//            l.pn = lPrima;
//            borrarEntradas(l);
//            int ini = 0;
//            int fin = Math.round((t.m - 1) / 2);
//            copiarEntradas(t, l, ini, fin);
//            copiarEntradas(t, lPrima, fin + 1, t.m - 1);
//            int kPrima = lPrima.entradas.get(0).clave;
//            insertarEnPadre(l, kPrima, lPrima);
//        }
//    }
//
//    public void insertarEnPadre(NodoArbolB N, int k, NodoArbolB nPrima) {
//        if (N == raiz) // si n es la raíz
//        {
//            NodoArbolB r = new NodoArbolB(this.n);
//            r.entradas.get(0).puntero = N;
//            r.entradas.get(0).clave = k;
//            r.entradas.get(1).puntero = nPrima;
//            r.m = 2;
//            raiz = r;
//            return;
//        }
//        NodoArbolB p = padre(N);
//        if (p.m<p.n)
//        {
//            ParPunteroClave par = new ParPunteroClave(kPrima);
//            p.entradas.set(m, null)
//        
//        }
//
//    }
//
//    public NodoArbolB padre(NodoArbolB N) {
//        NodoArbolB c = raiz();
//        NodoArbolB p = c;
//        while (!esHoja(c)) {
//            // ki minimo valor de la clave de busqueda, si lo hay, mayor que V
//            int i = posMinClaveMayorAV(N.entradas.get(0).clave, c);
//
//            if (i == -1) // no hay tal valor ki 
//            {
//                // numero de punteror en el nodo
//                int m = c.m;
//                // hacer que ahora sea el puntero que se apunta desde pm
//                p=c;
//                c = (NodoArbolB) c.entradas.get(m).puntero;
//            } else {
//                p=c;
//                c = (NodoArbolB) c.entradas.get(i + 1).puntero;
//                
//            }
//        }
//        return p;
//    }
//
//    public void borrarEntradas(NodoArbolB l) {
//
//        for (int i = 0; i < l.m; i++) {
//            l.entradas.set(i, new ParPunteroClave(null, -1));
//        }
//        l.m = 0;
//
//    }
//
//    public void copiarEntradas(NodoArbolB t, NodoArbolB l, int ini, int fin) {
//        int j = 0;
//        for (int i = ini; i <= fin; i++) {
//            l.entradas.set(j++, t.entradas.get(i));
//            l.m++;
//        }
//    }
//
//    public void copiarTodasLasEntradas(NodoArbolB l, NodoArbolB t) {
//        int i = 0;
//        for (ParPunteroClave p : l.entradas) {
//            t.entradas.set(i++, p);
//            t.m++;
//        }
//    }
//
//    public void insertarEnHoja(NodoArbolB l, int k, Object p) {
//        ParPunteroClave par = new ParPunteroClave(p, k);
//        int k0 = l.entradas.get(0).clave;
//        System.out.println("valor k0: " + k0);
//        if (k0 != -1) {
//            if (k < k0) {
//                System.out.println("insertando en 0");
//                desplazarEntradas(l, 0);
//                l.entradas.set(0, par);
//                l.m++;
//            } else {
//                System.out.println("insertando en ki");
//                int pos = posMinClaveMayorAV(k, l);
//                desplazarEntradas(l, pos + 1);
//                l.entradas.set(pos + 1, par);
//                l.m++;
//            }
//        } else {
//            l.entradas.set(0, par);
//            l.m++;
//        }
//    }
//
//    public void desplazarEntradas(NodoArbolB l, int pos) {
//        System.out.println("m= " + l.m);
//        for (int i = l.m - 1; i >= pos; i--) {
//            ParPunteroClave par = l.entradas.get(i);
//            System.out.println("par: " + par);
//
//            l.entradas.set(i + 1, par);
//        }
//
//    }
//
//    public NodoArbolB raiz() {
//        return raiz;
//    }
//
//    public NodoArbolB buscarNodo(int valor) {
//        NodoArbolB c = raiz();
//        while (!esHoja(c)) {
//            // ki minimo valor de la clave de busqueda, si lo hay, mayor que V
//            int i = posMinClaveMayorAV(valor, c);
//
//            if (i == -1) // no hay tal valor ki 
//            {
//                // numero de punteror en el nodo
//                int m = c.m;
//                // hacer que ahora sea el puntero que se apunta desde pm
//                c = (NodoArbolB) c.entradas.get(m).puntero;
//            } else {
//                c = (NodoArbolB) c.entradas.get(i + 1).puntero;
//            }
//        }
//        return c;
//    }
//
//    public NodoArbolB buscar(int valor) {
//        NodoArbolB c = buscarNodo(valor);
//        // si hay un valor de de la clave ki en C tal que ki=V
//        // entonces el puntero Pi conduce al registro o cajon deseado.
//        if (existeV(valor, c)) {
//            return c;
//        } else // no existe ningun registro con el valor de la clave k
//        {
//            return null;
//        }
//    }
//
//    public int posMinClaveMayorAV(int valor, NodoArbolB c) {
//        int pos = -1;
//        for (int i = 0; i < c.m; i++) {
//            ParPunteroClave par = c.entradas.get(i);
//            if (par.clave != -1) {
//                if (par.clave < valor) {
//                    pos = i;
//                } else {
//                    break;
//                }
//            }
//        }
//
//        return pos;
//    }
//
//    public boolean existeV(int valor, NodoArbolB c) {
//        for (int i = 0; i < c.m; i++) {
//            ParPunteroClave par = c.entradas.get(i);
//            if (par.clave == valor) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean esHoja(NodoArbolB nodo) {
//        for (int i = 0; i < nodo.m; i++) {
//            ParPunteroClave par = nodo.entradas.get(i);
//            if (par.clave != -1 && (par.puntero instanceof NodoArbolB)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public NodoArbolB Padre() {
//        return raiz;
//    }
//
//    public static void main(String[] args) {
//        ArbolB arbol = new ArbolB(3);
//        arbol.insertar(5, null);
//        arbol.insertar(3, null);
//        arbol.insertar(1, null);
//        arbol.insertar(7, null);
//        arbol.insertar(2, null);
//        System.out.println(arbol.raiz());
//    }
}
