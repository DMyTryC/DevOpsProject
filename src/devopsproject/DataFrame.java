/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devopsproject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author bleuzeb
 */
public class DataFrame {
    private HashMap<String, ArrayList<E>> data;

    public DataFrame(String[] labels, ArrayList<E>[]){
        for (Iterator<String> i = labels.iterator(); i.hasNext();) {
            String label = i.next();
            this.data.put(label,null)
        }
    }
}
