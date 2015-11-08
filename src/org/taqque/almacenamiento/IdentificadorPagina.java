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
 * IdentificadorPagina: Identifica una página en disco.
 *
 * @author hernan
 */
public class IdentificadorPagina {
	
    /** Nombre del archivo al que pertenece la página. */
    private String nombreArchivo;
	
    /** numero de la página. */
    private int numero;

    
    /**
     * Crear un nuevo identificador.
     * 
     * @param nombreArchivo el archivo al que la página pertenece.
     * @param numero el numero de la página en el archivo.
     */
    public IdentificadorPagina(String nombreArchivo, int numero) {
        
        this.nombreArchivo = nombreArchivo;
        this.numero = numero;
    } // PageIdentifier()

    
    /**
     * Recupera el nombre de archivo al que pertenece la página.
     * 
     * @return nombre de archivo.
     */
    public String getNombreArchivo() {
        
        return nombreArchivo;
    }

    
    /**
     * Recupera el número de la página.
     * 
     * @return numero de página.
     */
    public int getNumero () {
        return numero;
    }
    
    /**
     * Retorna una representación textual del identificador de página.
     * 
     * @return representación textual del identificador.
     */
    @Override
    public String toString() {
        
        return "[pagina " + getNombreArchivo() + ":" + getNumero() + "]";
    }
} 
