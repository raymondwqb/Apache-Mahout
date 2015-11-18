package com.mahout.clustering;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple kmeans.
 */
public class FuzzykmeansTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FuzzykmeansTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( FuzzykmeansTest.class );
    }

    /**
     * Rigorous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
