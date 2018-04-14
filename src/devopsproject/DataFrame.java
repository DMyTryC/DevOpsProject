/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devopsproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DataFrame implements DataFrameItf {

    private int linesNumber; // Stocke la taille de la plus grande colonne pour l'affichage du Dataframe
    private List<String> orderedLabels; // Stocke l'ordre des labels pour afficher les colonnes du Dataframe dans le même ordre que celui donné lors de la construction
    private HashMap<String, Integer> indexLabels; // Permet de retrouver la position d'un label/d'une colonne
    private TreeMap<String, List> data; // Table d'association Label -> données

    public DataFrame() {
        this.linesNumber = 0;
        this.orderedLabels = new ArrayList<>();
        this.indexLabels = new HashMap<>();
        // Comparator pour ordonner les labels selon leur position donnée à la construction
        this.data = new TreeMap<>((String o1, String o2) -> {
            return indexLabels.get(o1) < indexLabels.get(o2) ? -1
                    : indexLabels.get(o1) > indexLabels.get(o2) ? 1 : 0;
        });
    }

    public DataFrame(String[] labels, List<List> elements) {
        this();
        for (int i = 0; i < elements.size(); i++) {
            this.linesNumber = Math.max(linesNumber, elements.size());
            this.orderedLabels.add(labels[i]);
            this.indexLabels.put(labels[i], i);
            this.data.put(labels[i], elements.get(i));
        }
    }

    public DataFrame(String nameFile, String separator) {
        this();
        FileReader fr;
        BufferedReader br;
        String extension;
        List donnees;
        String[] values;
        //verificar extension y si el archivo exist
        extension = nameFile.substring(nameFile.lastIndexOf(".") + 1);
        if (extension.equalsIgnoreCase("csv")) {
            try {

                // Stockage des labels
                fr = new FileReader(nameFile);
                br = new BufferedReader(fr);
                String linea;
                linea = br.readLine();
                String[] labels = linea.split(separator);
                for (int j = 0; j < labels.length; j++) {
                    donnees = new ArrayList<>();
                    this.orderedLabels.add(labels[j]);
                    this.indexLabels.put(labels[j], j);
                    this.data.put(labels[j], donnees);
                }

                // Stockage de la première ligne et inférence de classe (ou du type) de chacun de ses éléments
                String lineaType = br.readLine();
                String[] firstElement = lineaType.split(separator);
                String elementString;

                for (int j = 0; j < firstElement.length; j++) {
                    try {
                        int op1 = Integer.parseInt(firstElement[j]);
                        donnees = this.data.get(labels[j]);
                        donnees.add(op1);

                    } catch (NumberFormatException e1) {
                        try {
                            float op2 = Float.parseFloat(firstElement[j]);
                            donnees = this.data.get(labels[j]);
                            donnees.add(op2);
                        } catch (NumberFormatException e2) {
                            elementString = firstElement[j];
                            donnees = this.data.get(labels[j]);
                            donnees.add(elementString);
                        }
                    }
                }

                // Stockage des lignes restantes, inférence des classes de chaun de leus éléments et comparaison des classes avec la classe du premier élément
                while ((linea = br.readLine()) != null) {
                    values = linea.split(separator);
                    for (int i = 0; i < labels.length; i++) {
                        donnees = this.data.get(labels[i]);
                        try {
                            Integer op1 = Integer.parseInt(values[i]);
                            if (op1.getClass().equals((donnees.get(donnees.size() - 1)).getClass())) {
                                donnees.add(op1);
                            }

                        } catch (NumberFormatException e1) {
                            try {
                                Float op2 = Float.parseFloat(values[i]);
                                if (op2.getClass().equals((donnees.get(donnees.size() - 1)).getClass())) {
                                    donnees.add(op2);
                                }
                            } catch (NumberFormatException e2) {
                                elementString = values[i];
                                if (elementString.getClass().equals((donnees.get(donnees.size() - 1)).getClass())) {
                                    donnees.add(elementString);
                                }
                            }
                        }
                    }
                }

                // Stockage de la taille de la plus grande colonne
                for (Map.Entry<String, List> entry : data.entrySet()) {
                    
                    linesNumber = Math.max(linesNumber, entry.getValue().size());
                }

            } catch (IOException type) {
                System.err.print(type);
            }

        }
    }

    private void print(int deb, int n) {
        int l = deb;
        showLabels();
        while (l < n) {
            String values = "";
            for (Map.Entry<String, List> entry : data.entrySet()) {
                values = values + " | " + entry.getValue().get(l);
            }
            values = l + " " + values;
            System.out.println(values);
            l++;
        }
    }

    @Override
    public void show() {
        print(0, linesNumber);
    }

    public void head(String label, int n) {
        List head;
        for (Map.Entry<String, List> entry : this.data.entrySet()) {
            if (label.equals(entry.getKey()) && n < entry.getValue().size()) {
                head = entry.getValue();
                System.out.println("    " + label);
                for (int i = 0; i < n; i++) {
                    System.out.println(i + " | " + head.get(i));
                }
            }
        }
    }

    public void tail(String label, int n) {
        List last;
        for (Map.Entry<String, List> entry : this.data.entrySet()) {
            if (label.equals(entry.getKey()) && n < entry.getValue().size()) {
                last = entry.getValue();
                System.out.println("    " + label);
                for (int i = last.size() - n; i < last.size(); i++) {
                    System.out.println(i + " | " + last.get(i));
                }
            }
        }
    }

    @Override
    public void head(int n) {
        if (n > linesNumber) {
            throw new IllegalArgumentException("Number of lines > Number of lines of Dataframe !");
        }
        print(0, n);
    }

    @Override
    public void tail(int n) {
        int l;
        if ((l = linesNumber - n) < 0) {
            throw new IllegalArgumentException("Number of lines > Number of lines of Dataframe !");
        }
        print(l, linesNumber);
    }

    @Override
    public void showLabels() {
        String lab = " ";
        for (String orderedLabel : orderedLabels) {
            lab = lab + " | " + orderedLabel;
        }
        System.out.println(lab);
    }

    @Override
    public int size() {
        int size = 0;
        for (Map.Entry<String, List> entry : this.data.entrySet()) {
            size += entry.getValue().size();
        }
        return size;
    }

    private class InfosColumn {

        List column;
        String label;

        public InfosColumn(List column, String label) {
            this.column = column;
            this.label = label;
        }

    }

    private InfosColumn column(String label) {

        List column;
        if ((column = data.get(label)) == null) {
            throw new IllegalArgumentException("Label " + label + " does not exist !");
        }
        return new InfosColumn(column, label);
    }

    @Override
    public DataFrameItf loc(String label) {
        List<List> elements = new ArrayList<>();
        elements.add(column(label).column);
        String[] labels = {label};
        return new DataFrame(labels, elements);
    }

    @Override
    public DataFrameItf loc(List<String> labels) {
        List<List> elements = new ArrayList<>(labels.size());
        for (String label : labels) {
            elements.add(column(label).column);
        }
        return new DataFrame((String[]) labels.toArray(), elements);
    }

    @Override
    public DataFrameItf loc(String labelInf, String labelSup) {

        InfosColumn infosInf = column(labelInf);
        InfosColumn infosSup = column(labelSup);

        int inf = indexLabels.get(infosInf.label), sup = indexLabels.get(infosSup.label);

        int size = Math.abs(sup - inf);
        List<List> elements = new ArrayList<>(size);
        String[] labels = new String[size];

        if (inf > sup) {
            int tmp = inf;
            sup = inf;
            inf = tmp;
        }

        for (int i = inf, j = 0; i <= sup; i++, j++) {
            labels[j] = this.orderedLabels.get(i);
            elements.add(data.get(this.orderedLabels.get(i)));
        }

        return new DataFrame(labels, elements);

    }

    @Override
    public DataFrameItf loc(String... labels) {
        List<String> labelsList = new ArrayList<>(labels.length);
        labelsList.addAll(Arrays.asList(labels));
        return loc(labelsList);
    }

    @Override
    public DataFrameItf iloc(int index) {
        List elements = new ArrayList<>(orderedLabels.size());
        for (String label : orderedLabels) {
            elements.add(data.get(label).get(index));
        }
        return new DataFrame();
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
    public double meanColumn(String label) {
        List donnee;
        double mean = 0;
        Double num;
        for (Map.Entry<String, List> entry : this.data.entrySet()) {
            if (label.equals(entry.getKey())) {
                donnee = entry.getValue();
                for (int i = 0; i < donnee.size(); i++) {
                        num= new Double(donnee.get(i).toString());
                        mean = mean +  num;
                }
                mean = mean / donnee.size();
            }
        }
        return mean;
    }

    @Override
    public double minColumn(String label) {
        List donnee;
        Double min = null;
        
        for (Map.Entry<String, List> entry : this.data.entrySet()) {
            if (label.equals(entry.getKey())) {
                donnee = entry.getValue();
                min = new Double(donnee.get(0).toString());
                for (int i = 1; i < donnee.size(); i++) {
                    if (new Double(donnee.get(i).toString()) < min) {
                        min = new Double(donnee.get(i).toString());
                    }
                }
            }
        }
        return min;
        
    }

    @Override
    public double maxColumn(String label) {
         List donnee;
        Double max = null;
        
        for (Map.Entry<String, List> entry : this.data.entrySet()) {
            if (label.equals(entry.getKey())) {
                donnee = entry.getValue();
                max = new Double(donnee.get(0).toString());
                for (int i = 1; i < donnee.size(); i++) {
                    if (new Double(donnee.get(i).toString()) > max) {
                        max = new Double(donnee.get(i).toString());
                    }
                }
            }
        }
        return max;
        
    }    
    
    public void showStatitic(String label){
        System.out.println("Label : "+label);
        System.out.println("Mean : "+meanColumn(label));
        System.out.println("Minimum : "+minColumn(label));
        System.out.println("Maximum : "+maxColumn(label));

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
