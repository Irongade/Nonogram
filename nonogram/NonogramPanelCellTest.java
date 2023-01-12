package nonogram;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
/**
 * The test class NonogramPanelCellTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class NonogramPanelCellTest
{
    NonogramPanelCell cell = null;
    /**
     * Default constructor for test class NonogramPanelCellTest
     */
    public NonogramPanelCellTest()
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
         cell = new NonogramPanelCell(1,1,30,30);
    }
    
    @Test
    public void testConstructor() {
        assertNotNull(cell);
    }
    
      @Test
    public void testGetRow() {    
        int row = cell.getRow();       
        assertEquals(1, row);
    }

    @Test
    public void testGetCol() {    
        int col = cell.getCol();       
        assertEquals(1, col);
    }
    
    @Test
    public void testGetInitialValue() {    
        int value = cell.getValue();       
        assertEquals(2, value);
    }
    
    @Test
    public void testSetBackgroundColor() {    
        cell.setBg(Color.WHITE); 
        
        assertEquals(Color.WHITE, cell.getBackground());
    }
    
      public void testSetBackgroundColorFail() {    
        cell.setBg(Color.RED); 
        
        assertNotEquals(Color.WHITE, cell.getBackground());
    }
    
    @Test
    public void testSetLabelValue() {    
        cell.setValue("5"); 
        
        assertEquals("5", cell.getText());
    }
    
    @Test
    public void testSetLabelIcon() {    
        cell.setValue(1); 
        
        assertNotNull(cell.getIcon());
    }
    
    @Test
    public void testSetNullValue() {    
        try {
            cell.setValue(null);
            fail("Expected Null pointerExceptionToBeThrown");
        } catch (NullPointerException e) {
            assertTrue(true);
        }
        cell.setValue(1); 
        
        assertNotNull(cell.getIcon());
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
