/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miselania.tiposgenericos;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hernan
 */
public class EjemploMap {
    public static void main(String[] args)
    {
        Map<String, Integer> mapa = new HashMap<String, Integer>();
        
        mapa.put("Aernan", 1);
        mapa.put("Jun", 2);
        mapa.put("Pedro", 3);
        mapa.put("Carla", 4);
        
        
        System.out.println("Mapa: "+mapa);
        
        System.out.println("Get: "+mapa.get("Jun"));
    }
    
}
