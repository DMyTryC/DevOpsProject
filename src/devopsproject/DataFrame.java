/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.devopsproject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author bleuzeb
 */
public class DataFrame {
    
    public String label;
    public ArrayList column;

    public DataFrame(String label, ArrayList column) {
        this.label = label;
        this.column = column;
    }
    
    public DataFrame(String nameFile){
        File file = new File(nameFile);
        
    }

        
    }
    

