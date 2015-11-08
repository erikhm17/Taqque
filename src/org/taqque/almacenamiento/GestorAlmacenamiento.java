package org.taqque.almacenamiento;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.taqque.memoriaintermedia.GestorMemoriaIntermedia;

/**
 * GestorAlmacenamiento: Provee un manejo a bajo nivel de la 
 * entrada y salida de paginas en un nivel inferior como servicio para el resto
 * del sistema.
 *
 * @author hernan
 */
public class GestorAlmacenamiento {

    /** Catalogo del gestor de almacenamiento. */
    private Catalogo catalogo;
    /** El gestor de memoria intermedio del gestor de almacenamiento. */
    private GestorMemoriaIntermedia buffer;

    /**
     * Inicializa un nuevo gestor de almacenamiento, dado un catalogo y 
     * un buffer de memoria inteermedia.
     * 
     */
    public GestorAlmacenamiento(Catalogo catalogo, GestorMemoriaIntermedia buffer) {
        this.catalogo = catalogo;
        this.buffer = buffer;
    } // GestorAlmacenamiento()

    /**
     * Registrar una pagina considerando la existencia de memoria intermedia.
     *
     */
    public void registrarPagina(Pagina pagina) {
        escribirPagina(pagina);
    }

    /**
     * Escribir una pagina en disco.
     * 
     */
    public synchronized void escribirPagina(Pagina pagina) {
        try {
            // Colocar la página en la memoria intermedia y verificar si una 
            // pagina será desechada -- si es así, extraer la página desechada            
            Pagina paginaDesechada = buffer.ponerPaginaEnBuffer(pagina, false);
            
            if (paginaDesechada != null) {
                String nombreArchivo = 
                        paginaDesechada.getIdentificadorPagina().getNombreArchivo();
                RandomAccessFile archivoDB = new RandomAccessFile(nombreArchivo,
                        "rw");
                GestorIOPagina.escribirPagina(archivoDB, paginaDesechada);
                archivoDB.close();
            }
            else
            {
                String nombreArchivo = 
                        pagina.getIdentificadorPagina().getNombreArchivo();
                RandomAccessFile archivoDB = new RandomAccessFile(nombreArchivo,
                        "rw");
                GestorIOPagina.escribirPagina(archivoDB, pagina);
                archivoDB.close();
            }
        } catch (IOException e) {
            System.out.println("Error escribiendo página a disco. " + e);
        }
    }

    /**
     * Leer una página de la bd conociendo el identificador de la página.
     * 
     * @param idPagina el identificador de la página a leer.
     * @return la pagina leida.
     */
    public synchronized Pagina leerPagina(Relacion relacion,
            IdentificadorPagina idPagina) {
        try {
            // Si la página esta en el buffer, entonces leer del mismo
            if (buffer.estaPaginaEnBuffer(idPagina)) {
                return buffer.recuperarPaginaDelBuffer(idPagina);
            } // caso contrario leer del archivo y colocarlo en la memoria intermedia.
            else {
                String nombreArchivo = idPagina.getNombreArchivo();
                RandomAccessFile archivoBD = 
                        new RandomAccessFile(nombreArchivo, "rw");
                Pagina pagina = GestorIOPagina.leerPagina(relacion, idPagina, archivoBD);
                archivoBD.close();
                Pagina paginaDesechada = buffer.ponerPaginaEnBuffer(pagina, true);
                if (paginaDesechada != null) {
                    nombreArchivo = paginaDesechada.getIdentificadorPagina().getNombreArchivo();
                    archivoBD = new RandomAccessFile(nombreArchivo, "rw");
                    GestorIOPagina.escribirPagina(archivoBD, paginaDesechada);
                    archivoBD.close();
                }
                return pagina;
            }
        } catch (IOException e) {
            System.out.println("Error leyendo la página del disco. "+ e);
            return null;
        }
    }
    
    /**
     * Crear unn nuevo archivo en disco.
     * 
     */
    public void crearArchivo(String nombreArchivo){
        try {
            File file = new File(nombreArchivo);
            if (!file.createNewFile()) {
                file.delete();
                file.createNewFile();
            }
        } catch (Exception e) {
            System.err.println("No se puede crear el archivo "
                    + nombreArchivo + ". "+ e);
        }
    }
} 
