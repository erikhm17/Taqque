/*
 * Creado el 28 de mayo, 2012 por Hernán Nina Hanco
 *
 * Este trabajo es parte del proyecto Taqque, que corresponde a la 
 * implementación de un Sistema de Gestión de Base de Datos. Cualquier
 * cambio es necesario declararlo en este apartado.
 * 
 * Universidad Nacional de San Antonio Abad del Cusco
 * Carrera Profesional de Ingeniería Informática y de Sistemas
 * Asignatura: Sistemas de Bases de Datos II
 */
package org.taqque.memoriaintermedia;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;
import org.taqque.almacenamiento.IdentificadorPagina;
import org.taqque.almacenamiento.Pagina;
import org.taqque.almacenamiento.Relacion;
/**
 * Representa una abstracción de un 
 * Gestor de la Memoria Intermedia
 * 
 * @author hernan
 */
public class GestorMemoriaIntermedia {

    static final Logger logger = 
            Logger.getLogger("Gestion de Buffer");
    /** Representa a los bloques de la 
     * memoria intermedia o buffer. */
    private ContenedorPagina[] buffer;
    /** Numero de paginas en total en el buffer. */
    private int numeroPaginas;
    /** Posición en el buffer de la ultima pagina colocada. */
    private int posicionUltimaPagina = -1;
    /** cola para el control de sustitución basada en LRU. */
    private Queue colaLRU;

    /**
     * Crea un nuevo Gestor de memoria intermedia.
     * 
     * @param numeroPaginas cantidad de paginas en total en el buffer.
     */
    public GestorMemoriaIntermedia(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
        // Crear el buffer con paginas vacias
        buffer = new ContenedorPagina[numeroPaginas];
        // Crear una cola FIFO
        colaLRU = new LinkedList<Integer>();
    }

    /**
     * Devuelve una pagina conociendo el numero de la página.
     * 
     * @param numeroPagina el id de la pagina que queremos devolver
     * @return la página del id de pagina proporcionado.
     */
    public Pagina recuperarPaginaDelBuffer(IdentificadorPagina idPagina) {
        // Verificar si la pagina esta en el buffer
        int posicion = posicionDePaginaEnBuffer(idPagina.getNumero());
        if (posicion >= 0) // la pagina esta en el buffer
        {
            // Marcar Pagina o bloque como recientemente referenciado
            colaLRU.remove(buffer[posicion].pagina.getIdentificadorPagina().getNumero());
            colaLRU.add(buffer[posicion].pagina.getIdentificadorPagina().getNumero());
            // devolver la pagina del buffer al solicitante
            return buffer[posicion].pagina;
        }
        return null;
    }

    /**
     * Poner una pagina en el buffer de la memoria intermedia.
     * 
     * @param pagina la pagina a ser insertado en el buffer.
     * @return la pagina que sera descartada del buffer si es necesario.
     */
    public Pagina ponerPaginaEnBuffer(Pagina pagina, boolean modificadoEnBuffer) {
        // Si la pagina esta en el buffer
        if (estaPaginaEnBuffer(pagina.getIdentificadorPagina())) {
            // no descartar ninguna pagina o bloque 
            int index = this.posicionDePaginaEnBuffer(
                    pagina.getIdentificadorPagina().getNumero());
            buffer[index].pagina = pagina;
            buffer[index].modificadorEnBuffer = modificadoEnBuffer;
            colaLRU.remove(pagina.getIdentificadorPagina().getNumero());
            colaLRU.add(pagina.getIdentificadorPagina().getNumero());
            
            return null;
        } // Si la pagina no esta en el buffer, pero con espacio en el buffer
        else if (!bufferLLeno()) {
            // Asignar la nueva pagina al bloque o pagina libre del buffer 
            posicionUltimaPagina++;
            buffer[posicionUltimaPagina] = new ContenedorPagina(pagina);
            buffer[posicionUltimaPagina].modificadorEnBuffer = modificadoEnBuffer;
            // Actualizar la LRU
            colaLRU.add(pagina.getIdentificadorPagina().getNumero());
            return null;
        } // Si la pagina no esta en el buffer, pero sin espacio en el buffer
        else {
            // recuperar la pagina a descartar
            // Identificar el ID Pagina a descartar de la cola LRU
            int numeroPaginaDescartada = (Integer) 
                    colaLRU.remove();
            // conociendo el ID de pagina ubicar la posición de 
            // la pagina en el buffer.
            int posicion = posicionDePaginaEnBuffer
                    (numeroPaginaDescartada);
            // Extraer la pagina a descartar del buffer
            Pagina paginaDescartada = buffer[posicion].pagina;
            buffer[posicion].modificadorEnBuffer = modificadoEnBuffer;
            // Actualizar la nueva pagina en la posición de la 
            // pagina descartada
            buffer[posicion].pagina = pagina;
            // hacer que el ID de la Nueva pagina sea el 
            // mas recientemente referenciado.
            colaLRU.add(pagina.getIdentificadorPagina().getNumero());
            
            return paginaDescartada;
        }

    }

