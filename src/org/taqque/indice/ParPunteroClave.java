/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.taqque.indice;

/**
 *
 * @author hernan
 */
public class ParPunteroClave {

    Object puntero;
    int clave;

    public ParPunteroClave(Object puntero, int clave) {
        this.puntero = puntero;
        this.clave = clave;
    }

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public Object getPuntero() {
        return puntero;
    }

    public void setPuntero(Object puntero) {
        this.puntero = puntero;
    }
    
    

    @Override
    public String toString() {
        return "(" + (puntero != null ? puntero : ("null"))
                + "| " + (clave != -1 ? clave : ("null")) + ")";
    } // toString()    
}
