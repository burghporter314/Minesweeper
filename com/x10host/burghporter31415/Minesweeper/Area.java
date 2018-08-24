/*****************************************
* Property of Dylan Porter
* Please ask for permission before using.
* Minesweeper Game
*****************************************/

package com.x10host.burghporter31415.Minesweeper;

class Area {

  private boolean isMine;
  private boolean isFlagged = false;
  private int id;
  private Area[] adjacentAreas;
  private PlacementType placement;
  private int adjacentMines;
  private boolean visible = false;

  public Area(boolean isMine, int id, PlacementType type) {
    this.isMine = isMine;
    this.id = id;
    setNumAdjacentAreas(type);
  }

  public void setAdjacentAreas(Area[] areas) {
    this.adjacentAreas = areas;
  }

  public void setAdjacentMines(int adjacentMines) {
      this.adjacentMines = adjacentMines;
  }

  public void setIfMine(boolean isMine) {
      this.isMine = isMine;
  }

  public void setVisible(boolean visible) {
      this.visible = visible;
  }

  public void setIfFlagged(boolean isFlagged) {
      this.isFlagged = isFlagged;
  }

  public int getAdjacentMines() {
      return this.adjacentMines;
  }

  public Area[] getAdjacentAreas() {
      return this.adjacentAreas;
  }

  public PlacementType getPlacementType() {
      return this.placement;
  }

  public boolean getIfVisible() {
      return this.visible;
  }

  public boolean getIfMine() {
      return this.isMine;
  }

  public boolean getIfFlagged() {
      return this.isFlagged;
  }

  private void setNumAdjacentAreas(PlacementType type) {
    if(type == PlacementType.EDGE) 
      adjacentAreas = new Area[3];
    else if(type == PlacementType.ROW) 
      adjacentAreas = new Area[5];
    else 
      adjacentAreas = new Area[8];
    this.placement = type;
  }
}