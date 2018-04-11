/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package devopsproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class DataFrame implements DataFrameItf {

    List<String> labels ;
    HashMap<String, List> data ;
    
    public DataFrame(){
	this.labels = new ArrayList<>() ;
        this.data = new HashMap<>();
    }

    public DataFrame(String[] labels, List<List> elements) {
        this();
        List<String> element;

        for (int i = 0; i < elements.size(); i++) {
            this.data.put(labels[i], elements.get(i));
        }
    }

    public DataFrame(String nameFile, String separator) {
	this();
        FileReader fr = null;
        BufferedReader br = null;
        String extension;
        List donne;
        String[] values;
        //verificar extension y si el archivo exist
        extension = nameFile.substring(nameFile.lastIndexOf(".") + 1);
        if (extension.equalsIgnoreCase("csv")) {

            try {
            fr = new FileReader(nameFile);
            br = new BufferedReader(fr);
            String linea = "";
            linea = br.readLine();
            String[] labels = linea.split(separator);
            for (int j = 0; j < labels.length; j++) {
                donne = new ArrayList();
                this.data.put(labels[j], donne);
            }
            String lineaType  = br.readLine();
            String[] firstElement = lineaType.split(separator);
            String elementString = "";
            for (int j = 0; j < firstElement.length; j++) {
               try {
                      System.out.print(firstElement[j]);
                      int  op1 = Integer.parseInt(firstElement[j]);
                      donne = this.data.get(labels[j]);
                      donne.add(op1);
                      
                    } 
               catch (NumberFormatException e1) {
                   try {
                            float  op2 = Float.parseFloat(firstElement[j]);
                            donne = this.data.get(labels[j]);
                            donne.add(op2);
                    }
                    catch(NumberFormatException e2){
                            elementString = firstElement[j];
                            donne = this.data.get(labels[j]);
                            donne.add(elementString);
                    }
                } 
            }
            
            while ((linea = br.readLine()) != null) {
                values = linea.split(separator);
                for (int i = 0; i < labels.length; i++) {
                    donne = this.data.get(labels[i]);
                    if(values[i].getClass().equals((donne.get(donne.size()-1)).getClass())){
                        donne.add(values[i]);
                    }
                    else {
                        throw new EmptyStackException();
                    }
                }

            }

        } catch (Exception fileNull) {

        }

        }

    }

        public void show() {
        String labels = "";
        String values = "";
        ArrayList d = new ArrayList();
        int l = 0;
        showLabels();
        do {
            for (Iterator iter = this.data.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                labels = labels + " || " + entry.getKey();
                d = (ArrayList) entry.getValue();
                values = values + " || " + (String) d.get(l);
            }
            System.out.println(values);
            values = "";
            l++;
        } while (l < 3);

    }

    public void head(String label, int n) {
        List head;
        for (Map.Entry<String, List> entry : this.data.entrySet()) {
            if (label.equals(entry.getKey()) && n < entry.getValue().size()) {
                head = entry.getValue();
                System.out.println(label);
                for (int i = 0; i < n; i++) {
                    System.out.println(head.get(i));
                }
            }

        }
    }

    public void tail(String label, int n) {
        List last;

        for (Map.Entry<String, List> entry : this.data.entrySet()) {
            if (label.equals(entry.getKey()) && n < entry.getValue().size()) {
                last = entry.getValue();
                System.out.println(label);
                for (int i = last.size() - n; i < last.size(); i++) {
                    System.out.println((String) last.get(i));
                }
   	    }
	}
    }

    public void head(int n) {
        String labels = "";
        String values = "";
        ArrayList d = new ArrayList();
        int l = 0;
        showLabels();
        do {
            for (Iterator iter = this.data.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                labels = labels + " || " + entry.getKey();
                d = (ArrayList) entry.getValue();
                values = values + " || " + (String) d.get(l);
            }
            System.out.println(values);
            values = "";
            l++;
        } while (l < n);

    }

    //DataFrame = tail?
    public void tail(int n) {
        String labels = "";
        String values = "";
        ArrayList d = new ArrayList();
        int l = 0;
        int p = 0;
        showLabels();
        do {
            for (Iterator iter = this.data.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                labels = labels + " || " + entry.getKey();
                d = (ArrayList) entry.getValue();
                p = (d.size() - n) + l;
                values = values + " || " + (String) d.get(p);
            }

            l++;
            System.out.println(values);
            values = "";
        } while (p < d.size() - 1);
    }

    public void showLabels() {
        String lab = "";
        for (Iterator iter = this.data.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            lab = lab + " || " + entry.getKey();
        }
        System.out.println(lab);

    }


   public void size(){
   int size=0;
   ArrayList values= new ArrayList();
    for (Iterator iter = this.data.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                
                values = (ArrayList) entry.getValue();
                size+=values.size();
    }
       System.out.println(size);
}

    public void showLabels() {
        String lab = "";
        for (Iterator iter = this.data.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            lab = lab + " || " + entry.getKey();
        }
        System.out.println(lab);

    }


   public void size(){
   int size=0;
   ArrayList values= new ArrayList();
    for (Iterator iter = this.data.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                
                values = (ArrayList) entry.getValue();
                size+=values.size();
    }
       System.out.println(size);
}

    @Override
    public List loc(String label) {
	List column ;
	if ((column = data.get(label)) == null)
	    throw new IllegalArgumentException("Label " + label + " does not exist !") ;
        return column ;
    }

    @Override
    public DataFrameItf loc(List<String> labels) {
	List<List> elements = new ArrayList<>(labels.size());
	for (String label : labels) {
	    elements.add(loc(label)) ;
	}
	return new DataFrame((String [])labels.toArray(), elements) ;
    }

    @Override
    public DataFrameItf loc(String labelInf, String labelSup) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DataFrameItf loc(String... labels) {
	List<String> labelsList = new ArrayList<>(labels.length);
	for (String label : labels) {
	    labelsList.add(label) ;
	}
        return loc(labelsList) ;
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
        if (!values.isEmpty()) {
            if (!data.containsKey(label)) {
                data.put(label, values);
            } else if (!data.get(label).isEmpty()) {
                if (data.get(label).getClass().getTypeName().equals(values.get(0).getClass().getTypeName())) {
                    data.put(label, values);
                } else {
                    throw new IllegalArgumentException("The type of the values is incompatible with the type of the column at label : " + label);
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
