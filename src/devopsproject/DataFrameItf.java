/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.devopsproject;

import java.util.ArrayList;

public interface DataFrameItf {
    
    /**
     * @return Prints the dataframe
     */
    public void show();
    
    /**
     * @return Prints the first n lines of the dataframe
     */
    public void head(int n);
    
    /**
     * @return Prints the last n lines of the dataframe
     */
    public void tail(int n);
    
    /**
     * @return A dataframe containing the label and the column associated
     */
    public DataFrameItf loc(String label);
    
    /**
     * @return A dataframe containing the labels and the columns associated
     */
    public DataFrameItf loc(ArrayList<String> labels);
    
    /**
     * @return A dataframe containing the labels and the columns in between
     */
    public DataFrameItf loc(String labelInf, String labelSup);
    
    // ILOC NEEDED
    
}
