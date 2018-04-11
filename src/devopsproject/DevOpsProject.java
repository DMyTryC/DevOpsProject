/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devopsproject;

import java.util.ArrayList;
import java.util.List;

public class DevOpsProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String [] labels = new String[3];
        labels[0] = String.valueOf('a');
        labels[1] = String.valueOf('b');
        labels[2] = String.valueOf('c');

        List<String> element1 = new ArrayList<>();
        element1.add("s1");element1.add("s2");element1.add("s3");

        List<Integer> element2 = new ArrayList<>();
        element2.add(1);element2.add(2);element2.add(3);

        List<Float> element3 = new ArrayList<>();
        element3.add((float) 1.4);element3.add((float) 2.4);element3.add((float) 3.5);

        List<List> elements = new ArrayList<>();
        elements.add(element1);elements.add(element2);elements.add(element3);

        DataFrame dataFrame = new DataFrame(labels,elements);
    }
    
}
