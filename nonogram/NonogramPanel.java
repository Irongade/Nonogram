package nonogram;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 * The Nonogram GUI Interface.
 * 
 * Game play idea inspired from here https://www.puzzle-nonograms.com/
 * 
 * @author Ayangade Adeoluwa
 * @version January 2023
 */
public class NonogramPanel extends JFrame implements Observer {

    // buttons
    private NonogramButton    undoBtn = null;
    private NonogramButton    saveBtn  = null;
    private NonogramButton    clearBtn = null;
    private NonogramButton    loadBtn = null;
    private NonogramButton    quitBtn  = null;
    private NonogramButton    difficultyBtn = null;
    private NonogramButton    helpBtn = null;
    private NonogramButton    changeNameBtn = null;

    // top level panels.
    private JPanel     btnPanel = null;
    private JPanel     gamePanel = null;
    private JPanel     topLayoutPanel = null;
    private JPanel     centerLayoutPanel = null;
    private Nonogram   game = null;
    private Stack<Assign> stack = null;

    // this scanner is specifically for reading game files.
    private Scanner       gameFileScanner = null;
    // this scanner is for reading other files.
    private Scanner      readFileScanner = null;

    // gamePanel cells
    private NonogramPanelCell[][] cells = null;
    private JPanel[][] xAxisNGPatternCells = null;
    private JPanel[][] yAxisNGPatternCells = null;

    // player 
    private String playerName = "Player";
    private JLabel nameLabel = null;
    private String difficultyLevel = "easy";

    // others
    private int cellWidthDimension = 25;
    private int cellHeightDimension = 25;
    private static final String NGFILE   = "nons/tiny.non";

        /**
	 * Constructor
	 */
    public NonogramPanel() {
        super("Nonogram");
        // this method creates the game puzzle using the Nonogram 'game' object.
        createGame(NGFILE);
        // This method makes the JFrame (GUI).
        makeFrame();
    };

        /**
   * Creates a game puzzle using the Nonogram class.
   * 
   * @param file the file location for the game settings file (non file)
   */
    private void createGame(String file) {
        stack = new Stack<Assign>();
		try {
			gameFileScanner = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
			System.out.println(file + "not found");
		}
        // start the game
        game = new Nonogram(gameFileScanner);
        // add the entire JFrame as the Observer for the game puzzle.
        game.addObserver(this);
    }

