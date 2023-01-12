package nonogram;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class NGPatternLabelCellTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class NGPatternLabelCellTest
{
    
    NGPatternLabelCell cell = null;
    
    /**
     * Default constructor for test class NGPatternLabelCellTest
     */
    public NGPatternLabelCellTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        cell = new NGPatternLabelCell("4");
    }

        @Test
    public void testConstructor() {
        assertNotNull(cell);
    }
    
      @Test
    public void testGetText() {    
        String val = cell.getValue();       
        assertEquals("4", val);
    }
    
      @Test
    public void testSetText() {  
        cell.setValue("10");
        String val = cell.getValue();       
        assertEquals("10", val);
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}
