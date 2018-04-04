/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devopsproject;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.TreeMap;

public class DataFrame implements DataFrameItf {

    private HashMap<String, List> data;

    public DataFrame(){
        this.data = new HashMap<String, List>();
    }

    public DataFrame(String[] labels, List<List> elements){
        this.data = new HashMap<String, List>();
        List<String> element;
        for(int i=0;i< elements.size();i++) {
            this.data.put(labels[i], elements.get(i));
        }
    } 

    @Override
    public void show() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void head(int n) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void tail(int n) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List loc(String label) {
	//return columns.get(label);
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DataFrameItf loc(List<String> labels) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DataFrameItf loc(String labelInf, String labelSup) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public DataFrameItf loc(String... labels) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DataFrameItf iloc(int index) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DataFrameItf iloc(List<Integer> indexes) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DataFrameItf iloc(Integer... indexes) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DataFrameItf iloc(int indexInf, int indexSup) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer meanColumn(String label) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer minColumn(String label) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer maxColumn(String label) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addToColumn(String label, List values) {
	// check if label exists
	// check if the values have the same type
	if (!values.isEmpty()){
	    if (!data.containsKey(label)){
		data.put(label, values);
	    } else if (!data.get(label).isEmpty()){
		if (data.get(label).getClass().getTypeName().equals(values.get(0).getClass().getTypeName())){
		    data.put(label, values);
		} else {
		    throw new IllegalArgumentException("The type of the values is incompatible with the type of the column at label : "+label);
		}
	    }
	}
    }

    @Override
    public void deleteFromColumn(String label) {
	// check if label exists
	//columns.remove(label);
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