            /**
   * Creates the game JFrame (GUI).
   * 
   */
    public void makeFrame() {
        // make the buttons
        makeButtons();

        // then make the game cells
        // the game cells are made of the GUI cells and the NGPattern Label cells
        makeGameCells(cellWidthDimension, cellHeightDimension);

        // Add a name label to track player's name.
        nameLabel = new JLabel("Hello " + playerName + ",");
        nameLabel.setBorder(new EmptyBorder(10,10,10,10));
        nameLabel.setOpaque(true);
        nameLabel.setBackground(Color.WHITE);
        
        topLayoutPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 100, 0));
        centerLayoutPanel = new JPanel();

        // this panel is for the button panels and player name label.
        topLayoutPanel.add(btnPanel);
        topLayoutPanel.add(nameLabel);

        // this for the game cells panel
        centerLayoutPanel.add(gamePanel);

        // The root JFrame has two panels, one for the top layout and the center layout
        setLayout(new BorderLayout());
        add(topLayoutPanel, BorderLayout.WEST);
        add(centerLayoutPanel, BorderLayout.SOUTH);

        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

            /**
   * Makes the Nonogram GUI Buttons with their respective actionListeners.
   * 
   */
    public void makeButtons() {
        // Undo button.
        undoBtn = new NonogramButton("Undo");
        undoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                undo();
            }
        });
        
        // Clear button.
        clearBtn = new NonogramButton("Clear");
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                clear();
            }
        });

        // Save button.
        saveBtn = new NonogramButton("Save");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String file = "";
                if(difficultyLevel == "easy") {
                    file = "easyGameOutput.txt";
                } else if (difficultyLevel == "medium") {
                    file = "mediumGameOutput.txt";
                } else {
                    file = "hardGameOutput.txt";
                }
                save(file);
            }
        });
        
        // Load button.
        loadBtn = new NonogramButton("Load");
        loadBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            String file = "";
            if(difficultyLevel == "easy") {
                file = "easyGameOutput.txt";
            } else if (difficultyLevel == "medium") {
                file = "mediumGameOutput.txt";
            }  else {
                file = "hardGameOutput.txt";
            }
            load(file);
        }
        });

        // Quit button.
        quitBtn = new NonogramButton("Quit");
        quitBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            quit();
        }
        });

        // Change Diifficulty button
        difficultyBtn = new NonogramButton("Change Difficulty");
        difficultyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                changeDifficulty();
            }
        });

        // Help button.
        helpBtn = new NonogramButton("Help");
        helpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                help();
            }
        });

        // Change Name
        changeNameBtn = new NonogramButton("Change Name");
        changeNameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                changeName();
            }
        });

        // add buttons.
        btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(4, 2, 0, 2));
        btnPanel.add(undoBtn);
        btnPanel.add(difficultyBtn);
        btnPanel.add(clearBtn);
        btnPanel.add(changeNameBtn);
        btnPanel.add(saveBtn);
        btnPanel.add(helpBtn);
        btnPanel.add(loadBtn);
        btnPanel.add(quitBtn);
    }

            /**
   * Makes the Nonogram GUI Game cells with their respective Constraints.
   * 
   */
    public void makeGameCells(int cellWidth, int cellHeight) {
        // get number of rows and columns of the game.
        int rowNum = game.getNumRows();
        int colNum = game.getNumCols();

        // these variables would be used in creating grid layouts for the NGPatternLabelCells
        int maxRowNGPatternNumber = 0;
        int maxColumnNGPatternNumber = 0;

        // get max number of ngPatterns in all the row. for example NGPattern of tiny.non file is 3
        // number of rows in x-axis is dependent on number of cols in ngpattern
        for(int row = 0; row < rowNum; row++) {
            int num = game.getColNums(row).length;
            maxRowNGPatternNumber = Math.max(num, maxRowNGPatternNumber);
        }

        // get max num of ngPatterns in all the columns
        // number of cols in y-axis is dependent on number of rows in ng pattern
        for(int col = 0; col < colNum; col++) {
            int num = game.getRowNums(col).length;
            maxColumnNGPatternNumber = Math.max(num, maxColumnNGPatternNumber);
        }

        // these are the actual game cells
        cells = new NonogramPanelCell[rowNum+1][colNum+1];

        // these JPanels will hold the NGPattern nums
        // the size of both will be dynamically determined based on maxRowPattern numbers.
        xAxisNGPatternCells = new JPanel[maxRowNGPatternNumber+1][colNum+1];
        yAxisNGPatternCells = new JPanel[rowNum+ 1][maxColumnNGPatternNumber + 1];

        // gamePanel for the game cells
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());

        // the center container panel consists of the cellsPanel (for the actual game cells) and the horizontal panel for the NG Patterns on the x-axis (the patterns above the cellspanel)
        JPanel centerContainerPanel = new JPanel();
        centerContainerPanel.setLayout(new BorderLayout());

        // The left container panel houses the vertical panel for NG patterns on the y-axis
        JPanel leftContainerPanel = new JPanel();
        leftContainerPanel.setLayout(new BorderLayout());

        // we make use of grid layouts for all the sub-panels i.e. game cells, horizonal and vertical NG patterns.
        // all sub-panels use gridlayout, to ensure that all the cells are perfectly aligned.
        JPanel horizontalPatternPanel = new JPanel();
        horizontalPatternPanel.setLayout(new GridLayout(maxRowNGPatternNumber, colNum));

        JPanel verticalPatternPanel = new JPanel();
        verticalPatternPanel.setLayout(new GridLayout(0, maxColumnNGPatternNumber));

        JPanel cellsPanel = new JPanel();
        cellsPanel.setLayout(new GridLayout(rowNum + 1, colNum + 1));

        // add the main game cells - the main game cells are the cells that contain the GUI and the numbering system for the grid
        for (int row = 0; row <= rowNum; row++) {
            for (int col = 0; col <= colNum; col++) {
                NonogramPanelCell cell = new NonogramPanelCell(row, col, cellWidth, cellHeight);
                cellsPanel.add(cell);
                cells[row][col] = cell;
                // a mouselistener is used for the NonogramPanelCells so we can knownwhen a cell has been clicked or pressed.
                cell.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        play(e);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                    }
                });
            }
        }

        // NOTE: The Idea here is to first create panels, stack them in the gridLayout, then iterate over the panels and populate those panels with the right NGPatternLabelCell

        // we then create new panels to be added to the horizontal panels for NG patterns on the x-axis (above the game cells).
        // these panels will house the NGPatternLabelCell.
        for (int row = 0; row < maxRowNGPatternNumber; row++) {
            for(int col = 0; col <= colNum; col++) {
                JPanel xPanel = new JPanel();
                xPanel.setPreferredSize(new Dimension(cellWidth, cellHeight));
                horizontalPatternPanel.add(xPanel);
                xAxisNGPatternCells[row][col] = xPanel;
            }
        }

        for(int row = 0; row <= rowNum; row++) {
            for (int col = 0; col < maxColumnNGPatternNumber; col++) {
                JPanel yPanel = new JPanel();
                yPanel.setPreferredSize(new Dimension(cellWidth, cellHeight));
                verticalPatternPanel.add(yPanel);
                yAxisNGPatternCells[row][col] = yPanel;
            }
        }

        // here we then iterate from the back to populate those panels with the label cells
        // the reason we iterate backwards is because gridLayout only adds items from left to right, top to bottom.

        for(int col = colNum - 1; col >= 0; col--) {
            // get each NG column numbers and add them to each gridlayout
            int[] nums = game.getColNums(col);
            for (int row = 0; row < nums.length; row++) {
                // adding 1 since our actual game starts after index 1.
                xAxisNGPatternCells[row][col + 1].add(new NGPatternLabelCell((nums[row] + "")));
            }
        }

        for (int row = rowNum - 1; row >= 0; row--) {
            // get each NG row numbers and add them to each gridlayout
            int[] nums = game.getRowNums(row);
            for (int col = nums.length - 1; col >= 0; col--) {
                // adding 1 since our actual game starts after index 1.
                yAxisNGPatternCells[row + 1][col].add(new NGPatternLabelCell((nums[col] + "")));
            }
        }

        // the centerContainerPanel - then holds the horizontal NGPattern used above and the cellsPanel
        centerContainerPanel.add(horizontalPatternPanel, BorderLayout.NORTH);
        centerContainerPanel.add(cellsPanel, BorderLayout.CENTER);

        // this holds the vertical NGPattern
        leftContainerPanel.add(verticalPatternPanel, BorderLayout.SOUTH);

        // then we add and position both in the gamePanel.
        gamePanel.add(centerContainerPanel, BorderLayout.CENTER);
        gamePanel.add(leftContainerPanel, BorderLayout.WEST);


        // here we add numbering system (for labels used in the game from 1 to colNum).
        for (int col = 1; col <= colNum; col++) {
            cells[0][col].setValue((col - 1) + "");
        }
        for (int row = 1; row <= rowNum; row++) {
            cells[row][0].setValue((row - 1) + "");
        }
    };

            /**
   * Plays a game move.
   * 
   * @param e MouseEvent that holds the currently clicked game cell
   */
    private void play(MouseEvent e) {
        NonogramPanelCell pressed = (NonogramPanelCell) e.getSource();
        int row = pressed.getRow();
        int col = pressed.getCol();
        int value = pressed.getValue();
        int newValue;

        // we make sure that if the user clicks on the row and col labels nothing happens
        // we do this because our game starts from index 1 for both rows and columns (this also allows us to have the label for each row and col).
        if (row == 0 || col == 0) {
            return;
        }

        // here we select the new value.
        // unknown --> full --> empty --> unknown
        if (value == Nonogram.UNKNOWN) {
            newValue = Nonogram.FULL;
        } else if (value == Nonogram.FULL) {
            newValue = Nonogram.EMPTY;
        } else {
            newValue = Nonogram.UNKNOWN;
        }

        // subtract 1 from both row and column because the nonogram game starts from index  0, but our GUI cells start from 1.
        Assign userMove = new Assign(row - 1, col - 1, newValue);

        game.setState(userMove);
        stack.add(userMove);
    }

                /**
   * Quits the game.
   * 
   */
    private void quit() {
        dispose();
        System.exit(0);
    }

            /**
   * Load game moves from file.
   * 
   * @param filename name of file to load from
   */
    private void load(String filename) {
        // clear prev game
        clear();
        try {
            // read moves from file
            readFileScanner = new Scanner(new File(filename));
        
            while(readFileScanner.hasNext()) {
            // get a line of text from the file
              String gameLine = readFileScanner.nextLine();
        
              // process that line
              String[] move = gameLine.split(",");
        
              int row = Integer.parseInt(move[0]);
              int col = Integer.parseInt(move[1]);
              int num = Integer.parseInt(move[2]);
        
              // create a new Assign object from it, assign the move and add to stack.
              Assign newAssignment = new Assign(row, col, num);
              game.setState(newAssignment);
              stack.add(newAssignment);
            }
        
            readFileScanner.close();

            JOptionPane.showMessageDialog(this, "Game Loaded Successfully.", "Success!", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException err) {
            JOptionPane.showMessageDialog(this, "Could not load game file.", "Error!!", JOptionPane.ERROR_MESSAGE);
        }
    }

               /**
   * Save current game moves to file.
   * 
   * @param filename name of file to save to
   */
    private void save(String filename) {
        try {
			PrintStream writeFileScanner = new PrintStream(new File(filename));

			for (Assign move : stack) {
				writeFileScanner.println(move.getRow() + "," + move.getCol() + "," + move.getState());
			}

			writeFileScanner.close();
            JOptionPane.showMessageDialog(this, "Game saved Successfully.", "Success!", JOptionPane.INFORMATION_MESSAGE);
		} catch(FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error!!", JOptionPane.ERROR_MESSAGE);
			throw new NonogramException("Output file could not be created");
		}
    }

               /**
   * Clear the game.
   * 
   */
    private void clear() {
        stack.clear();
		game.clear();
    }

    /**
   * Undo last game move.
   * 
   */
    private void undo() {
        if (stack.isEmpty()) {
            System.out.println();
            System.out.println("There are no more moves you can undo");
            System.out.println();
            return;
          }
      
        Assign move = stack.pop();
        int value = move.getState();

        // undo move.
        // ensure that the right previous move is played remembering the sequence. 
        // unknown --> full --> empty --> unknown
        if (value == Nonogram.UNKNOWN) {
            value = Nonogram.EMPTY;
        } else if (value == Nonogram.FULL) {
            value = Nonogram.UNKNOWN;
        } else {
            value = Nonogram.FULL;
        }

        game.setState(move.getRow(), move.getCol(), value);
    }

    /**
   * Switch game difficulty.
   * 
   */
    private void changeDifficulty() {
        String gameFile;
        int gameHeight;
        int gameWidth;
        // incase we want to change the size of the cells.
        int cellWidth;
        int cellHeight;
        Object[] possibilities = {"easy", "medium", "hard"};
        String difficulty = (String)JOptionPane.showInputDialog(
            this,
            "Select Difficulty Level",
            "Change Difficulty",
            JOptionPane.PLAIN_MESSAGE,
            null,
            possibilities,
            "easy");

        // if difficulty has not changed, do nothing 
        if (difficulty == null || difficultyLevel == difficulty) {
            return;
        }

        // set new difficulty level
        difficultyLevel = difficulty;

        if (difficulty == "hard") {
            gameFile = "nons/6.non";
            cellHeight = 25;
            cellWidth = 25;
            gameHeight = 850;
            gameWidth = 1200;
        } else if (difficulty == "medium") {
            gameFile = "nons/test15.non";
            cellHeight = 25;
            cellWidth = 25;
            gameHeight = 750;
            gameWidth = 1000;
        } else {
            gameFile = NGFILE;
            cellHeight = cellHeightDimension;
            cellWidth = cellWidthDimension;
            gameHeight = 500;
            gameWidth = 800;
        }

        // create new game, clear cells and x and y axis NG pattern cells.
        // then make new game cells, add the gamePanel, repaint whole JFrame and increase/decrease the window height and width.
        createGame(gameFile);
        cells = null;
        xAxisNGPatternCells = null;
        yAxisNGPatternCells = null;
        centerLayoutPanel.removeAll();
        makeGameCells(cellWidth, cellHeight);
        centerLayoutPanel.add(gamePanel);
        revalidate();
        repaint();
        setMinimumSize(new Dimension(gameWidth, gameHeight));
    }

    /**
   * Show Help section.
   * 
   */
    private void help() {
        String helpMsg = "Nonogram is a puzzle where you must colour in/fill in the grid according to the patterns of contiguous full cells given in the rows and columns. \n Full cells are shown as a black square box, empty cells are shown as red cross, and unknown cells are simply empty, \n If a row or column is invalid the label for that row or column will be colored orange, a solved row or column is marked with a green label background, \n but it may still be wrong because of the other columns or rows - keep trying!"; 
        JTextArea textArea = new JTextArea(helpMsg);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        scrollPane.setPreferredSize(new Dimension(1000, 100));
        JOptionPane.showMessageDialog(this,
        scrollPane,
        "Help Section",
        JOptionPane.INFORMATION_MESSAGE);
    }

    /**
   * Change player name.
   * 
   */
    private void changeName() {
        String s = (String)JOptionPane.showInputDialog(
            this,
            "Please enter your name",
            "Player Name",
            JOptionPane.PLAIN_MESSAGE);

        //If string is not null and if it has more than one text.
        if ((s != null) && (s.length() > 0)) {
            playerName = s;
            nameLabel.setText("Hello " + playerName + ",");
            return;
        }
    }

    /**
   * Check if entire game has been won, show success dialog box if yes.
   * 
   */
    public void checkWin() {
        if (game.isSolved()) {
            JOptionPane.showMessageDialog(this, "You have won the game.", "Congratulations!!", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
   * Check if a specific row has been solved.
   * @param row row that changed
   */
    public void isRowSolved(int row) {
        NonogramPanelCell cellInterface = cells[row][0];
        // if a row has been solved, then the label corresponding to that row will turn green, and if not it will turn orange.

        // also remember that our GUI are 1 indexed, while game puzzle is 0 indexed. hence subtract one.
        if (game.isRowSolved(row - 1)) {
            cellInterface.setBg(Color.GREEN);
        } else {
            cellInterface.setBg(Color.ORANGE);
        }
    }

    /**
   * Check if a specific column has been solved.
   * @param col col that changed.
   */
    public void isColumnSolved(int col) {
        NonogramPanelCell cellInterface = cells[0][col];
        // if a col has been solved, then the label corresponding to that row will turn green, and if not it will turn orange.
        if (game.isRowSolved(col - 1)) {
            cellInterface.setBg(Color.GREEN);
        } else {
            cellInterface.setBg(Color.ORANGE);
        }
    }

      /**
   * Check if a specific column has been solved.
   * @param o the observable.
   * @param arg the object that changed.
   */
    @Override
    public void update(Observable o, Object arg) {
        try {
            Cell changedCell = (Cell) arg;
            // add 1, cause we are 1-indexed.
            int row = changedCell.getRow() + 1;
            int col = changedCell.getCol() + 1;
            int val = changedCell.getState();
            // System.out.println(changedCell.toString());
    
            NonogramPanelCell cellInterface = cells[row][col];
    
            // then set the value
            cellInterface.setValue(val);
    
            // check if row has won.
            isRowSolved(row);

            // check if col has won
            isColumnSolved(col);
    
            // check if game is won
            checkWin();
        } catch (NonogramException err) {
            System.out.println("An error occured");
        }
    }

    public static void main(String[] args) {
        new NonogramPanel();
    }
}
