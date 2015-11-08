/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miselania.serializacion;

import java.io.*;
/**
 *
 * @author hernan
 */
public class Serializacion {

    public static void main(String[] args) {
        // Crear objetos de clase Agenda
        Agenda a1 = new Agenda("Ana1", "Martínez1", "Fernández1");
        Agenda a2 = new Agenda("Ernesto", "García", "Pérez");
        // iniciar el proceso de serialización
        // colocar los objetos a1 y a2 en disco
        try {
            //Creamos el archivo
            FileOutputStream fs = new FileOutputStream("agendas.ser");
            //Esta clase tiene el método writeObject() que necesitamos
            ObjectOutputStream os = new ObjectOutputStream(fs);

            //El método writeObject() serializa el objeto 
            //y lo escribe en el archivo         
            os.writeObject(a1);
            os.writeObject(a2);
            //Hay que cerrar siempre el archivo
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(
                "Los Objetos se han serializado correctamente.");
    }
}