
package supersnake.game.map;

import supersnake.util.CellBlock;

public class Bounds{
  private int left, right, bottom, top;

  public Bounds(int left, int right, int bottom, int top){
    this.left = left;
    this.right = right;
    this.bottom = bottom;
    this.top = top;
  }

  public int getLeft(){
    return left;
  }

  public int getRight(){
    return right;
  }

  public int getBottom(){
    return bottom;
  }

  public int getTop(){
    return top;
  }

  public boolean contains(CellBlock cell){
    int x = cell.getX();
    int y = cell.getY();
    return left <= x && x <= right && bottom <= y && y <= top;
  }
}
