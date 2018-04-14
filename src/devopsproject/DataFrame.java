/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devopsproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class DataFrame implements DataFrameItf {

    
    HashMap<String, Integer> indexLabels;
    TreeMap<String, List> data;

    public DataFrame() {
        this.indexLabels = new HashMap<>();
        this.data = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
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
                            this.indexLabels.put(labels[j], j);
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
        String labelsHash = "";
        String values = "0";
        ArrayList donnee = new ArrayList();
        int l = 0;
        showLabels();
        do {
            for (Iterator iter = this.data.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                labelsHash = entry.getKey().toString();

                donnee = (ArrayList) entry.getValue();
                values = values + " | " + (String) donnee.get(l);

            }
            l++;
            System.out.println(values);
                values = ""+l;
        } while (l < longuerLabels());

    }

    public int longuerLabels() {
        ArrayList donnee = new ArrayList();
        int sizeMaxInitial = 0;
        for (Iterator iter = this.data.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            donnee = (ArrayList) entry.getValue();
            if (donnee.size() > sizeMaxInitial) {
                sizeMaxInitial = donnee.size();
            }
        }
        return sizeMaxInitial;

    }

    public void head(String label, int n) {
        List head;
        for (Map.Entry<String, List> entry : this.data.entrySet()) {
            if (label.equals(entry.getKey()) && n < entry.getValue().size()) {
                head = entry.getValue();
                System.out.println("    "+label);
                for (int i = 0; i < n; i++) {
                    System.out.println(i+" | "+head.get(i));
                }
            }

        }
    }

    public void tail(String label, int n) {
        List last;

        for (Map.Entry<String, List> entry : this.data.entrySet()) {
            if (label.equals(entry.getKey()) && n < entry.getValue().size()) {
                last = entry.getValue();
                System.out.println("    "+label);
                for (int i = last.size() - n; i < last.size(); i++) {
                    System.out.println(i+" | "+last.get(i));
                }
            }
        }
    }

    public void head(int n) {
        String labels = "";
        String values = "0";
        ArrayList d = new ArrayList();
        int l = 0;
        showLabels();
        do {
            for (Iterator iter = this.data.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                labels = labels + " | " + entry.getKey();
                d = (ArrayList) entry.getValue();
                values = values + " | " + (String) d.get(l);
            }
            System.out.println(values);
            values = ""+l;
            l++;
        } while (l < n);

    }

    //DataFrame = tail?
    public void tail(int n) {
        String labels = "";
        String values = "0";
        ArrayList d = new ArrayList();
        int l = 0;
        int p = 0;
        showLabels();
        do {
            for (Iterator iter = this.data.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                labels = labels + " | " + entry.getKey();
                d = (ArrayList) entry.getValue();
                p = (d.size() - n) + l;
                values = values + " | " + (String) d.get(p);
            }

            l++;
            System.out.println(values);
            values = ""+l;
        } while (p < d.size() - 1);
    }

    /**
     *
     */
    public void showLabels() {
        String lab = " ";
        for (Iterator iter = this.data.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            lab = lab + " | " + entry.getKey();
        }
        System.out.println(lab);

    }

    public void size() {
        int size = 0;
        ArrayList values = new ArrayList();
        for (Iterator iter = this.data.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();

            values = (ArrayList) entry.getValue();
            size += values.size();
        }
        System.out.println(size);
    }

    public List loc(String label) {
        List column;
        if ((column = data.get(label)) == null) {
            throw new IllegalArgumentException("Label " + label + " does not exist !");
        }
        return column;
    }

    public DataFrameItf loc(List<String> labels) {
        List<List> elements = new ArrayList<>(labels.size());
        for (String label : labels) {
            elements.add(loc(label));
        }
        return new DataFrame((String[]) labels.toArray(), elements);
    }

    public DataFrameItf loc(String labelInf, String labelSup) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DataFrameItf loc(String... labels) {
        List<String> labelsList = new ArrayList<>(labels.length);
        for (String label : labels) {
            labelsList.add(label);
        }
        return loc(labelsList);
    }

    public DataFrameItf iloc(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DataFrameItf iloc(List<Integer> indexes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DataFrameItf iloc(Integer... indexes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DataFrameItf iloc(int indexInf, int indexSup) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Integer meanColumn(String label) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Integer minColumn(String label) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Integer maxColumn(String label) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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

    public void deleteFromColumn(String label) {
        // check if label exists
        //columns.remove(label);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public TreeMap<String, List> groupby(String[] labels){
        TreeMap<String, List> dataReturnd = new TreeMap<String, List>();
        List listReturnd;
        for(int i=0;i<labels.length;i++){
            listReturnd = groupby(labels[i]);
            dataReturnd.put(labels[i], listReturnd);
        }
        return dataReturnd;
    }
 
    /*
       groupby one column, it takes the label as param then it returns the list 
    */
    public List groupby(String label){
        List dataReturnd = new ArrayList();
        List data = this.data.get(label);
        for(int i=0;i<data.size();i++){
               if(!dataReturnd.contains(data.get(i))){
                   dataReturnd.add(data.get(i));
               }
        }
        return dataReturnd;
    }
    /*
          it takes the label and the aggreagate as an optional param "{max, sum} of column"
    */
    public TreeMap<String, Object> groupby(String label, Optional<String> aggregate){
        TreeMap<String, Object> dataReturnd = new TreeMap<String, Object>();
        List data = this.data.get(label);
        if(aggregate.isPresent()){
            switch(aggregate.get()){
                case "sum":
                    dataReturnd.put(aggregate.get()+"("+label+")",sum(data));
                    break;
                case "max":
                     dataReturnd.put(aggregate.get()+"("+label+")",Collections.max(data));
                    break;
            }
        }
        return dataReturnd;
    }
    
    /*
        it takes a list of labels and aggreagate as optional (just count)
    */
    
    public TreeMap<String, List> groupby(String[] labels, String aggregate){
        TreeMap<String, List> dataReturned = new TreeMap<String, List>();
        List listReturnd;
        List labelListData;
        List aggregateList;
            if(aggregate.equals("count")){
                for(int i=0;i<labels.length;i++){
                    labelListData = this.data.get(labels[i]);
                    aggregateList = new ArrayList<Integer>();
                    listReturnd = new ArrayList();
                    for(int j=0;j<labelListData.size();j++){
                       if(!listReturnd.contains(labelListData.get(i))){
                           listReturnd.add(labelListData.get(i));
                           aggregateList.add(count(labelListData,labelListData.get(i)));
                       }
                    }
                    dataReturned.put(labels[i], listReturnd);
                    dataReturned.put(aggregate+"("+labels[i]+")", aggregateList);
                }
            }
        return dataReturned;
    }
    
    
        
    public int count(List list, Object element){
        int cpt = 0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).equals(element)) cpt++;
        }
        return cpt;
    }
    
    public Object sum(List list){
        if(list.get(0) instanceof Integer){
                int somme = 0;
                for(int i=0;i<list.size();i++){
                    somme = somme + (Integer)list.get(i);
                }
                return somme;
        }
        else{
             if(list.get(0) instanceof Double){
                float somme = 0;
                for(int i=0;i<list.size();i++){
                    somme = somme + (Float)list.get(i);
                }
                return somme;
            } else {
                 throw new EmptyStackException();
             }
        }
    }
    
}
