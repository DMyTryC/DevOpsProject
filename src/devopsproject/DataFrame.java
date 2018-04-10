package devopsproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataFrame implements DataFrameItf {

    HashMap<String, ArrayList> data;

    public DataFrame() {
        this.data = new HashMap<String, ArrayList>();
    }

    public DataFrame(String[] labels, ArrayList<ArrayList> elements) {
        this();
        ArrayList<String> element;
        for (int i = 0; i < elements.size(); i++) {
            this.data.put(labels[i], elements.get(i));
        }
    }

    public DataFrame(String nameFile, String separator) {
        this();
        FileReader fr = null;
        BufferedReader br = null;
        ArrayList donne;
        String[] values;
        HashMap prueba;
        //verificar extension y si el archivo existe

        try {
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

            while ((linea = br.readLine()) != null) {
                //System.out.println(linea);
                values = linea.split(separator);
                for (int i = 0; i < labels.length; i++) {
                    donne = this.data.get(labels[i]);
                    //verificar el tipo
                    prueba = this.data;
                    donne.add(values[i]);
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

    public void lost(int n) {
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
    public void tail(int n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DataFrameItf loc(String label) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DataFrameItf loc(ArrayList<String> labels) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DataFrameItf loc(String labelInf, String labelSup) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
