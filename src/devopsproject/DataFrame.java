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
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DataFrame implements DataFrameItf {

    private int linesNumber; // Stocke la taille de la plus grande colonne pour l'affichage du Dataframe
    private List<String> orderedLabels; // Stocke l'ordre des labels pour afficher les colonnes du Dataframe dans le même ordre que celui donné lors de la construction
    private HashMap<String, Integer> indexLabels; // Permet de retrouver la position d'un label/d'une colonne
    private TreeMap<String, List> data; // Table d'association Label -> données

    // Comparator pour ordonner les labels selon leur position donnée à la construction
    private class DataComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return indexLabels.get(o1) < indexLabels.get(o2) ? -1
                    : indexLabels.get(o1) > indexLabels.get(o2) ? 1 : 0;
        }
    }

    public DataFrame() {
        this.linesNumber = 0;
        this.orderedLabels = new ArrayList<>();
        this.indexLabels = new HashMap<>();
        this.data = new TreeMap<>(new DataComparator());
    }

    public DataFrame(String[] labels, List<List> elements) {
        this();
        for (int i = 0; i < elements.size(); i++) {
            this.linesNumber = Math.max(linesNumber, elements.get(i).size());
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
                            if (op1.getClass().equals((donnees.get(0).getClass()))) {
                                donnees.add(op1);
                            }

                        } catch (NumberFormatException e1) {
                            try {
                                Float op2 = Float.parseFloat(values[i]);
                                if (op2.getClass().equals((donnees.get(0)).getClass())) {
                                    donnees.add(op2);
                                }
                            } catch (NumberFormatException e2) {
                                elementString = values[i];
                                donnees.add(elementString);
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
                values = values + " | " + (l < entry.getValue().size() ? entry.getValue().get(l).toString() : " ");
            }
            values = l + " " + values;
            System.out.println(values);
            l++;
        }
    }

    @Override
    public void head(int n) {
        print(0, checkingLinesNumber(n, PrintingType.HEAD));
    }

    @Override
    public void tail(int n) {
        print(checkingLinesNumber(n, PrintingType.TAIL), linesNumber);
    }

    private enum PrintingType {
        HEAD, TAIL
    };

    private int checkingLinesNumber(int n, PrintingType type) {
        if (linesNumber - n < 0) {
            throw new IllegalArgumentException("Number of lines > Number of lines of Dataframe !");
        }
        if (n < 0) {
            throw new IllegalArgumentException("Number of lines < 0 !");
        }
        return type == PrintingType.HEAD ? n : linesNumber - n;
    }

    @Override
    public void show() {
        print(0, linesNumber);
    }

    public void head(String label, int n) {
        column(label);
        System.out.println(label + " : " + data.get(label).subList(0, checkingLinesNumber(n, PrintingType.HEAD)));
    }

    public void tail(String label, int n) {
        column(label);
        System.out.println(label + " : " + data.get(label).subList(checkingLinesNumber(n, PrintingType.TAIL), data.get(label).size()));
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

    private List column(String label) {
        try {
            data.containsKey(label);
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException("Label " + label + " does not exist !");
        }
        return data.get(label);
    }

    @Override
    public DataFrameItf loc(String label) {
        List<List> elements = new ArrayList<>();
        elements.add(new ArrayList<>(column(label)));
        String[] labels = {label};
        return new DataFrame(labels, elements);
    }

    @Override
    public DataFrameItf loc(List<String> labels) {
        List<List> elements = new ArrayList<>(labels.size());
        for (String label : labels) {
            elements.add(new ArrayList<>(column(label)));
        }
        String[] labelsArray = new String[labels.size()];
        return new DataFrame(labels.toArray(labelsArray), elements);
    }

    @Override
    public DataFrameItf loc(String labelInf, String labelSup) {

        column(labelInf);
        column(labelSup);

        int inf = indexLabels.get(labelInf), sup = indexLabels.get(labelSup);

        int size = Math.abs(sup - inf) + 1;
        List<List> elements = new ArrayList<>(size);
        String[] labels = new String[size];

        if (inf > sup) {
            int tmp = inf;
            inf = sup;
            sup = tmp;
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

    private DataFrame initDataFrameBeforeSelectingLines(int linesNumber) {
        DataFrame df = new DataFrame();
        df.linesNumber = linesNumber;
        df.orderedLabels = new ArrayList<>(orderedLabels);
        df.indexLabels = new HashMap<>(indexLabels);
        df.data = new TreeMap<>(data);
        return df;
    }

    private void checkingIndex(int index) {
        if (index > linesNumber) {
            throw new IllegalArgumentException("index > Number of lines of DataFrame !");
        }
        if (index < 0) {
            throw new IllegalArgumentException("index < 0 !");
        }
    }

    @Override
    public DataFrameItf iloc(int index) {
        checkingIndex(index);
        DataFrame df = initDataFrameBeforeSelectingLines(1);
        for (String label : orderedLabels) {
            df.data.replace(label, new ArrayList(Arrays.asList(data.get(label).get(index))));
        }
        return df;
    }

    private List indexToElement(List<Integer> indexes, List elements) {
        return indexes.stream().map((index) -> {
            return elements.get(index);
        }).collect(Collectors.toList());
    }

    @Override
    public DataFrameItf iloc(List<Integer> indexes) {
        for (Integer index : indexes) {
            checkingIndex(index);
        }
        DataFrame df = initDataFrameBeforeSelectingLines(indexes.size());
        for (String label : orderedLabels) {
            df.data.replace(label, indexToElement(indexes, data.get(label)));
        }
        return df;
    }

    @Override
    public DataFrameItf iloc(Integer... indexes) {
        for (Integer index : indexes) {
            checkingIndex(index);
        }
        DataFrame df = initDataFrameBeforeSelectingLines(indexes.length);
        for (String label : orderedLabels) {
            df.data.replace(label, indexToElement(new ArrayList<>(Arrays.asList(indexes)), data.get(label)));
        }
        return df;
    }

    @Override
    public DataFrameItf iloc(int indexInf, int indexSup) {
        int inf = indexInf, sup = indexSup;
        if (inf > sup) {
            int tmp = inf;
            inf = sup;
            sup = tmp;
        }
        checkingIndex(inf);
        checkingIndex(sup);
        DataFrame df = initDataFrameBeforeSelectingLines(sup - inf + 1);
        for (String label : orderedLabels) {
            df.data.replace(label, IntStream.range(inf, sup + 1).boxed().map((index) -> {
                return data.get(label).get(index);
            }).collect(Collectors.toList()));
        }
        return df;
    }

    private void checkingNumberFormat(String label) {
        if (!(data.get(label).get(0) instanceof Number)) {
            throw new IllegalArgumentException("Column at Label " + label + " is not Numeric !");
        }
    }

    private Class<?> checkingComparable(String label) {
        if (!(data.get(label).get(0) instanceof Comparable)) {
            throw new IllegalArgumentException("Column at Label " + label + " is not Comparable !");
        }
        return data.get(label).get(0).getClass();
    }

    @Override
    public Double meanColumn(String label) {
        column(label);
        checkingNumberFormat(label);
        int n = 1;
        Double sum = ((Number) data.get(label).get(0)).doubleValue();
        for (int i = 1; i < data.get(label).size(); i++) {
            if (!(data.get(label).get(i) instanceof String)) {
                sum += ((Number) data.get(label).get(i)).doubleValue();
                n++;
            }
        }
        return sum / n;
    }

    @Override
    public Comparable minColumn(String label) {
        column(label);
        Class<?> classe = checkingComparable(label);
        Comparable min = (Comparable) data.get(label).get(0);
        for (int i = 1; i < data.get(label).size(); i++) {
            if (!(data.get(label).get(i).getClass().equals(classe))) {
                throw new IllegalArgumentException(data.get(label).get(i) + "is not Comparable !");
            }
            Comparable currentElt = (Comparable) data.get(label).get(i);
            min = currentElt.compareTo(min) == -1 ? currentElt : min;
        }
        return min;
    }

    @Override
    public Comparable maxColumn(String label) {
        column(label);
        Class<?> classe = checkingComparable(label);
        Comparable max = (Comparable) data.get(label).get(0);
        for (int i = 1; i < data.get(label).size(); i++) {
            if (!(data.get(label).get(i).getClass().equals(classe))) {
                throw new IllegalArgumentException(data.get(label).get(i) + "is not Comparable !");
            }
            Comparable currentElt = (Comparable) data.get(label).get(i);
            max = currentElt.compareTo(max) == 1 ? currentElt : max;
        }
        return max;
    }

    @Override
    public void orderBy(String label) {
        column(label);
        checkingComparable(label);
    }

    @Override
    public Integer getMaxColumnSize() {
        return linesNumber;
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
