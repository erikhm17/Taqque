package org.taqque.almacenamiento;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Diccionario de Datos - Meta datos
 * @author hernan
 */
public class Catalogo { // almacenar las tablas que tiene la BD
                        //esta clase almacena la informacion de la base de datos con sus atributos y nombre de la tabla
                        // tmb almacena el nombre del archivo 
                        
    /** Directorio de la BD. */
    private String directorio;
    /** Nombre de archivo del catalogo. */
    private String archivoCatalogo;
    /** Entradas del catalogo, mapean nombres de tabla a entradas. */
    private java.util.Map<String, EntradaCatalogo> entradas;// cada objeto entrada este representando a una determinada tabla
    /** Numero de tablas actualmente almacenadas en el catalogo. */
    private int numeroDeTablas;
    public Catalogo(String directorio, String archivoCatalogo) {
        this.directorio = directorio;
        this.archivoCatalogo = archivoCatalogo;
        entradas = new java.util.Hashtable<String, EntradaCatalogo>(); 
    }
    //Metodo si existe la tabla se coloca dentro de la tabla hash en caso contrario 
    public void crearNuevaEntrada(EntradaCatalogo entrada) throws IllegalArgumentException {
        if (!existeTabla(entrada.getNombreTabla())) {
            entradas.put(entrada.getNombreTabla(), entrada);
            numeroDeTablas++;
        } else {
            this.entradas.remove(entrada.getNombreTabla());
            entradas.put(entrada.getNombreTabla(), entrada);
            
//            throw new IllegalArgumentException("Tabla: "
//                    + entrada.getNombreTabla()
//                    + " ya existe.");
        }
    }
    protected boolean existeTabla(String nombreTabla) {
        return (entradas.get(nombreTabla) != null);
    }
    @Override
    public String toString() {
        return "Catalogo: " + numeroDeTablas + " tablas.  Entradas: \n"
                + "\t" + entradas.toString();
    }
    // Leer un archivo de catalogo del Disco
    public void leerCatalogo() throws Exception { // leer del archivo de disco y poder recuperarlo usando la serializacion
        try {
            // Objeto que representa al archivo
            String path = directorio+System.getProperty("file.separator")+archivoCatalogo;
            FileInputStream fstream = new FileInputStream(path);
            // tarea de lectura utilizamos este objeto
            ObjectInputStream istream = new ObjectInputStream(fstream);

            // leer en del número de tablas
            numeroDeTablas = istream.readInt();
            // leer en las entradas del catálogo

            entradas = (Map<String, EntradaCatalogo>) istream.readObject();
            

            istream.close();
        } catch (Exception ex) {
            System.out.println("Problemas al abrir el archivo: " + ex.getMessage());
        }
    }
    // Metodo para almacenar el catalogo en disco
    // si queremos almacenar objeos de manera permanente en disco es recomendado usar la serializacion
    public void escribirCatalogo() throws Exception {

        try {
            // Crear un nuevo archivo en disco - Interface acceso de archivo
            String path = directorio+System.getProperty("file.separator")+archivoCatalogo;
            FileOutputStream fstream = new FileOutputStream(path);
            // realizar operaciones de escritura en el archivo
            ObjectOutputStream ostream = new ObjectOutputStream(fstream);

            // write out the number of tables
            ostream.writeInt(numeroDeTablas);
            // write out the entries of the catalog
            ostream.writeObject(entradas);
            // ejecutar el almacenamiento
            ostream.flush();
            ostream.close(); // si no realiza cerrar no almacena 
        } catch (IOException ioe) {
            System.out.println("I/O Exception mientras se almacenaba el archvio del catalogo "
                    + archivoCatalogo + ioe.getMessage());
        }
    }
    
    public Map<String, EntradaCatalogo> getEntradas()
    {
        return entradas;
    }
    
    public void eliminarTabla(String nombreTabla)
        throws NoSuchElementException {
        
        if (! existeTabla(nombreTabla))
            throw new NoSuchElementException("Tabla " + nombreTabla + " no esta "
                                             + "en el catalogo de la BD.");
        entradas.remove(nombreTabla);
        numeroDeTablas--;
    } // deleteTable()

    public String getDirectorio() {
        return directorio;
    }

    public void setDirectorio(String directorio) {
        this.directorio = directorio;
    }
    
    public String getPath()
    {
        return directorio+System.getProperty("file.separator")+archivoCatalogo;
    }
    
    

    public static void main(String args[]) {
        try {
            String directorio = "D:/prueba";
            
            // crear tres nuevas entradas de tabla
            Atributo at = new Atributo("dni",Integer.class);
            ArrayList<Atributo> lista = new ArrayList<Atributo>();
            lista.add(at);
            
            EntradaCatalogo ce1 = new EntradaCatalogo(
                    new Tabla("alumno",lista ),directorio);
            EntradaCatalogo ce2 =
                    new EntradaCatalogo(new Tabla("docente",
                    new ArrayList<Atributo>()),directorio);
            EntradaCatalogo ce3 =
                    new EntradaCatalogo(new Tabla("curso",
                    new ArrayList<Atributo>()),directorio);

            // crear el catalogo

            Catalogo catalogo = new Catalogo(directorio,"catalogoprueba.dat");
            // añdir informacion a catalogo
            
            catalogo.crearNuevaEntrada(ce1);
            catalogo.crearNuevaEntrada(ce2);
            catalogo.crearNuevaEntrada(ce3);

            // imprimir en patalla el catalogo
            System.out.println(catalogo);
            Thread.sleep(1000);

            // escribir el catalogo en archivo
            System.out.println("Creando catalogo catalog.");
            catalogo.escribirCatalogo();
            Thread.sleep(1000);

            // Leer el catalogo almacenado recientemente
            System.out.println("Leyendo catalogo.");
            catalogo.leerCatalogo();

             // Imprimir nuevamente el catalogo
            System.out.println(catalogo);
            Thread.sleep(1000);
            // Eliminar una Tabla
            System.out.println("Eliminado una tabla.");
            catalogo.eliminarTabla("docente");
             // Imprimir nuevamente el catalogo
            System.err.println(catalogo);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println("Exception " + e.getMessage());
            e.printStackTrace(System.err);
        }
    } // main()
}
