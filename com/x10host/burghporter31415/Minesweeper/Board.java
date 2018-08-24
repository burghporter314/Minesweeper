/*****************************************
* Property of Dylan Porter
* Please ask for permission before using.
* Minesweeper Game
*****************************************/

package com.x10host.burghporter31415.Minesweeper;

import java.util.*;

class Board {

  private Area[][] board;
  private Difficulty difficulty;
  private int rows, columns;
  private int nonMines = 0;

  public Board(int rows, int columns, Difficulty difficulty) {
    try {
        board = new Area[rows][columns];
        this.rows = rows;
        this.columns = columns;
        this.difficulty = difficulty;
        createBoard();
        addMines();
        findAdjacentMines();
        printBoard();
    } catch(Exception e) {
        System.out.println("Sorry, our game does not support these dimensions!\n");
        System.exit(0);
    }
  }

  public Board(int rows) {
    this(rows, rows, Difficulty.EASY);
  }

  public Board() {
    this(16, 10, Difficulty.EASY);
  }

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  public int getRows() {
      return this.rows;
  }

  public int getColumns() {
      return this.columns;
  }

  private void createBoard() {

    /*Initializes the entire board setting appropriate fields*/
    for(int i = 0; i < this.rows; i++) {
        for(int z = 0; z < this.columns; z++) {
            PlacementType type;
            if((i == 0 || i == this.rows-1) && (z == 0 || z == this.columns-1)) {
                type = PlacementType.EDGE;
            } else if((i == 0) || (i == this.rows-1) || (z == 0) || (z == this.columns-1)) {
                type = PlacementType.ROW;
            } else {
                type = PlacementType.OTHER;
            }
            this.board[i][z] = new Area(false, this.columns * i + z, type);
        }
    }

  }

  private void addMines() {

    /*
    * ArrayList{row 1, row 2 ... row N}
    * random number between 1-N inclusive to choose row
    * ArrayList.get(rowN) --> ArrayList {id1, id2, id3, ... , idN}
    * random number between 1-N inclusive and remove Element chosen
    */

    double difficultyPercent;

    if(this.difficulty == Difficulty.EASY)          { difficultyPercent = .15;   } 
    else if(this.difficulty == Difficulty.MEDIUM)   { difficultyPercent = .2125; } 
    else                                            { difficultyPercent = .375;  }

    int minesweepers = (int)(this.rows * this.columns * difficultyPercent);
    this.nonMines = (this.rows * this.columns) - minesweepers;

    Random rand = new Random();

    Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

    Integer[] arr = new Integer[this.columns];
    for(int z = 0; z < this.columns; z++) {
        arr[z] = z;
    }

    ArrayList<Integer> rows = new ArrayList<Integer>();

    for(int i = 0; i < this.rows; i++) {
        rows.add(i);
        map.put(i, new ArrayList(Arrays.asList(arr)));
    }

    while(minesweepers > 0) {
        
        int rowIndex, rowValue;

        if(rows.size() == 1) rowIndex = 0;
        else rowIndex = rand.nextInt(rows.size());

        rowValue = rows.get(rowIndex);

        ArrayList<Integer> tempArr = map.get(rowValue);

        int columnIndex, columnValue;

        if(tempArr.size() == 1) columnIndex = 0;
        else columnIndex = rand.nextInt(tempArr.size()-1);


        columnValue = tempArr.get(columnIndex);
        board[rowValue][columnValue].setIfMine(true);
        
        tempArr.remove(columnIndex);
        map.put(rowValue, tempArr);

        if(tempArr.size() == 0) {  rows.remove(rowIndex); }

        minesweepers--;
        
    }
  }

