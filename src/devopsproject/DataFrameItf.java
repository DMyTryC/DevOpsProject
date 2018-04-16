package devopsproject;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

public interface DataFrameItf {
    
    /**
     * Prints the dataframe
     */
    public void show();
    
    /**
     * Prints the first n lines of the dataframe
     * @param n The number of lines to print
     */
    public void head(int n);
    
    /**
     * Prints the first n lines of the dataframe for the label
     * @param label The label for which to show the dataframe
     * @param n The number of lines to print
     */
    public void head(String label, int n);
    
    /**
     * Prints the last n lines of the dataframe
     * @param n The number of lines to print
     */
    public void tail(int n);
    
    /**
     * Prints the last n lines of the dataframe for the label
     * @param label The label for which to show the dataframe
     * @param n The number of lines to print
     */
    public void tail(String label, int n);
    
    /**
     * Selects a subset of the dataframe that is associated with the column "label"
     * @param label The column to select
     * @return A dataframe containing the label and the column associated
     */
    public DataFrame loc(String label);
    
    /**
     * Selects a subset of the dataframe that is associated with the columns that are in the list "labels"
     * @param labels The list of columns to select
     * @return A dataframe containing the labels and the columns associated
     */
    public DataFrameItf loc(List<String> labels);
    
    /**
     * Selects a subset of the dataframe that is associated with the columns "labels"
     * Note : If only two strings are given, the loc(labelInf, labelSup) will be executed instead of this one
     * @param labels The columns to select
     * @example loc("a","b","c") -> selects the columns labeled "a","b","c"
     * @return A dataframe containing the labels and the columns associated
     */
    public DataFrameItf loc(String ... labels);
    
    /**
     * Selects a subset of the dataframe that is associated with the columns in between the inferior and the superior label
     * @param labelInf The label to select from
     * @param labelSup The label to select to
     * @return A dataframe containing the labels and the columns in between them
     */
    public DataFrameItf loc(String labelInf, String labelSup);
    


    /**
     * Selects the lines of the dataframe that are at the index
     * @param index A line index
     * @return All the values at the line index
     */
    public DataFrameItf iloc(int index);
    
    /**
     * Selects the lines of the dataframe that are at the indexes
     * @param indexes A list of indexes
     * @return The values that are at the lines of indexes
     */
    public DataFrameItf iloc(List<Integer> indexes) ;
    
    /**
     * Selects the lines of the dataframe that are at the indexes
     * Note : If only two strings are given, the iloc(indexInf, indexSup) will be executed instead of this one
     * @param indexes Multiple indexes
     * @example iloc(1,2,3) -> a dataframe containing the first, second and third rows
     * @return The values contained at line indexes
     */
    public DataFrameItf iloc(Integer ... indexes);
    
    /**
     * Selects the lines of the dataframe that are in between the inferior and the superior index
     * @param indexInf Lower bound index
     * @param indexSup Upper bound index
     * @return A dataframe containing the lines in between the indexInf and indexSup
     */
    public DataFrameItf iloc(int indexInf, int indexSup);
    
    /**
     * Calculates the mean of the column at the label "label" 
     * @param label The label of the column to calculate the mean on
     * @return The mean on the column
     */
    public Float meanColumn(String label);
    
    /**
     * Finds the minimum of the column at the label "label" 
     * @param label The label of the column to calculate the min on
     * @return The minimum of the column
     */
    public Comparable minColumn(String label);
    
    /**
     * Finds the maximum of the column at the label "label"
     * @param label The label of the column to calculate the max on
     * @return The maximum of the column
     */
    public Comparable maxColumn(String label);
    
    /**
     * Shows the labels
     */
    public void showLabels();
    
    /**
     * Finds the size of the dataframe
     * @return The size of the dataframe
     */
    public int size();
    
    /**
     * Groups the information by the labels
     * @param labels The labels to group by
     * @return A treemap containing the information associated to the labels
     */
    public TreeMap<String, List> groupby(String[] labels);
    
    /**
     * Groups the information with a label
     * @param label The label to group by
     * @return A list containing the information associated to the label
     */
    public List groupby(String label);
    
    /**
     * Groups the information with the labels and aggregates it
     * Aggregate methods : count
     * @param labels The labels to group by
     * @param aggregate The aggregation method 
     * @return A treemap containing the information associated to the label, that has been aggregated
     */
    public TreeMap<String, List> groupby(String[] labels, String aggregate);
    
    /**
     * Groups the information with the labels and aggregates it
     * Aggregate methods : sum, max
     * @param labels The labels to group by
     * @param aggregate The aggregation method 
     * @return A treemap containing the information associated to the label, that has been aggregated
     */
    public TreeMap<String, Object> groupby(String label, Optional<String> aggregate);
    
    /**
     * Shows all the statistics (mean, min, max) for the label
     * @param label The label to show the statistics for
     */
    public void showStatitic(String label);
    
    /**
     * Gives the maximum column size
     * @return The maximum column size
     */
    public Integer getMaxColumnSize();
    
    /**
     * Counts the occurences of the element in the list
     * @param list The list to verify the occurences in
     * @param element The element to check the occurences for
     * @return The number of the occurences
     */
    public int count(List list, Object element);
    
}
