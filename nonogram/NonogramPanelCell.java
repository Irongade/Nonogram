package nonogram;
import javax.swing.*;
import java.awt.*;

/**
 * A Single Nonogram game cell in the JFrame GUI.
 * 
 * @author Ayangade Adeoluwa
 * @version January 2023
 */
public class NonogramPanelCell extends JLabel {

    private int row;
    private int col;
    private int value;

    // icons used in each cell
    private ImageIcon filled = null;
    private ImageIcon close = null;

    // icon cell paths.
    private static final String FILLED = "nonogram/assets/filled.png";
    private static final String CLOSE = "nonogram/assets/close.png";
    

    /**
	 * Constructor
	 * 
	 * @param r the row that this cell corresponds to in the game cell matrix
	 * @param c the col that this cell corresponds to in the game cell matrix
     * 
	 * @param cellWidth the width of each cell 
	 * @param cellHeight the height of each cell
     *        The height and width of each cell is dynamically generated based on the difficulty of the game (number of rows and columns of the game)
	 */
    public NonogramPanelCell(int r, int c, int cellWidth, int cellHeight) {
        super();
        row = r;
        col = c;
        // initial value is unknown
        value = Nonogram.UNKNOWN;

        // setting up the JLabel.
        this.setPreferredSize(new Dimension(cellWidth, cellHeight));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setOpaque(true);
        
        // creating the image files
        filled = new ImageIcon(new ImageIcon(FILLED).getImage().getScaledInstance(cellWidth - 10, -1, Image.SCALE_SMOOTH));
        close = new ImageIcon(new ImageIcon(CLOSE).getImage().getScaledInstance(cellWidth - 10, -1, Image.SCALE_SMOOTH));
    }

    /**
	 * Retrieve the row for this particular cell
	 * 
	 * @return the cell row
	 */
    public int getRow() {
        return row;
    }

    /**
	 * Retrieve the column for this particular cell
	 * 
	 * @return the cell column
	 */
    public int getCol() {
        return col;
    }

  /**
	 * Retrieve the value for this particular cell
	 * 
	 * @return the cell value
	 */
    public int getValue() {
        return value;
    }

    /**
   * Set the background color for this cell.
   * 
   * @param color the new color for the cell
   */
    public void setBg(Color color) {
        this.setBackground(color);
    } 

     /**
   * Set the label text for the JLabel.
   * 
   * @param val the new cell value as a string
   */
    public void setValue(String val) {
        if (val == null) {
            throw new NullPointerException();
        }
        this.setText(val);
    }

     /**
   * Set the icon value for the JLabel.
   * 
   * @param val the new cell value
   */
    public void setValue(int val) {
        value = val;

        // if the current value is empty - set the icon to close icon, if it is full, set the icon to filled icon, and remove any icons if null
        if (value == Nonogram.EMPTY) {
            this.setIcon(close);
        } else if (value == Nonogram.FULL) {
            this.setIcon(filled);
        } else {
            this.setIcon(null);
        }
    }
}
