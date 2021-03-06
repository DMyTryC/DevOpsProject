package devopsproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataFrame implements DataFrameItf{

    private int linesNumber; // Stores size of the greatest column
    private List<String> orderedLabels; // Stores labels positionning to print columns in the same order as th one given by the constructor
    private HashMap<String, Integer> indexLabels; // Enables to retrieve label/column position
    private TreeMap<String, List> data; // Map : label -> data ordered by labels positions

    // Comparator to sort labels by their position
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

    public DataFrame(String nameFile, String separator) throws IOException {
        this();
        FileReader fr ;
        BufferedReader br;
        String extension;
        List donnees;
        String[] values;
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]");
        Matcher matcher;
        extension = nameFile.substring(nameFile.lastIndexOf(".") + 1);
        if (!extension.equals("csv")) {
            throw new IOException("The file extension is incorrect, please give a .csv file");
        } else {
            try {
                fr = new FileReader(nameFile);
                br = new BufferedReader(fr);
                String linea;
                linea = br.readLine();
                String[] labels = linea.split(separator, -1);
                for (int j = 0; j < labels.length; j++) {
                    donnees = new ArrayList<>();
                    this.orderedLabels.add(labels[j]);
                    this.indexLabels.put(labels[j], j);
                    this.data.put(labels[j], donnees);
                }

                // Storage of the first line and inference of the class/type of all elements
                String lineaType = br.readLine();
                String[] firstElement = lineaType.split(separator, -1);
                String elementString;

                for (int j = 0; j < firstElement.length; j++) {
                    matcher = pattern.matcher(firstElement[j]);
                    if (!matcher.find()) {
                        throw new NumberFormatException("The first line cannot have null values");
                    }

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
                // Storage of the remaining lines, inference of the class/type of all elements and comparison with the classes of the elements of the first line
                while ((linea = br.readLine()) != null) {
                    values = linea.split(separator, -1);
                    for (int i = 0; i < labels.length; i++) {
                        donnees = this.data.get(labels[i]);

                        try {
                            Integer op1 = Integer.parseInt(values[i]);
                            if (op1.getClass().equals(donnees.get(0).getClass())) {
                                donnees.add(op1);
                            } else {
                                throw new IllegalArgumentException("The data at the label " + "'" + labels[i] + "'" + " don't have a good type. Line : " + linea + " Data : " + op1);
                            }

                        } catch (NumberFormatException e1) {
                            try {
                                Float op2 = Float.parseFloat(values[i]);
                                if (op2.getClass().equals(donnees.get(0).getClass())) {
                                    donnees.add(op2);
                                } else {
                                    throw new IllegalArgumentException("The data at the label " + "'" + labels[i] + "'" + " don't have a good type. Line : " + linea + " Data : " + op2);
                                }
                            } catch (NumberFormatException e2) {
                                String op3 = values[i];

                                matcher = pattern.matcher(values[i]);

                                if (matcher.find()) {
                                    if (op3.getClass().equals((donnees.get(0).getClass()))) {
                                        donnees.add(op3);
                                    } else {
                                        throw new IllegalArgumentException("The data at the label " + "'" + labels[i] + "'" + " don't have a good type. Line : " + linea + " Data : " + op3);
                                    }

                                } else {

                                    donnees.add(op3);
                                }

                            }
                        }
                    }
                }

                // Storage of the size of the greatest column
                for (Map.Entry<String, List> entry : data.entrySet()) {

                    linesNumber = Math.max(linesNumber, entry.getValue().size());
                }

                fr.close();

            } catch (FileNotFoundException e) {
                System.out.println("Error: The file wasn't found");
                System.out.println(e.getMessage());
            } catch (IOException type) {
                System.err.print(type);
            }
        }
    }

    /**
     * Prints the elements from the dataframe from the "deb" index to the "n" index
     * @param deb The beginning index
     * @param n The ending index
     */
    private void print(int deb, int n) {
        int l = deb;
        showLabels();
        while (l < n) {
            String values = "";
            for (Map.Entry<String, List> entry : data.entrySet()) {
                values = values + " | "
                        + (l < entry.getValue().size() && entry.getValue().get(l) != null
                        ? entry.getValue().get(l).toString() : " ");
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

    /**
     * An enumeration of the printing type
     */
    private enum PrintingType {
        HEAD, TAIL
    };

    /**
     * Checks if the line number is correct and gives the lines to print in the dataframe
     * @param n The line number to check
     * @param type The printing type
     * @return The lines to print
     */
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

    @Override
    public void head(String label, int n) {
        column(label);
        System.out.println(label + " : " + data.get(label).subList(0, checkingLinesNumber(n, PrintingType.HEAD)));
    }

    @Override
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
            for (Object object : entry.getValue()) {
                if (object != null) {
                    size++;
                }
            }
        }
        return size;
    }

    /**
     * Gives the column that is associated with the label
     * @param label the label for which to get the column  
     * @return The column associated with the label
     */
    private List column(String label) {
        try {
            data.containsKey(label);
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException("Label " + label + " does not exist !");
        }
        return data.get(label);
    }

    @Override
    public DataFrame loc(String label) {
        List<List> elements = new ArrayList<>();
        elements.add(new ArrayList<>(column(label)));
        String[] labels = {label};
        return new DataFrame(labels, elements);
    }

    @Override
    public DataFrame loc(List<String> labels) {
        List<List> elements = new ArrayList<>(labels.size());
        for (String label : labels) {
            elements.add(new ArrayList<>(column(label)));
        }
        String[] labelsArray = new String[labels.size()];
        return new DataFrame(labels.toArray(labelsArray), elements);
    }

    @Override
    public DataFrame loc(String labelInf, String labelSup) {

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
    public DataFrame loc(String... labels) {
        List<String> labelsList = new ArrayList<>(labels.length);
        labelsList.addAll(Arrays.asList(labels));
        return loc(labelsList);
    }
    
    /**
     * Initialize a DataFrame in order to assign lines to it
     * @param linesNumber the number of selected lines
     * @return a copy of this Dataframe with "linesNumber" lines
     */
    private DataFrame initDataFrameBeforeSelectingLines(int linesNumber) {
        DataFrame df = new DataFrame();
        df.linesNumber = linesNumber;
        df.orderedLabels = new ArrayList<>(orderedLabels);
        df.indexLabels = new HashMap<>(indexLabels);
        df.data = new TreeMap<>(data);
        return df;
    }
    
    /**
     * Checks if the index > 0 and < linesNumber
     * @param idex The index which must be checked
     */
    private void checkingIndex(int index) {
        if (index > linesNumber) {
            throw new IllegalArgumentException("index > Number of lines of DataFrame !");
        }
        if (index < 0) {
            throw new IllegalArgumentException("index < 0 !");
        }
    }

    @Override
    public DataFrame iloc(int index) {
        checkingIndex(index);
        DataFrame df = initDataFrameBeforeSelectingLines(1);
        for (String label : orderedLabels) {
            df.data.replace(label, new ArrayList(Arrays.asList(data.get(label).get(index))));
        }
        return df;
    }

    /**
     * For each index of a List of indexes, maps its corresponding element
     * @param indexes List of indexes of the elements we want to retrieve
     * @param elements List of Objects containing the elements we want to retrieve
     * @return List of wanted elements
     */
    private List indexToElement(List<Integer> indexes, List elements) {
        return indexes.stream().map(new Function<Integer, Object>() {
            @Override
            public Object apply(Integer index) {
                return elements.get(index);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public DataFrame iloc(List<Integer> indexes) {
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
    public DataFrame iloc(Integer... indexes) {
        for (Integer index : indexes) {
            checkingIndex(index);
        }
        DataFrame df = initDataFrameBeforeSelectingLines(indexes.length);
        for (String label : orderedLabels) {
            df.data.replace(label, indexToElement(Arrays.asList(indexes), data.get(label)));
        }
        return df;
    }

    @Override
    public DataFrame iloc(int indexInf, int indexSup) {
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
            df.data.replace(label, IntStream.range(inf, sup + 1).boxed().map(new Function<Integer, Object>() {

                @Override
                public Object apply(Integer index) {
                    return data.get(label).get(index);
                }
            }).collect(Collectors.toList()));
        }
        return df;
    }

    /**
     * Checks if the first element is a number
     * @param label The label for which to check the number format
     */
    private void checkingNumberFormat(String label) {
        try {
            if (!(data.get(label).get(0) instanceof Number)) {
                throw new IllegalArgumentException("Column at Label " + label + " is not Numeric !");
            }
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException("Label " + label + " does not exist !");
        }
    }

    /**
     * Checks if the label is comparable
     * @param label The label to check for
     * @return The class of the first element of the column
     */
    private Class<?> checkingComparable(String label) {
        try {
            if (!(data.get(label).get(0) instanceof Comparable)) {
                throw new IllegalArgumentException("Column at Label " + label + " is not Comparable !");
            }
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException("Label " + label + " does not exist !");
        }
        return data.get(label).get(0).getClass();
    }

    @Override
    public void showStatitic(String label) {
        System.out.println("Label : " + label);
        System.out.println("Mean : " + meanColumn(label));
        System.out.println("Minimum : " + minColumn(label));
        System.out.println("Maximum : " + maxColumn(label));
    }

    @Override
    public Float meanColumn(String label) {
        column(label);
        checkingNumberFormat(label);
        List donnee;
        float mean = 0;
        int noNonNull = 0;
        Float num;
        for (Map.Entry<String, List> entry : this.data.entrySet()) {
            if (label.equals(entry.getKey())) {
                donnee = entry.getValue();
                for (int i = 0; i < donnee.size(); i++) {
                    if (!donnee.get(i).toString().equals("") && !donnee.get(i).toString().equals(" ")) {
                        num = new Float(donnee.get(i).toString());
                        mean = mean + num;
                        noNonNull ++;
                    }
                }
                mean = mean / noNonNull;
            }
        }
        return mean;
    }
    
    /*@Override
    public Double meanColumn(String label) {
        column(label);
        checkingNumberFormat(label);
        int n = 1;
        Double sum = ((Number) data.get(label).get(0)).doubleValue();
        for (int i = 1; i < data.get(label).size(); i++) {
            if (data.get(label).get(i) != null) {
                sum += ((Number) data.get(label).get(i)).doubleValue();
                n++;
            }
        }
        return sum / n;
    } */ 
    

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
    public Integer getMaxColumnSize() {
        return linesNumber;
    }

    @Override
    public void orderBy(String label) {
        checkingComparable(label) ;
    }

    @Override
    public TreeMap<String, List> groupby(String[] labels) {
        TreeMap<String, List> dataReturnd = new TreeMap<>();
        List listReturnd;
        for (int i = 0; i < labels.length; i++) {
            listReturnd = groupby(labels[i]);
            dataReturnd.put(labels[i], listReturnd);
        }
        return dataReturnd;
    }

    /*
       groupby one column, it takes the label as param then it returns the list 
     */
    @Override
    public List groupby(String label) {
        List dataReturnd = new ArrayList();
        List data = this.data.get(label);
        for (int i = 0; i < data.size(); i++) {
            if (!dataReturnd.contains(data.get(i))) {
                dataReturnd.add(data.get(i));
            }
        }
        return dataReturnd;
    }


    /*
          it takes the label and the aggreagate as an optional param "{max, sum} of column"
     */
    @Override
    public TreeMap<String, Object> groupby(String label, Optional<String> aggregate) {
        TreeMap<String, Object> dataReturnd = new TreeMap<>();
        List data = this.data.get(label);
        if (aggregate.isPresent()) {
            switch (aggregate.get()) {
                case "sum":
                    dataReturnd.put(aggregate.get() + "(" + label + ")", sum(data));
                    break;
                case "max":
                    dataReturnd.put(aggregate.get() + "(" + label + ")", Collections.max(data));
                    break;
                default:
                    throw new IllegalArgumentException("The aggregate : " + aggregate + " doesn't exist");
            }
        }
        return dataReturnd;
    }

    /*
        it takes a list of labels and aggreagate as optional (just count)
     */
    @Override
    public TreeMap<String, List> groupby(String[] labels, String aggregate) {
        TreeMap<String, List> dataReturned = new TreeMap<>();
        List listReturnd;
        List labelListData;
        List aggregateList;
        if (aggregate.equals("count")) {
            for (int i = 0; i < labels.length; i++) {
                labelListData = this.data.get(labels[i]);
                aggregateList = new ArrayList<>();
                listReturnd = new ArrayList();
                for (int j = 0; j < labelListData.size(); j++) {
                    if (!listReturnd.contains(labelListData.get(i))) {
                        listReturnd.add(labelListData.get(i));
                        aggregateList.add(count(labelListData, labelListData.get(i)));
                    }
                }
                dataReturned.put(labels[i], listReturnd);
                dataReturned.put(aggregate + "(" + labels[i] + ")", aggregateList);
            }
        } else {
            throw new IllegalArgumentException("The aggregate : " + aggregate + " doesn't exist");
        }
        return dataReturned;
    }
    
    @Override
    public int count(List list, Object element) {
        int cpt = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(element)) {
                cpt++;
            }
        }
        return cpt;
    }
    
    /**
     * @param list List of Numbers
     * @return The sum of the Numbers of the List
     */
    private Object sum(List list) {
        if (list.get(0) instanceof Integer) {
            int somme = 0;
            for (int i = 0; i < list.size(); i++) {
                somme = somme + (Integer) list.get(i);
            }
            return somme;
        } else {
            if (list.get(0) instanceof Float) {
                float somme = 0;
                for (int i = 0; i < list.size(); i++) {
                    somme = somme + (Float) list.get(i);
                }
                return somme;
            } else {
                throw new IllegalArgumentException("Cannot aggregate a string");
            }
        }
    }
}
