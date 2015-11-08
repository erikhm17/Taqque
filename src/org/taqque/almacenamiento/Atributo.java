/*
 * Creado el 30 de Noviembre, 2011 por Hernán Nina Hanco
 *
 * Modificado el 11 de Diciembre, 2011 por Hernán Nina Hanco
 *
 * Esto es parte del proyecto Taqque.  
 * Código exclusivamente para fines académicos.
 * 
 * 
 * Universidad Nacional de San Antonio Abad del Cusco
 * Carrera de Ingeniería Informática y de Sistemas
 * 
 */
package org.taqque.almacenamiento;

/**
 * Atributo: Abstracción básica de un atributo como parte del 
 * modelo de datos del SGBD.
 *
 * @author hanconina
 */
public class Atributo implements java.io.Serializable {// la serializacion tiene q tener los hijos

    /** Nombre del atributo */
    private String nombre;
    /** Tipo de datos del atributo. */
    // no se sabe el tipo 
    private Class<? extends Comparable> tipo; // 

    /**
     * Crear un nuevo atributo por su nombre y clase.
     * 
     * @param nombre, representa el nombre del atributo.
     * @param tipo, representa la clase del atributo.
     */
    public Atributo(String nombre,Class<? extends Comparable> tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    } // Atributo()
    
    /**
     * Devuelve el nombre del atributo.
     * 
     * @return nombre del atributo.
     */    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la clase o tipo del atributo.
     * 
     * @return tipo del atributo.
     */

    public Class<? extends Comparable> getTipo() {
        return tipo;
    }

    public void setTipo(Class<? extends Comparable> tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (! (o instanceof Atributo)) return false;
        Atributo a = (Atributo) o;
        return getNombre().equals(a.getNombre()) && 
                getTipo().equals(a.getTipo());
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash*31 + getNombre().hashCode();
        return hash*31 + getTipo().hashCode();
    }
    
    /**
     * Representación Textual.
     */
    @Override
    public String toString() {
        return getNombre() + ":" + getTipo();
    } // toString()    
}
