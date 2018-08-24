/*****************************************
* Property of Dylan Porter
* Please ask for permission before using.
* Minesweeper Game
*****************************************/

package com.x10host.burghporter31415.Minesweeper;

import java.util.Scanner;

class MinesweeperGame {

  private Player player;
  private Difficulty difficulty = Difficulty.EASY;
  private Scanner input = new Scanner(System.in);
  
  public MinesweeperGame(Player player, Difficulty difficulty) {
    this.player = player;
    this.difficulty = difficulty;
  }

  public MinesweeperGame(Player player) {
    this.player = player;
  }

  public MinesweeperGame() {}

  public void setPlayer(Player player) {
    this.player = player;
  }

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  public void play() {
      try {
        System.out.println("\nWelcome to Minesweeper! A new game has started.\n");

        if(player == null) {

            Difficulty difficulty;
            int rows = 0, columns = 0, level;

            while(rows <= 1) {
                System.out.print("Enter number of rows (Should be > 1): ");
                rows = input.nextInt();
            }
            while(columns <= 1) {
                System.out.print("Enter number of columns (Should be > 1): ");
                columns = input.nextInt();
            }

            System.out.print("Enter 0 for Easy | 1 for Medium | 2 for Hard: ");
            level = input.nextInt();
            
            if(level == 0) difficulty = Difficulty.EASY;
            else if(level == 1) difficulty = Difficulty.MEDIUM;
            else if(level == 2) difficulty = Difficulty.HARD;
            else {
                System.out.println("\nError: Wrong Difficulty Input. Assuming Easy... ");
                difficulty = Difficulty.EASY;
            }

            this.player = new Player(rows, columns, difficulty);

        }

        while(this.player.requestCoordinates()) {}
      } catch(Exception e) {
          System.out.println("Error: Invalid Input... Restarting Game");
      }
  }

}