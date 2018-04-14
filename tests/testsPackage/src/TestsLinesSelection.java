/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import devopsproject.DataFrame;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author bap
 */
public class TestsLinesSelection {
    
    DataFrame df ;
    
    public TestsLinesSelection() {
        df = new DataFrame("tests/resources/test1.csv", ",") ;
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // Test Columns Selection
    
    @Test
    public void ilocV1 () {
        System.out.println("ILOCV1 :");
        System.out.println("-----------------------------");
        df.iloc(5).show();
        System.out.println("-----------------------------");
    }
    
    @Test
    public void ilocV2 () {
        System.out.println("ILOCV2 :");
        System.out.println("-----------------------------");
        df.iloc(new ArrayList<>(Arrays.asList(5,2,3))).show();
        System.out.println("-----------------------------");
    }
    
    @Test
    public void ilocV3 () {
        System.out.println("ILOCV3 :");
        System.out.println("-----------------------------");
        df.iloc(5,2,3).show();
        System.out.println("-----------------------------");
    }
    
    @Test
    public void ilocV41 () {
        System.out.println("ILOCV41 :");
        System.out.println("-----------------------------");
        df.iloc(2,5).show();
        System.out.println("-----------------------------");
    }
    
    @Test
    public void ilocV42 () {
        System.out.println("ILOCV42 :");
        System.out.println("-----------------------------");
        df.iloc(6,0).show();
        System.out.println("-----------------------------");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexOutOfBoundExceptionV1 () {
        df.iloc(10);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexOutOfBoundExceptionV2 () {
        df.iloc(new ArrayList<>(Arrays.asList(5,2,10)));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexOutOfBoundExceptionV3 () {
        df.iloc(5,2,10);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexOutOfBoundExceptionV41 () {
        df.iloc(0,10);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ilocIndexOutOfBoundExceptionV42 () {
        df.iloc(10,0);
    }
}
