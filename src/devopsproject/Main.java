/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.devopsproject;

import java.io.IOException;

/**
 *
 * @author stephanie
 */
public class Main {
    
     public static void main(String [] args) throws IOException {
     
     DataFrame d= new DataFrame("/home/stephanie/Documents/Documents/NewFolder/DevOps/src/src/devopsproject/test.csv",",");
    // d.show();
    //d.head(3);
    d.lost(2);
    //d.head("Age", 2);
     //d.last("Age", 2);
     //ArrayList toprint = d.data.get("Age");
     //System.out.println(toprint.get(0));
 }    
    
}
