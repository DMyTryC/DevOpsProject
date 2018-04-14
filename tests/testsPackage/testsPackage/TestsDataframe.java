package testsPackage;

import devopsproject.DataFrame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("rawtypes")
public class TestsDataframe {

    DataFrame df;

    public TestsDataframe() {

    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void dataframeCreationEmptyConstructor() {
        df = new DataFrame();
    }

    @Test
    public void dataframeCreationLists() {
        String[] labels = new String[3];
        labels[0] = String.valueOf('a');
        labels[1] = String.valueOf('b');
        labels[2] = String.valueOf('c');

        List<String> element1 = new ArrayList<>();
        element1.add("s1");
        element1.add("s2");
        element1.add("s3");

        List<Integer> element2 = new ArrayList<>();
        element2.add(1);
        element2.add(2);
        element2.add(3);

        List<Float> element3 = new ArrayList<>();
        element3.add((float) 1.4);
        element3.add((float) 2.4);
        element3.add((float) 3.5);

        List<List> elements = new ArrayList<>();
        elements.add(element1);
        elements.add(element2);
        elements.add(element3);

        df = new DataFrame(labels, elements);
        df.head(2);
        System.out.println("");
        df.show();
        System.out.println("");
        df.tail(2);
    }

    @Test
    public void dataframeCreationFile() throws IOException {
        df = new DataFrame("tests/testsPackage/resources/file1.csv", ",");
        df.head(2);
        System.out.println("");
        df.show();
        System.out.println("");
        df.tail(2);
    }
}
