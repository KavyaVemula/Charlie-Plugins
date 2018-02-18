/*
 * This is a test suite for the Advisor Plugin
 * 
 */

import charlie.bs.section1.Test00_12_2;
import charlie.bs.section1.Test00_12_7;
import charlie.bs.section1.Test01_12_2;
import charlie.bs.section1.Test01_12_7;
import charlie.bs.section2.Test00_5_2;
import charlie.bs.section2.Test00_5_7;
import charlie.bs.section2.Test01_5_2;
import charlie.bs.section2.Test01_5_7;
import charlie.bs.section3.Test00_A2_2;
import charlie.bs.section3.Test00_A2_7;
import charlie.bs.section3.Test01_A2_2;
import charlie.bs.section3.Test01_A2_7;
import charlie.bs.section4.Test00_22_2;
import charlie.bs.section4.Test00_22_7;
import charlie.bs.section4.Test01_22_2;
import charlie.bs.section4.Test01_22_7;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author kavyareddy
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
Test00_12_2.class,
Test01_12_2.class,
Test00_12_7.class,
Test01_12_7.class,
Test00_5_2.class,
Test01_5_2.class,
Test00_5_7.class,
Test01_5_7.class,
Test00_A2_2.class,
Test01_A2_2.class,
Test00_A2_7.class,
Test01_A2_7.class,
Test00_22_2.class,
Test01_22_2.class,
Test00_22_7.class,
Test01_22_7.class


})
public class TestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
