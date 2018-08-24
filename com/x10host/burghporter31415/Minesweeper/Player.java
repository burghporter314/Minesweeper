/*****************************************
* Property of Dylan Porter
* Please ask for permission before using.
* Minesweeper Game
*****************************************/

package com.x10host.burghporter31415.Minesweeper;

import java.util.Scanner;

class Player {

  private Scanner input = new Scanner(System.in);
  private Board board;

  public Player(int rows, int columns, Difficulty difficulty) {
      board = new Board(rows, columns, difficulty);
  }

  public Player(int rows, int columns) {
      this(rows, columns, Difficulty.EASY);
  }

  public Player(int rows) {
      this(rows, rows);
  }

  public Player() {
      this(5);
  }

  public boolean requestCoordinates() {
      System.out.print("Enter Coordinates (x, y, 0 for click | 1 for flag): ");
      String result = input.nextLine().replace("(","").replace(")","");

      if(isValidCoords(result)) {

        String[] arr = result.split(","); 

        int[] coords = getCoords(arr, arr.length == 3);
        int flag = coords[2];

        if(flag == 0) {
            return board.submitClick(coords[0], coords[1]);
        } else if(flag == 1) {
            board.submitFlag(coords[0], coords[1]);
        } else {
            System.out.println("ERROR: Invalid input \'" + flag + "\'");
        }
      } else {
          System.out.println("ERROR: Invalid Coordinates");
      }

      return true;

  }

  private boolean isValidCoords(String s) {
      try {
        int commas = 0;
        for(int i = 0; i < s.length(); i++) {
            if(!((s.charAt(i) >= 48 && s.charAt(i) <= 57) || s.charAt(i) == ' ')) {
                if(s.charAt(i) == ',') {
                    commas++;
                    if(commas > 2) return false;
                } else {
                    return false;
                }
            }
        }

        String[] arr = s.split(",");
        int x = Integer.parseInt(arr[0].trim());
        int y = Integer.parseInt(arr[1].trim());

        if(x > this.board.getRows() || y > this.board.getColumns()) return false;
      } catch(Exception e) {
          return false;
      }
      return true;
  }

  private int[] getCoords(String[] args, boolean opt) {

      int thirdParam = 0;
      if(opt) { thirdParam = Integer.parseInt(args[2].trim()); }

      int[] arr = {Integer.parseInt(args[0].trim())-1, 
                    Integer.parseInt(args[1].trim())-1,
                    thirdParam};
      return arr;

  }


}