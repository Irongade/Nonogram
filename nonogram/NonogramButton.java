package nonogram;

import javax.swing.*;

/**
 * A Single Nonogram Button in the JFrame GUI.
 * 
 * @author Ayangade Adeoluwa
 * @version January 2023
 */
public class NonogramButton extends JButton {
    // fixed height and width for buttons
    private static final int WIDTH = 400;
    private static final int HEIGHT = 50;

    /**
	 * Create the button with name and size
	 * 
	 * @param btnName the name of the button
	 */
    public NonogramButton(String btnName) {
        super(btnName);
        super.setSize(WIDTH, HEIGHT);
    }
}
