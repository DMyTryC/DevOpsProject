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
    private HashMap<String, ArrayList> data;

    public DataFrame(){
        this.data = new HashMap<String, ArrayList>();
    }

    public DataFrame(String[] labels, ArrayList<ArrayList>  elements){
        this.data = new HashMap<String, ArrayList>();
        ArrayList<String> element;
        for(int i=0;i< elements.size();i++) {
            this.data.put(labels[i], elements.get(i));
        }
    }
}
