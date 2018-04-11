package testsPackage;

import devopsproject.DataFrame;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.Rule;

public class TestsDataframe {
    
    DataFrame df;
    
    @Rule
    public TemporaryFolder tf = new TemporaryFolder();
    
    public TestsDataframe() {
        
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
        
    }
    
    @Test
    public void dataframeCreationEmptyConstructor(){
        df = new DataFrame();
    }
    
    @Test
    public void dataframeCreationLists(){
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
        
        df = new DataFrame(labels, elements);
    }
    
    @Test
    public void dataframeCreationFile() throws IOException, Exception{
        File fileTest = tf.newFile("file1.csv");
        fileTest.deleteOnExit();
        assertTrue(fileTest.exists());
    }
}
