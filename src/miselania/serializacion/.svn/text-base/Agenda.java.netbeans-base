/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miselania.serializacion;

/**
 *
 * @author hernan
 */
import java.io.*;
class Agenda implements Serializable {

    private String nombre;
    private String p_Apellido;
    private String s_Apellido;
    /*getters y setters*/

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getP_Apellido() {
        return p_Apellido;
    }

    public void setP_Apellido(String p_Apellido) {
        this.p_Apellido = p_Apellido;
    }

    public String getS_Apellido() {
        return s_Apellido;
    }

    public void setS_Apellido(String s_Apellido) {
        this.s_Apellido = s_Apellido;
    }

    
    public Agenda(String nombre, String p_Apellido, 
            String s_Apellido) {
        super();
        this.nombre = nombre;
        this.p_Apellido = p_Apellido;
        this.s_Apellido = s_Apellido;
    }

    @Override
    public String toString() {
        return (getNombre() + " " + 
                getP_Apellido() + " " + getS_Apellido());
    }
}