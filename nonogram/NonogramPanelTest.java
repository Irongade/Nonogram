package nonogram;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class NonogramPanelTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class NonogramPanelTest
{
    
    NonogramPanel game = null;
    
    /**
     * Default constructor for test class NonogramPanelTest
     */
    public NonogramPanelTest()
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
        game = new NonogramPanel();
    }
    
     
   @Test
    public void testConstructor() {
        assertNotNull(game);
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
