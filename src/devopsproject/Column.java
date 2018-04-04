/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devopsproject;

import java.util.ArrayList;

/**
 *
 * @author bleuzeb
 */
public class Column <E> {
    
    String label ;
    ArrayList <E> content ;
    //String dataType ; 

    public E getContentAt(int i) {
	return content.get(i) ;
    }

    public void setContentAt(int i, E elt) {
	content.set(i, elt) ;
    }
    
    /*public String getDataType() {
	return dataType ;
    }*/
    
}
