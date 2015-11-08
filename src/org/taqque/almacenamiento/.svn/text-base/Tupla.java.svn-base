package org.taqque.almacenamiento;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hernan
 */
public class Tupla {

    /** Identificador de la tupla. */
    private IdentificadorTupla idTupla;
    /** Valores almacenados en la tupla. */
    private List<Comparable> valores;

    /**
     * Constructor de una nueva <code>Tupla</code> vácia.
     */
    public Tupla() {
        this.idTupla = null;
        this.valores = new ArrayList<Comparable>();
    } // Tupla()

    /**
     * Constructor de una nueva tupla dado un identificador y  
     * <code>Lista</code> de valores.
     * 
     * @param idTupla, identificador de la tupla.
     * @param valores el valores a ser añadidos.
     */
    public Tupla(IdentificadorTupla idTupla, List<Comparable> valores) {
        this.idTupla = idTupla;
        this.valores = valores;
    } // Tupla()

    /**
     * Devolver el identificador de la tupla.
     * 
     * @return el identificador de la tupla.
     */
    public IdentificadorTupla getIdTupla() {
        return idTupla;
    } // getIdTupla()

    /**
     * Cambiar el valor de una celda de la tupla.
     * 
     * @param celda, la celda de la tupla a la que se accede.
     * @param valor, el nuevo valor en la celda de la tupla.
     */
    public void setValor(int celda, Comparable valor) {
        valores.set(celda, valor);
    } // setValor()

    /**
     * Cambia los valores de la tupla.
     * 
     * @param valores, una lista de los valores nuevos.
     */
    public void setValores(List<Comparable> values) {
        this.valores.clear();
        this.valores = values;
    } // setValores()

    /**
     * Devuelve la cantidad de celdas de la tupla.
     * 
     * @return numero de celdas en la tupla.
     */
    public int size() {
        return valores.size();
    } // size()

    /**
     * Devuelve los valores de la tupla.
     * 
     * @return valores de la tupla.
     */
    public List<Comparable> getValores() {
        return valores;
    } // getValores()

    /**
     * Devolver la celda de una tupla como tipo primitivo character.
     * 
     * @param celda, la celda de la <code>Tupla</code> accedida.
     * @return un celda específica de la tupla como un tipo primitivo
     * character.
     * 
     */
    public char asChar(int celda) {
        Character c = (Character) valores.get(celda);
        return c.charValue();
    } // asChar()

    /**
     * Devolver la celda de una tupla como tipo primitivo Byte.
     * 
     * @param celda, la celda de la <code>Tupla</code> accedida.
     * @return un celda específica de la tupla como un tipo primitivo
     * Byte.
     * 
     */
    public byte asByte(int celda) {
        Byte b = (Byte) valores.get(celda);
        return b.byteValue();
    } // asByte()

    /**
     * Devolver la celda de una tupla como tipo primitivo Short.
     * 
     * @param celda, la celda de la <code>Tupla</code> accedida.
     * @return un celda específica de la tupla como un tipo primitivo
     * Short.
     * 
     */
    public short asShort(int celda) {
        Short s = (Short) valores.get(celda);
        return s.shortValue();
    } // asShort()

    /**
     * Devolver la celda de una tupla como tipo primitivo int.
     * 
     * @param celda, la celda de la <code>Tupla</code> accedida.
     * @return un celda específica de la tupla como un tipo primitivo
     * int.
     * 
     */
    public int asInt(int celda) {
        Integer in = (Integer) valores.get(celda);
        return in.intValue();
    } // asInt()

    /**
     * Devolver la celda de una tupla como tipo primitivo Long.
     * 
     * @param celda, la celda de la <code>Tupla</code> accedida.
     * @return un celda específica de la tupla como un tipo primitivo
     * long.
     * 
     */
    public long asLong(int celda){
        Long l = (Long) valores.get(celda);
        return l.longValue();
    } // asLong()

    /**
     * Devolver la celda de una tupla como tipo primitivo float.
     * 
     * @param celda, la celda de la <code>Tupla</code> accedida.
     * @return un celda específica de la tupla como un tipo primitivo
     * float.
     * 
     */
    public float asFloat(int celda){
        Float f = (Float) valores.get(celda);
        return f.floatValue();
    } // asFloat() 

    /**
     * Devolver la celda de una tupla como tipo primitivo double.
     * 
     * @param celda, la celda de la <code>Tupla</code> accedida.
     * @return un celda específica de la tupla como un tipo primitivo
     * double.
     * 
     */
    public double asDouble(int celda){
        Double doub = (Double) valores.get(celda);
        return doub.doubleValue();
    } // asDouble() 

    /**
     * Devolver la celda de una tupla como tipo primitivo String.
     * 
     * @param celda, la celda de la <code>Tupla</code> accedida.
     * @return un celda específica de la tupla como un tipo primitivo
     * String.
     * 
     */
    public String asString(int celda) {
        String str = (String) valores.get(celda);
        return str;
    } // asString()

    /**
     * Devolver la celda especificada de la tupla como generic
     * Comparable.
     * 
     * @param celda, la celda de la <code>Tupla</code> accedida.
     * @return la celda especifica de la tupla como generic Comparable.
     */
    public Comparable getValor(int celda) {
        return valores.get(celda);
    } // getValue()

    /**
     * probar la igualdad de objetos.
     *
     * @param o objeto a comparar con la tupla.
     * @return <code>true</code> si la tupla es igual a
     * <code>o</code>, <code>false</code> caso contrario.
     */
    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (!(o instanceof Tupla)) {
            return false;
        }
        Tupla t = (Tupla) o;
        if (size() != t.size()) {
            return false;
        }
        if (!getIdTupla().equals(t.getIdTupla())) {
            return false;
        }
        int i = 0;
        for (Comparable comp : valores) {
            if (!comp.equals(t.getValor(i))) {
                return false;
            }
            i++;
        }
        return true;
    }

    /**
     * Devolver el hash code de la tupla.
     *
     * @return un hash code de la tupla.
     */
    @Override
    public int hashCode() {
        int hash = 17;
        hash += 31 * hash + getIdTupla().hashCode();
        for (Comparable comp : valores) {
            hash += 31 * hash + comp.hashCode();
        }
        return hash;
    }

    /**
     * Representación Textual.
     */
    @Override
    public String toString() {
        return idTupla.toString() + " : " + valores.toString();
    } // toString()
}
