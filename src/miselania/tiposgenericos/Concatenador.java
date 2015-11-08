/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miselania.tiposgenericos;

/**
 *
 * @author hernan
 */
public class Concatenador<E> {
    private E dato1;
    private E dato2;
    @Override
    public String toString() {
        return "El primer dato es: " + getDato1()
                + " y el segundo es: " + getDato2();
    }
    public E getDato1() {
        return dato1;
    }
    public void setDato1(E dato1) {
        this.dato1 = dato1;
    }
    public E getDato2() {
        return dato2;
    }
    public void setDato2(E dato2) {
        this.dato2 = dato2;
    }
} // Fin de clase
