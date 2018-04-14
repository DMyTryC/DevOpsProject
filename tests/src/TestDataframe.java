package src;

import devopsproject.DataFrame;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TestDataframe {
    
    DataFrame dfArray;
    List<DataFrame> dfFileList;
    DataFrame dfBase;
    
    public TestDataframe() {
        /* Test base constructor dataframe creation */
        dfBase = new DataFrame();
       
        /* Test array constructor dataframe creation */
        String [] labels = new String[3];
        labels[0] = String.valueOf('a');
        labels[1] = String.valueOf('b');
        labels[2] = String.valueOf('c');
        
        List<String> element1 = new ArrayList<>();
        element1.add("s1");element1.add("s2");element1.add("s3");

        List<Integer> element2 = new ArrayList<>();
        element2.add(1);
        element2.add(2);
        element2.add(3);

        List<Float> element3 = new ArrayList<>();
        element3.add((float) 1.4);
        element3.add((float) 2.4);
        element3.add((float) 3.5);
        
        List <List> elements = new ArrayList<>();
        elements.add(element1);
        elements.add(element2);
        elements.add(element3);
        
        dfArray = new DataFrame(labels, elements);

        /* Test file constructor dataframe creation */
        dfFileList = new ArrayList<>();
        dfFileList.add(new DataFrame("test.csv", ","));
        dfFileList.add(new DataFrame("test1.csv", ","));
        dfFileList.add(new DataFrame("test2.csv", ","));
        dfFileList.add(new DataFrame("file1.csv", ","));
    }
    
    @Test
    public void showTest() {
        /* Test show of base dataframe */
        dfBase.show();
        
        /* Test show of dataframe created from array */
        dfArray.show();
        
        /* Test show of dataframe created from file */
        for(DataFrame dfObject : dfFileList){
            dfObject.show();
        }
    }
    
    @Test
    public void headTestGoodValues(){
        dfBase.head(0);
        dfBase.head(dfBase.getMaxColumnSize()/2);

        dfArray.head(0);
        dfArray.head(dfBase.getMaxColumnSize()/2);
        
        for (DataFrame dfObject : dfFileList){
            dfObject.head(0);
            dfObject.head(dfBase.getMaxColumnSize()/2);
        }
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void headTestNegativeValues(){
        dfBase.head(-1);
        
        dfArray.head(-1);
        
        for (DataFrame dfObject : dfFileList){
            dfObject.head(-1);
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void headTestBiggerThanSize(){
        dfBase.head(Integer.MAX_VALUE);
        
        dfArray.head(Integer.MAX_VALUE);
        
        for (DataFrame dfObject : dfFileList){
            dfObject.head(Integer.MAX_VALUE);
        }
    }
}
