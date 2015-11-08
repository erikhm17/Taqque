/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miselania.tiposgenericos;

/**
 *
 * @author hernan
 */
public class Persona implements Comparable<Persona> {
    // Atributo de Persona
    private String nombre;
    // Constructor de Persona
    public Persona(String nombre) {
        super();
        this.nombre = nombre;
    }
    // Propiedades de nombre
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public int compareTo(Persona o) {
        return nombre.compareTo(o.nombre);
    }
    @Override
    public String toString()
    {
        return "Nombre:"+this.nombre;
    }
}