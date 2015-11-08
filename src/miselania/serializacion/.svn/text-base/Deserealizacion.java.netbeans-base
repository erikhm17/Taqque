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
public class Deserealizacion {

    public static void main(String[] args) {
        Agenda a1 = null;
        Agenda a2 = null;
        try {
            // abrir o Recuperar un archivo de disco
            FileInputStream fis = 
                    new FileInputStream("agenda1.ser");
            // Stream para recuperar o reconstruir objetos
            ObjectInputStream ois = 
                    new ObjectInputStream(fis);
            //El método readObject() recupera el objeto
            a1 = (Agenda) ois.readObject();
            a2 = (Agenda) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(a1);
        System.out.println(a2);

    }
}