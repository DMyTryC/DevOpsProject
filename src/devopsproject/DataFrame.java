/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devopsproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class DataFrame implements DataFrameItf {
    
    private int linesNumber ;
    private List<String> orderedLabels ;
    private HashMap<String, Integer> indexLabels ;
    private TreeMap<String, List<Object>> data ;

    public DataFrame() {
        this.linesNumber = 0 ;
        this.orderedLabels = new ArrayList<>();
        this.indexLabels= new HashMap<>();
        this.data = new TreeMap<>((String o1, String o2) -> {
                return indexLabels.get(o1) < indexLabels.get(o2) ? -1 :
                       indexLabels.get(o1) > indexLabels.get(o2) ?  1 : 0 ;
        });
    }

    public DataFrame(String[] labels, List<List<Object>> elements) {
        this();
        for (int i = 0; i < elements.size(); i++) {
            this.linesNumber = Math.max(linesNumber, elements.size());
            this.orderedLabels.add(labels[i]);
            this.indexLabels.put(labels[i], i) ;
            this.data.put(labels[i], elements.get(i));
        }
    }

    public DataFrame(String nameFile, String separator) {
        this();
        FileReader fr ;
        BufferedReader br ;
        String extension;
        List<Object> donnees;
        String[] values;

        extension = nameFile.substring(nameFile.lastIndexOf(".") + 1);
        if (extension.equalsIgnoreCase("csv")) {

            try {

            fr = new FileReader(nameFile);
            br = new BufferedReader(fr);
            String linea ;
            linea = br.readLine();
            String[] labels = linea.split(separator);
            for (int j = 0; j < labels.length; j++) {
                donnees = new ArrayList<>();
                this.data.put(labels[j], donnees);
                this.indexLabels.put(labels[j], j);
            }
            String lineaType  = br.readLine();
            String[] firstElement = lineaType.split(separator);
            String elementString ;
            for (int j = 0; j < firstElement.length; j++) {
               try {
                      System.out.print(firstElement[j]);
                      int  op1 = Integer.parseInt(firstElement[j]);
                      donnees = this.data.get(labels[j]);
                      donnees.add(op1);
                      
                    } 
               catch (NumberFormatException e1) {
                   try {
                            float  op2 = Float.parseFloat(firstElement[j]);
                            donnees = this.data.get(labels[j]);
                            donnees.add(op2);
                    }
                    catch(NumberFormatException e2){
                            elementString = firstElement[j];
                            donnees = this.data.get(labels[j]);
                            donnees.add(elementString);
                    }
                } 
            }
            
            while ((linea = br.readLine()) != null) {
                values = linea.split(separator);
                for (int i = 0; i < labels.length; i++) {
                    donnees = this.data.get(labels[i]);
                    if(values[i].getClass().equals((donnees.get(donnees.size()-1)).getClass())){

                        donnees.add(values[i]);

                    }
                }
            }
            } catch (Exception type) {

            }

        }

    }
    
    
    private void print(int deb, int n) {
        String values = "";
        int l = deb;
        showLabels();
        while (l < n) {
            values += l ;
            for (Map.Entry<String, List<Object>> entry : data.entrySet())
                values = values + " | " + entry.getValue().get(l);
            System.out.println(values);
            l++;
        }
    }

    @Override
    public void show() {
        print(0, linesNumber);
    }

    public void head(String label, int n) {
        List<Object> head;
        for (Map.Entry<String, List<Object>> entry : this.data.entrySet()) {
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
        List<Object> last;
        for (Map.Entry<String, List<Object>> entry : this.data.entrySet()) {
            if (label.equals(entry.getKey()) && n < entry.getValue().size()) {
                last = entry.getValue();
                System.out.println("    "+label);
                for (int i = last.size() - n; i < last.size(); i++) {
                    System.out.println(i+" | "+last.get(i));
                }
            }
        }
    }

    @Override
    public void head(int n) {
        if (n > linesNumber)
            throw new IllegalArgumentException("Number of lines > Number of lines of Dataframe !") ;
        print(0,n) ;
    }

    //DataFrame = tail?
    @Override
    public void tail(int n) {
        String labels = "";
        String values = "";
        int l ;
        if ((l = linesNumber - n) < 0)
            throw new IllegalArgumentException("Number of lines > Number of lines of Dataframe !") ;
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


    public void showLabels() {
        String lab = " ";
        for (Iterator iter = this.data.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            lab = lab + " | " + entry.getKey();
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
    
    private class InfosColumn {
	List<Object> column ;
	String label ;

	public InfosColumn(List<Object> column, String label) {
	    this.column = column;
	    this.label = label;
	}
	
    }
   
    private InfosColumn column(String label) {
	
	List<Object> column ;
	if ((column = data.get(label)) == null)
	    throw new IllegalArgumentException("Label " + label + " does not exist !") ;
	return new InfosColumn(column, label) ;
    }

    @Override
    public DataFrameItf loc(String label) {
	List<List<Object>> elements = new ArrayList<>();
	elements.add(column(label).column) ;
	String [] labels = {label} ;
        return new DataFrame(labels, elements) ;
    }

    @Override
    public DataFrameItf loc(List<String> labels) {
	List<List<Object>> elements = new ArrayList<>(labels.size());
	for (String label : labels) {
	    elements.add(column(label).column) ;
	}
	return new DataFrame((String [])labels.toArray(), elements) ;
    }

    public DataFrameItf loc(String labelInf, String labelSup) {
        
	InfosColumn infosInf = column(labelInf) ;
	InfosColumn infosSup = column(labelSup) ;
	
	int inf = indexLabels.get(infosInf.label), sup = indexLabels.get(infosSup.label) ;
	
	int size = Math.abs(sup - inf) ;
	List<List> elements = new ArrayList<>(size) ;
	String [] labels = new String[size] ;
	
	if (inf > sup) {
	    int tmp = inf ;
	    sup = inf ;
	    inf = tmp ;
	}
	
	for (int i = inf, j = 0 ; i <= sup; i++, j++) {
	    labels[j] = this.orderedLabels.get(i) ;
	    elements.add(data.get(this.orderedLabels.get(i))) ;
	}
    
	return new DataFrame(labels, elements) ;
	
    }

    public DataFrameItf loc(String... labels) {
        List<String> labelsList = new ArrayList<>(labels.length);
        for (String label : labels) {
            labelsList.add(label);
        }
        return loc(labelsList);
    }

    public DataFrameItf iloc(int index) {
	List elements = new ArrayList<>(orderedLabels.size());
        for (String label : orderedLabels) {
	    elements.add(data.get(label).get(index)) ;
	}
	return new DataFrame() ;
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
}
