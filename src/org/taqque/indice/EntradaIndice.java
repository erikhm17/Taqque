/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.taqque.indice;

import org.taqque.almacenamiento.Tupla;

/**
 *
 * @author hernan
 */
public class EntradaIndice {
    // clave de búsqueda
    Comparable claveBusqueda;
    Tupla puntero;
    EntradaIndice siguienteEntrada;
}