  private void findAdjacentMines() {
    for(int i = 0; i < this.rows; i++) {
        for(int z = 0; z < this.columns; z++) {

            Area area = board[i][z];
            Area[] areas;

            if(area.getIfMine()) continue;
            if(area.getPlacementType() == PlacementType.EDGE) {
                areas = new Area[3];
                if(i == 0 && z == 0) {
                    areas[0] = board[i][z+1];
                    areas[1] = board[i+1][z];
                    areas[2] = board[i+1][z+1];
                } else if(i == 0 && z == this.columns-1) {
                    areas[0] = board[i][z-1];
                    areas[1] = board[i+1][z-1];
                    areas[2] = board[i+1][z];
                } else if(i == this.rows-1 && z == 0) {
                    areas[0] = board[i-1][z];
                    areas[1] = board[i-1][z+1];
                    areas[2] = board[i][z+1];
                } else {
                    areas[0] = board[i][z-1];
                    areas[1] = board[i-1][z-1];
                    areas[2] = board[i-1][z];
                }          
            } else if(area.getPlacementType() == PlacementType.ROW) {
                areas = new Area[5];
                if(i == 0) {
                    areas[0] = board[i][z-1];
                    areas[1] = board[i][z+1];
                    areas[2] = board[i+1][z-1];
                    areas[3] = board[i+1][z];
                    areas[4] = board[i+1][z+1];
                } else if(i == this.rows-1) {
                    areas[0] = board[i][z-1];
                    areas[1] = board[i][z+1];
                    areas[2] = board[i-1][z-1];
                    areas[3] = board[i-1][z];
                    areas[4] = board[i-1][z+1];
                } else if(z == 0) {
                    areas[0] = board[i-1][z];
                    areas[1] = board[i+1][z];
                    areas[2] = board[i-1][z+1];
                    areas[3] = board[i][z+1];
                    areas[4] = board[i+1][z+1];
                } else {
                    areas[0] = board[i-1][z];
                    areas[1] = board[i+1][z];
                    areas[2] = board[i-1][z-1];
                    areas[3] = board[i][z-1];
                    areas[4] = board[i+1][z-1];
                }
            } else {
                areas = new Area[8];
                areas[0] = board[i][z-1];
                areas[1] = board[i][z+1];
                areas[2] = board[i+1][z-1];
                areas[3] = board[i+1][z];
                areas[4] = board[i+1][z+1];
                areas[5] = board[i-1][z-1];
                areas[6] = board[i-1][z];
                areas[7] = board[i-1][z+1];
            }
            board[i][z].setAdjacentAreas(areas);
            board[i][z].setAdjacentMines(getNumMines(areas));
        }
    }
  }

  private int getNumMines(Area[] areas) {

      int counter = 0;
      for(int i = 0; i < areas.length; i++) {
          if(areas[i].getIfMine()) {
              counter++;
          }
      }
      return counter;
  }

  public boolean submitClick(int x, int y) {
      if(board[x][y].getIfMine()) {
          printGameOver(true);
          return false;
      } else {
          revealZeros(board[x][y], false);
          printBoard();
          if(this.nonMines == 0) {
              printGameOver(false);
              return false;
          }
      }
      return true;
  }

  public void submitFlag(int x, int y) {
      board[x][y].setIfFlagged(!board[x][y].getIfFlagged());
      printBoard();
  }

  /*Recursive Solution to display all areas if zero area has been picked*/
  public void revealZeros(Area area, boolean otherIteration) {

      this.nonMines--;
      area.setVisible(true);
      Area[] areas = area.getAdjacentAreas();

      for(int i = 0; i < areas.length; i++) {

          if(areas[i] == null) { return; }

          if(!areas[i].getIfVisible() && areas[i].getAdjacentMines() == 0 && !areas[i].getIfMine()) {
              revealZeros(areas[i], true);
          } else if(!areas[i].getIfVisible() 
                    && !areas[i].getIfMine() 
                    && (otherIteration || area.getAdjacentMines() == 0)) {
              areas[i].setVisible(true);
              this.nonMines--;
          }
          
      }
  }

  public void printBoard() {

    System.out.print("\n\n\t");
    for(int a = 0; a < this.columns; a++) {
        System.out.print((a+1) + ":\t");
    }
    System.out.println("\n");

    for(int i = 0; i < this.rows; i++) {
        System.out.print((i+1) + ": \t");
        for(int z = 0; z < this.columns; z++) {
            if(board[i][z].getIfVisible()) {
                System.out.print(board[i][z].getAdjacentMines() + "\t");
            } else {
                if(board[i][z].getIfFlagged()) {
                    System.out.print("F\t");
                } else {
                    System.out.print(".\t");
                }
            }
        }
        System.out.println("\n");
    }
    System.out.println("\n");
  }

  public void printGameOver(boolean lost) {

    if(lost) {
        System.out.println("\n\n\tGame Over\n");
    } else {
        System.out.println("\n\n\tYou Won!\n");
    }
    System.out.print("\t");
    for(int a = 0; a < this.columns; a++) {
        System.out.print((a+1) + ":\t");
    }
    System.out.println("\n");

    for(int i = 0; i < this.rows; i++) {
        System.out.print((i+1) + ": \t");
        for(int z = 0; z < this.columns; z++) {
            if(board[i][z].getIfMine()) {
                System.out.print("X\t");
            } else {
                System.out.print(board[i][z].getAdjacentMines() + "\t");
            }
        }
        System.out.println("\n");
    }
    System.out.println("\n");
  }
}