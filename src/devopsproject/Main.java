/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devopsproject;

import java.io.IOException;

/**
 *
 * @author stephanie
 */
public class Main {
    
     public static void main(String [] args) throws IOException {
     
     DataFrame d= new DataFrame("Users/r_benounnas/Desktop/test.csv",",");
    // d.show();
    //d.head(3);
    d.tail(2);
    //d.head("Age", 2);
     //d.last("Age", 2);
     //ArrayList toprint = d.data.get("Age");
     System.out.println(d.data.size());
 }    
    
}