    private int posicionDePaginaEnBuffer(int numeroPagina) {
        int posicion = -1;
        for (int i = 0; i < buffer.length; i++) {
            if ((buffer[i] != null)   && 
             (numeroPagina == buffer[i].pagina.getIdentificadorPagina().getNumero())) {
                posicion = i;           break;
            }
        } return posicion;
    }

    public boolean estaPaginaEnBuffer(IdentificadorPagina idPagina) {
        return (posicionDePaginaEnBuffer
                (idPagina.getNumero()) != -1);
    }

    private boolean bufferLLeno() {
        return numeroPaginas - 1 == posicionUltimaPagina;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Buffer: ").append("Paginas: {\n");
        int tid = 0;
        for (ContenedorPagina cpg : buffer) {
            if (cpg!=null)
                sb.append("\t").append(tid++).append(": ").append(cpg.pagina.toString()).append("\n");
        }
        sb.append("}").append("\n");
        sb.append("LRU: {").append(colaLRU).append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        // Crear un gestor de memoria
        GestorMemoriaIntermedia gestorBuffer = 
                new GestorMemoriaIntermedia(2);
        // Paginas del Disco
        System.out.println("Creando Paginas...");
        Pagina[] paginas = new Pagina[2];
        for (int i = 0; i < paginas.length; i++) {
            Pagina pagina = new Pagina(new Relacion(),new IdentificadorPagina("relacion",i));
            paginas[i] = pagina;
        }
        System.out.println("Contenido del Gestor: ");
        System.out.println(gestorBuffer);
       
        gestorBuffer.ponerPaginaEnBuffer(new Pagina(new Relacion(),
                new IdentificadorPagina("relacion",9)),false);

        gestorBuffer.ponerPaginaEnBuffer(new Pagina(new Relacion(),
                new IdentificadorPagina("relacion",5)),false);
        
       
        System.out.println("Contenido del Gestor despues de poner bloques: ");
        System.out.println(gestorBuffer);
        
        gestorBuffer.ponerPaginaEnBuffer(new Pagina(new Relacion(),
                new IdentificadorPagina("relacion",3)),false);
        
        System.out.println("Contenido del Gestor despues de poner bloques 3: ");
        System.out.println(gestorBuffer);

        gestorBuffer.ponerPaginaEnBuffer(new Pagina(new Relacion(),
                new IdentificadorPagina("relacion",12)),false);
        
        System.out.println("Contenido del Gestor despues de poner bloques 12: ");
        System.out.println(gestorBuffer); 
        
        System.out.println("despues de recuperar 3: ");
        Pagina pagina2 = gestorBuffer.recuperarPaginaDelBuffer(new IdentificadorPagina("relacion",3));
        System.out.println(gestorBuffer);
        System.out.println(pagina2);

        System.out.println("despues de recuperar 12: ");
        Pagina pagina12 = gestorBuffer.recuperarPaginaDelBuffer(new IdentificadorPagina("relacion",12));
        System.out.println(gestorBuffer);
        System.out.println(pagina12);
        
        gestorBuffer.ponerPaginaEnBuffer(new Pagina(new Relacion(),
                new IdentificadorPagina("relacion",15)),false);
        
        System.out.println("Contenido del Gestor despues de poner bloques 15: ");
        System.out.println(gestorBuffer); 
    }
}
