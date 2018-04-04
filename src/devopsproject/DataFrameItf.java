/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devopsproject;

import java.util.List;

public interface DataFrameItf {

    /**
     * Add a column to the dataframe
     * @param label the label for the  column to add
     * @param values the values for the column
     */
    public void addToColumn(String label, List values);
    
    /**
     * Deletes a column from the dataframe
     * @param label the label of the column to delete
     */
    public void deleteFromColumn(String label);
    
    /**
     * @return Prints the dataframe
     */
    public void show();
    
    /**
     * @param n the number of lines to print
     * @return Prints the first n lines of the dataframe
     */
    public void head(int n);
    
    /**
     * @param n the number of lines to print
     * @return Prints the last n lines of the dataframe
     */
    public void tail(int n);
    
    /**
     * @param label the column to select
     * @return A dataframe containing the label and the column associated
     */
    public List loc(String label);
    
    /**
     * @param labels the list of columns to select
     * @return A dataframe containing the labels and the columns associated
     */
    public DataFrameItf loc(List<String> labels);
    
    /**
     * @param labels the columns to select
     * @example loc("a","b","c") -> selects the columns labeled "a","b","c"
     * @return A dataframe containing the labels and the columns associated
     */
    public DataFrameItf loc(String ... labels);
    
    /**
     * @param labelInf the label to select from
     * @param labelSup the label to select to
     * @return A dataframe containing the labels and the columns in between them
     */
    public DataFrameItf loc(String labelInf, String labelSup);

    /**
     * 
     * @param index a line index
     * @return all the values at the line index
     */
    public DataFrameItf iloc(int index);
    
    /**
     * 
     * @param indexes a list of indexes
     * @return the values that are at the lines of indexes
     */
    public DataFrameItf iloc(List<Integer> indexes) ;
    
    /**
     * 
     * @param indexes multiple indexes
     * @example iloc(1,2,3) -> a dataframe containing the first, second and third rows
     * @return the values contained at line indexes
     */
    public DataFrameItf iloc(Integer ... indexes);
    
    /**
     * 
     * @param indexInf lower bound index
     * @param indexSup upper bound index
     * @return a dataframe containing the lines in between the indexInf and indexSup
     */
    public DataFrameItf iloc(int indexInf, int indexSup);
    
    /**
     * 
     * @param label the label of the column to calculate the mean on
     * @return the mean on the column
     */
    public Integer meanColumn(String label);
    
    /**
     * 
     * @param label the label of the column to calculate the min on
     * @return the minimum of the column
     */
    public Integer minColumn(String label);
    
    /**
     * 
     * @param label the label of the column to calculate the max on
     * @return the maximum of the column
     */
    public Integer maxColumn(String label);
    
}
