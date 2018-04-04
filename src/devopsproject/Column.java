/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devopsproject;

import java.util.ArrayList;
import java.util.List;

public class Column<Elements> {

    List<Elements> list;
    String typeVariables;
    
    public Column(){
	this.typeVariables = "Undefined";
	list = new ArrayList<>();
    }
    
    public Column(List<Elements> list){
	this.list = list;
	if (!list.isEmpty()){
	    this.typeVariables = list.get(0).getClass().getTypeName() ;
	} else {
	    this.typeVariables = "Undefined";
	}
    }

    public List<Elements> getList() {
	return list;
    }

    public String getTypeVariables() {
	return typeVariables;
    }

    public void setList(List<Elements> list) {
	this.list = list;
	if (!list.isEmpty()){
	    this.typeVariables = list.get(0).getClass().getTypeName() ;
	} else {
	    this.typeVariables = "Undefined";
	}
    }
    
    // Modification of the column (adding a cell to it)
}