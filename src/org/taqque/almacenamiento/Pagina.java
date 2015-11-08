/*
 * Creado el 30 de Noviembre, 2011 por Hernán Nina Hanco
 *
 * Modificado el 25 de Junio, 2012 por Hernán Nina Hanco
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

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Pagina: Representación básica de una página de disco.
 *
 * @author hernan
 */
public class Pagina implements Iterable<Tupla> {

    /** Las tuplas de la página. */
    private List<Tupla> tuplas;
    /** El ID de la página. */
    private IdentificadorPagina idPagina;
    /** El esquema de la relación al que pertenece la página. */
    private Relacion relacion;
    /** El espacio libre en la página. */
    private int espacioLibre;

    /**
     * Crear una nueva página dado el esquema de la relación y el IdPagina.
     * 
     * @param relacion la relación al que pertenece la página.
     * @param idPagina el ID de la página.
     */
    public Pagina(Relacion relacion, IdentificadorPagina idPagina) {
        this.relacion = relacion;
        this.idPagina = idPagina;
        this.tuplas = new ArrayList<Tupla>();
        // tamaño de página 4k
        espacioLibre = 4096 - Convert.INT_SIZE;
    }

    /**
     * Devolver la relación a la que pertenece la página.
     * 
     * @return relación a la que pertenece la página.
     */
    public Relacion getRelacion() {
        return relacion;
    }

    /**
     * Verificar si la página tiene espacio para mas tuplas.
     *
     * @return <pre>true</pre> si hay espacio para mas tuplas
     * en la página, <pre>false</pre> en caso contrario.
     */
    public boolean hayEspacio(Tupla t) {
        return espacioLibre >= GestorIOTupla.byteSize(getRelacion(), t);
    }

    /**
     * Devolver el número de tuplas almacendas en la página.
     *
     * @return numero de tuplas almacenadas en la página.
     */
    public int getNumeroDeTuplas() {
        return tuplas.size();
    }

    /**
     * Almacenar una nueva tupla en la página.
     * 
     * @param tupla la nueva tupla.
     * @throws ArrayIndexOutOfBoundsException lanzar si el límite del tamaño de la
     * página no es suficiente para almacenar la tupla.
     */
    public void adicionarTupla(Tupla tupla)
            throws ArrayIndexOutOfBoundsException {

        if (hayEspacio(tupla)) {
            tuplas.add(tupla);
            espacioLibre -= GestorIOTupla.byteSize(getRelacion(), tupla);
        } else {
            throw new ArrayIndexOutOfBoundsException("no hay espacio en la página.");
        }
    }

    /**
     * Cambiar una tupla.
     * 
     * @param indice el indice de la tupla a ser cambiada.
     * @param tupla la nueva tupla.
     * @throws ArrayIndexOutOfBoundsException lanzar si el límite del tamaño de la
     * página no es suficiente para almacenar la nueva tupla.
     */
    public void setTupla(int index, Tupla tupla)
            throws ArrayIndexOutOfBoundsException {

        if (!puedeCambiar(index, tupla)) {
            throw new ArrayIndexOutOfBoundsException("no hay espacio en la página.");
        }
        tuplas.set(index, tupla);
    }

    /**
     * Verificar si un especifico indice de una tupla puede ser reemplazado 
     * por una nueva tupla.
     *
     * @param index indice de la tupla a ser reemplado.
     * @param nt la nueva tupla.
     */
    public boolean puedeCambiar(int index, Tupla nt) {

        return (espacioLibre
                + GestorIOTupla.byteSize(getRelacion(), tuplas.get(index))
                - GestorIOTupla.byteSize(getRelacion(), nt)) >= 0;
    }

    /**
     * Intercambiar dos tuplas en base a sus indices.
     *
     * @param x el indice de la primera tupla.
     * @param y el índice de la segunda tupla.
     */
    public void intercambiar(int x, int y) {
        Tupla t = tuplas.get(x);
        tuplas.set(x, tuplas.get(y));
        tuplas.set(y, t);
    }

    /**
     * Recuperar una determinada tupla de la página.
     * 
     * @param index el indice de la tupla a ser recuperada.
     * @return una tupla en la posición de index.
     */
    public Tupla recuperarTupla(int index) {

        return tuplas.get(index);
    }

    /**
     * Recuperar el ID de la pagina
     * 
     * @return ID de la página
     */
    public IdentificadorPagina getIdentificadorPagina() {
        return idPagina;
    }

    /**
     * Devolver un iterator de tuplas de la página.
     *
     * @return an iterator .
     */
    @Override
    public Iterator<Tupla> iterator() {
        return new IteradorDePagina();
    }

    /**
     * Clase interna que implementa un iterator de tupla
     * DIGA 18 de Junio - 022600
     */
    private class IteradorDePagina implements Iterator<Tupla> {

        /** El actual indice del iterator. */
        private int indiceActual;

        /**
         * Constructor de un nuevo iterator para el contenido de la página.
         */
        public IteradorDePagina() {
            indiceActual = 0;
        }

        /**
         * Verificar si hay mas tuplas en la página.
         *
         * @return <code>true</code> si hay mas tuplas
         * en la página, <code>false</code> en caso contrario.
         */
        @Override
        public boolean hasNext() {
            return indiceActual < tuplas.size();
        }

        /**
         * Devuelve la siguiente tupla del iterator.
         *
         * @return la siguiente tupla del iterator.
         */
        @Override
        public Tupla next() {
            return tuplas.get(indiceActual++);
        }

        /**
         * Elimina la última tupla devuelta por el iterador.
         */
        @Override
        public void remove() {
            int size = GestorIOTupla.byteSize(getRelacion(),
                    tuplas.get(indiceActual));
            espacioLibre += size;
            tuplas.remove(indiceActual);
        }
    }

    /**
     * Devolver un representación textual de la página.
     * 
     * @return representación textual de la página.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("pagina: ").append(getIdentificadorPagina()).append(", tuplas: {\n");
        int tid = 0;
        for (Tupla it : this) {
            sb.append("\t").append(tid++).append(": ").append(it.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
