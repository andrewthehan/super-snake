
package game.object;

import game.util.CellBlock;

import java.awt.Point;

public class Food{
  private CellBlock body;
  private boolean isConsumed;

  public Food(int x, int y){
    body = new CellBlock(x, y);
    isConsumed = false;
  }

  public Food(Point location){
    body = new CellBlock(location);
  }

  public int getX(){
    return body.getX();
  }

  public int getY(){
    return body.getY();
  }

  public Point getLocation(){
    return body.getLocation();
  }

  public boolean isConsumed(){
    return isConsumed;
  }

  public void consume(){
    isConsumed = true;
  }

  public void reset(int x, int y){
    body.setLocation(x, y);
    isConsumed = false;
  }

  public void reset(Point location){
    body.setLocation(location);
    isConsumed = false;
  }
}
