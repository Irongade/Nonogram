package nonogram;

import javax.swing.*;

/**
 * A Single Nonogram NGPattern Label cell in the JFrame GUI.
 * This cell is used to display the NGPatterns for each row and column
 * 
 * @author Ayangade Adeoluwa
 * @version January 2023
 */
public class NGPatternLabelCell extends JLabel {
    /**
	 * Constructor for the NG pattern label cell 
	 * 
	 * @param num the text stored in the label
	 */
    public NGPatternLabelCell(String num) {
        super(num);

        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
    }

    /**
	 * method to set the label text. 
	 * 
	 * @param val the text stored in the label
	 */
    public void setValue(String val) {
        this.setText(val);
    }

    /**
	 * method to set the label text. 
	 * 
	 * @return the text stored in the label
	 */
    public String getValue() {
        return this.getText();
    }
}
