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

import org.taqque.almacenamiento.Pagina;

/**
 * ContenedorPagina: Contenedor de una página en el buffer de la memoria intermedia
 * con seguimiento de versión de página o modificación de la página en buffer. 
 * 
 * @author hernan
 */

public class ContenedorPagina {

    public Pagina pagina;
    public boolean modificadorEnBuffer;

    public ContenedorPagina(Pagina pagina) {
        this.pagina = pagina;
        modificadorEnBuffer = false;
    } // ContenedorPagina
} // ContenedorPagina