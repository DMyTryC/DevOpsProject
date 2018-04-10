/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.devopsproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bleuzeb
 */
public class DataFrame {

    HashMap<String, ArrayList> data = new HashMap<String, ArrayList>();

    public DataFrame(String nameFile, String separator) {

        FileReader fr = null;
        BufferedReader br = null;
        String extension;
        ArrayList donne;
        String[] values;
        Integer typeEntier;
        double typeDouble;
        float typeFloat;
        String typeCadena;
        HashMap prueba;

        extension = nameFile.substring(nameFile.lastIndexOf(".") + 1);
        if (extension.equalsIgnoreCase("csv")) {

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
                        donne.add(values[i]);

                        /*typeEntier = Integer.parseInt(values[i]);
                            typeDouble = Double.parseDouble(values[i]);
                            typeFloat = Float.parseFloat(values[i]);
                            typeCadena = (values[i]).toString();*/
                    }
                }
            } catch (Exception type) {

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
        ArrayList head;
        for (Map.Entry<String, ArrayList> entry : this.data.entrySet()) {
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
        ArrayList last;

        for (Map.Entry<String, ArrayList> entry : this.data.entrySet()) {
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

}
