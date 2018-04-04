/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devopsproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bleuzeb
 */
public class DataFrame {
    
    HashMap<String, ArrayList> data;
    

    
    

       
    public DataFrame(String nameFile, String separator){
    
        FileReader fr = null;
        BufferedReader br = null;
        ArrayList donne;
        String [] values;
      
        try {		
		fr = new FileReader (nameFile);
		br = new BufferedReader(fr);
                
                System.out.println(nameFile);
                String linea="";
                linea =br.readLine();
                
                String [] labels= linea.split(separator);
                donne= new ArrayList();

                
                while((linea =br.readLine())!=null){
                        values = linea.split(separator);
                        for(int i=0;i<labels.length;i++){
                            donne = this.data.get(labels[i]);
                            //verificar el tipo
                            donne.add(values[i]);
                            this.data.put(labels[i],donne);
                        }
                        
                }
                        
            }catch(Exception e){
           
            }finally{


           try{

              if( null != fr ){

                 fr.close();
              }

                }catch (Exception e2){
              e2.printStackTrace();

           }
        }
    }

    
    
        
 public static void main(String [] args) throws IOException {
     
     DataFrame d= new DataFrame("/home/stephanie/Documents/Documents/NewFolder/DevOps/src/src/devopsproject/test.csv",",");
     ArrayList toprint = d.data.get("Age");
     System.out.println(toprint.get(0));
 }    
            
            
            
        
         
        
        
        
        
    }
    

