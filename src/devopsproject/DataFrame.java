/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devopsproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author bleuzeb
 */
public class DataFrame {
    
    HashMap<String, ArrayList> estructure;
    
    
    public DataFrame(){
        
    }
    
    
    public DataFrame(String nameFile, String separator){
        String [] extension= nameFile.split(".");
        if(extension[1].equalsIgnoreCase("csv")){
            FileReader file;
            BufferedReader br = null;
      
      try {
         file= new FileReader(nameFile);
         br =new BufferedReader(file);
         String line = br.readLine();
         while (null!=line) {
            String [] fields = line.split(separator);
            line = br.readLine();
         }
         
      } catch (Exception e) {
         //Exception fichier
      } finally {
         if (null!=br) {
            br.close();
         }
      }

            
            
            
            
    }

        
        
        
        
            
        }
        
        
        
        
         
        
        
        
        
    }
    
}
