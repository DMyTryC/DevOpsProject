/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devopsproject;

import java.util.ArrayList;

public class DevOpsProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String [] labels = new String[3];
        labels[0] = String.valueOf('a');
        labels[1] = String.valueOf('b');
        labels[2] = String.valueOf('c');

        ArrayList<String> element1 = new ArrayList<String>();
        element1.add("s1");element1.add("s2");element1.add("s3");

        ArrayList<Integer> element2 = new ArrayList<Integer>();
        element2.add(1);element2.add(2);element2.add(3);

        ArrayList<Float> element3 = new ArrayList<Float>();
        element3.add((float) 1.4);element3.add((float) 2.4);element3.add((float) 3.5);

        ArrayList<ArrayList> elements = new ArrayList<ArrayList>();
        elements.add(element1);elements.add(element2);elements.add(element3);

        DataFrame dataFrame = new DataFrame(labels,elements);

    }
    
}
