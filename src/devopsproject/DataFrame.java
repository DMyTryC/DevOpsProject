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

    HashMap<String, List> data;
    
    public DataFrame(){
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
        List donne;
        String[] values;
        HashMap prueba;
        //verificar extension y si el archivo existe
        try {
            System.out.print("hi");
            fr = new FileReader(nameFile);
            br = new BufferedReader(fr);
            System.out.println(nameFile);
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
                    Object obj = donne.get(donne.size()-1);
                    if(values[i].getClass().equals(obj.getClass())){
                        donne.add(values[i]);
                    }
                    else {
                        throw new EmptyStackException();
                    }
                }

            }

        } catch (Exception e) {

        } finally {

            try {

                if (null != fr) {

                    fr.close();
                }

            } catch (Exception e2) {
                e2.printStackTrace();

            }
        }

    }

    public void show() {
        String labels = "";
        String values = "";
        ArrayList d = new ArrayList();
        int l = 0;
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

    // To check with Riad, head function doesn't need to work like this
    /*public void head(String label, int n){
     ArrayList head;
    for (Map.Entry<String, ArrayList> entry : this.data.entrySet()) { 
        if(label.equals(entry.getKey()) && n<entry.getValue().size()){
            head=entry.getValue();
            System.out.println(label);
            for(int i=0;i<n;i++){
                System.out.println(head.get(i));
            }
        }
    
    }
    }
  
  
  public void last(String label, int n){
           ArrayList last;
    for (Map.Entry<String, ArrayList> entry : this.data.entrySet()) { 
        if(label.equals(entry.getKey()) && n<entry.getValue().size()){
            last=entry.getValue();
            System.out.println(label);
            for(int i=n;i<last.size();i++){
                System.out.println(last.get(i));
            }
        }
    
    }
    }*/
    public void head(int n) {
        String labels = "";
        String values = "";
        ArrayList d = new ArrayList();
        int l = 0;
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

    public void tail(int n) {
        String labels = "";
        String values = "";
        ArrayList d = new ArrayList();
        int l = n;
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
        } while (l < d.size());

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
