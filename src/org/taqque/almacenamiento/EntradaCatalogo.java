package org.taqque.almacenamiento;
/**
 * EntradaCatalogo: Almacena toda la informacion referente a una 
 * tabla en el catalogo.
 * 
 * @author hernan
 */
public class EntradaCatalogo implements java.io.Serializable {
    /** Directory of the server. */
    public static String directorio;
    /** Tabla para este entrada a catalogo. */
    private Tabla tabla;
    /** El nombre de archvio para la tabla. */
    private String nombreArchivo;
    public EntradaCatalogo(Tabla tabla, String directorio) {
        this.tabla = tabla;
        crearNombreArchivo(directorio);
    } 
    public String getNombreTabla() {
        return tabla.getNombre();
    } 
    public Tabla getTabla() {
        return tabla;
    } 
    public String getNombreArchivo() {
        return nombreArchivo;
    } 
    @Override
    public String toString() {
        return "Tabla: " + getNombreTabla() + ", nombre de Archivo: "
            + nombreArchivo + ", definición: " + tabla+"\n";
    }
    protected void crearNombreArchivo(String directorio) {
        String nombreTabla = tabla.getNombre();
        nombreArchivo = new String(directorio
                              + System.getProperty("file.separator")
                              + nombreTabla + "_" + nombreTabla.hashCode());
    } 
}
