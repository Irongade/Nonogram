package nonogram;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class NonogramButtonTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class NonogramButtonTest
{
    
    NonogramButton btn = null;
    
    /**
     * Default constructor for test class NonogramButtonTest
     */
    public NonogramButtonTest()
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
        btn = new NonogramButton("Test");
    }

    @Test
    public void testConstructor() {
        assertNotNull(btn);
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
