package org.taqque.almacenamiento;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.RandomAccessFile;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.taqque.memoriaintermedia.GestorMemoriaIntermedia;

/**
 * GestorIORelacion: clase que representa de forma básica las funciones 
 * de I/O en una relación.
 *
 * @author hernan
 */
public class GestorIORelacion {

    /** El esquema de relación a ser gestionada. */
    private Relacion relacion;
    /** El gestor de almacenamiento para este gestor. */
    private GestorAlmacenamiento ga;
    /** El nombre de archivo de la relación. */
    private String nombreArchivo;

    /**
     * Constructor de un nuevo gestor I/O de relación.
     * 
     */
    public GestorIORelacion(GestorAlmacenamiento ga,
            Relacion relacion,
            String nombreArchivo) {
        this.ga = ga;
        this.relacion = relacion;
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * Insertar una nueva tupla en la relación.
     * 
     * @param lista de valores.
     */
    public void insertarTupla(List<Comparable> valores) {
        try {
            // leer la última página del archivo
            int numPagina = 
                    recuperarNumeroDePaginas(getNombreArchivo());
            numPagina = (numPagina == 0) ? 0 : numPagina - 1;
            IdentificadorPagina idPagina =
                    new IdentificadorPagina(getNombreArchivo(), numPagina);
            Pagina pagina = ga.leerPagina(relacion, idPagina);
            int num = 0;
            if (pagina.getNumeroDeTuplas() != 0) {
                Tupla t = pagina.recuperarTupla(pagina.getNumeroDeTuplas() - 1);
                num = t.getIdTupla().getNumero() + 1;
            }
            Tupla nuevaTupla = new Tupla(
                    new IdentificadorTupla(getNombreArchivo(), num), valores);
            if (!pagina.hayEspacio(nuevaTupla)) {
                pagina = new Pagina(relacion, 
                        new IdentificadorPagina(getNombreArchivo(),
                        numPagina + 1));
                cambiarNumeroDePaginas(getNombreArchivo(), numPagina + 2);
            }
            pagina.adicionarTupla(nuevaTupla);

            ga.escribirPagina(pagina);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.err.println("Error de I/O mientras se insertaba la tupla "
                    + "al archivo: " + getNombreArchivo()
                    + " (" + e.getMessage() + ")");
        }
    }

    /**
     * Recuperar el numero de paginas en el archivo de la relación.
     * 
     */
    public int recuperarNumeroDePaginas(String nombreArchivo)
            throws IOException, FileNotFoundException {

        RandomAccessFile archivoDB = new RandomAccessFile(getNombreArchivo(), "r");
        int paginas = (int) ((archivoDB.length() / 4096) + 0.5);
        archivoDB.close();
        return paginas;
    }

    public void cambiarNumeroDePaginas(String nombreArchivo, int np)
            throws IOException, FileNotFoundException {

        RandomAccessFile archivoBD = new RandomAccessFile(getNombreArchivo(), "rw");
        int paginas = (int) ((archivoBD.length() / 4096) + 0.5);
        if (paginas < np) {
            archivoBD.setLength(np * 4096);
        }
        archivoBD.close();
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * Abrir un iterador de pagina sobre la relación.
     *
     */
    public Iterable<Pagina> paginas() throws IOException {
        return new PaginaIterator();
    }

    /**
     * Abrir un iterador de tuplas sobre la relación.
     *
     */
    public Iterable<Tupla> tuplas() throws IOException {
        return new TuplaIterator();
    }

    /**
     * Un iterador basico para paginas de la relación.
     */
    class PaginaIterator implements Iterable<Pagina> {

        /** La pagina actual en la iteración. */
        private Pagina paginaActual;
        /** Numero de páginas en la relación. */
        private int numPaginas;
        /** El desplazamiento de la actual pagina. */
        private int desplazamientoPagina;

        /**
         * Construir un nuevo iterador de pagina.
         */
        public PaginaIterator() throws IOException {

            desplazamientoPagina = 0;
            numPaginas = recuperarNumeroDePaginas(getNombreArchivo());
        }

        /**
         * devolver un iterador de paginas.
         */
        @Override
        public Iterator<Pagina> iterator() {
            return new Iterator<Pagina>() {

                @Override
                public boolean hasNext() {
                    return desplazamientoPagina < numPaginas;
                }

                @Override
                public Pagina next() throws NoSuchElementException {
                    try {
                        paginaActual =
                                ga.leerPagina(relacion,
                                new IdentificadorPagina(getNombreArchivo(),
                                desplazamientoPagina++));
                        return paginaActual;
                    } catch (Exception sme) {
                        throw new NoSuchElementException("No se puede leer la "
                                + "paina por el iterador." + sme.getMessage());

                    }
                } 

                @Override
                public void remove() throws UnsupportedOperationException {
                    throw new UnsupportedOperationException("no se puede eliminar "
                            + "paginas en un iterador");
                }
            }; 
        } 
    } // Fin de clase

    /**
     * UN iterador básico para tuplas de la relación.
     */
    class TuplaIterator implements Iterable<Tupla> {

        /** La página a iterar. */
        private Iterator<Pagina> paginas;
        /** un simple iterador de tuplas. */
        private Iterator<Tupla> tuplas;
        /** Registrar la pista si hay mas tuplas que devolver. */
        private boolean masElementos;

        /**
         * Construir el iterador de tuplas.
         */
        public TuplaIterator() throws IOException {

            paginas = paginas().iterator();
            masElementos = paginas.hasNext();
            tuplas = null;
        } 

        /**
         * Verificar si hay mas tuplas que iterar
         */
        @Override
        public Iterator<Tupla> iterator() {
            return new Iterator<Tupla>() {

                public boolean hasNext() {
                    return masElementos;
                } // hasNext()

                public Tupla next() throws NoSuchElementException {
                    if (tuplas == null && masElementos) {
                        tuplas = paginas.next().iterator();
                    }

                    Tupla tupla = tuplas.next();
                    if (tuplas.hasNext()) {
                        masElementos = true;
                    } else if (paginas.hasNext()) {
                        tuplas = paginas.next().iterator();
                        masElementos = true;
                    } else {
                        masElementos = false;
                    }
                    return tupla;
                } 

                @Override
                public void remove() throws UnsupportedOperationException {
                    throw new UnsupportedOperationException("no se puede eliminar "
                            + "tuplas al "
                            + "iterar.");
                }
            }; 
        } 
    } // Fin de clase

    /**
     * test de las clases.
     */
    public static void main(String[] args) {
        try {
            GestorMemoriaIntermedia gmi = new GestorMemoriaIntermedia(2);
            GestorAlmacenamiento ga = new GestorAlmacenamiento(null, gmi);

            List<Atributo> atributos = new ArrayList<Atributo>();
            atributos.add(new Atributo("integer", Integer.class));
            atributos.add(new Atributo("string", String.class));
            
            Relacion relacion = new Relacion(atributos);
            Tabla tabla = new Tabla("cliente",atributos);
            EntradaCatalogo entrada = new EntradaCatalogo(tabla,"d:/prueba");
            
            
            //String nombreArchivo = "d:/prueba/relacionprueba.db";
            ga.crearArchivo(entrada.getNombreArchivo());

            GestorIORelacion gestor =
                    new GestorIORelacion(ga, tabla, entrada.getNombreArchivo());

            for (int i = 0; i < 2; i++) {
                List<Comparable> v = new ArrayList<Comparable>();
                v.add(new Integer(i));
                v.add(new String("12345678901234567890123456789012345678901234567890"));
                // System.out.println("insertando: " + v);
                gestor.insertarTupla(v);
            }

            System.out.println("Tuplas insertadas correctamente.");
            System.out.println("Abriendo un cursos de tuplas...");



            for (Tupla tuple : gestor.tuplas()) {
                System.out.println("read tupla: " + tuple);
            }

            for (Pagina pagina : gestor.paginas()) {
                System.out.println("read page: " + pagina);
            }

            System.out.println("buffer: " + gmi);

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    } 
} 
