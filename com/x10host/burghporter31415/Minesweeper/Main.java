/*****************************************
* Property of Dylan Porter
* Please ask for permission before using.
* Minesweeper Game
*****************************************/

package com.x10host.burghporter31415.Minesweeper;

class Main {
  public static void main(String[] args) {
    while(true) {
        try {
            MinesweeperGame game = new MinesweeperGame();
            game.play();
        } catch(Exception e) {
            System.out.println("Encountered an Error! Restarting game...\n");
        }
    }
  }
}