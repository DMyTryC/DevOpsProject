/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devopsproject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author stephanie
 */
public class Main {
    
     public static void main(String [] args) throws IOException {
     
     /*DataFrame d= new DataFrame("/home/stephanie/Documents/Documents/NewFolder/DevOps/src/src/devopsproject/test.csv",",");
    // d.show();
    //d.head(3);
    d.tail(2);*/
    HashMap<String,Integer> hash = new HashMap<>();
    hash.put("b", 1);
    hash.put("a", 2);


    for (Map.Entry<String, Integer> entrySet : hash.entrySet()) {
	String key = entrySet.getKey();
	Integer value = entrySet.getValue();

	System.out.println(key + " "+ value);

    }
    //d.head("Age", 2);
     //d.last("Age", 2);
     //ArrayList toprint = d.data.get("Age");
     //System.out.println(toprint.get(0));
 }    
    
}
