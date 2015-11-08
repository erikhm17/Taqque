/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miselania.tiposgenericos;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author hernan
 */
public class PruebaComparable {
    public static void main(String[] args)
    {
       // Crear una Lista de Personas 
       Set <Persona> lista = new TreeSet<Persona>();
       lista.add(new Persona("Juan"));
       lista.add(new Persona("Alberto"));
       lista.add(new Persona("Ivan"));
       lista.add(new Persona("Angel"));
       // Imprimir la lista de personas
       System.out.println(lista);
    }
}
