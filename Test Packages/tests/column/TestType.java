/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.column;

import devopsproject.DataFrame;
import devopsproject.DataFrameItf;
import org.junit.Before;
import org.junit.Test;

public class TestType {
    
    private static DataFrameItf dataFrame ;
    
    public TestType() {
	
    }
    
    @Before
    public void setUp() {
	dataFrame = new DataFrame() ;
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test(expected = IndexOutOfBoundsException.class)
    public void testForException() {
	dataFrame.iloc(0) ;
    }
    
}
