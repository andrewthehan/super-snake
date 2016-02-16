
package game.util;

import java.awt.Point;

public class CellBlock{
  private int x, y;

  public CellBlock(int x, int y){
    this.x = x;
    this.y = y;
  }

  public CellBlock(Point location){
    this((int) location.getX(), (int) location.getY());
  }

  public boolean intersects(CellBlock cb){
    return x == cb.getX() && y == cb.getY();
  }

  @Override
  public boolean equals(Object o){
    return (o instanceof CellBlock) && (this == (CellBlock) o);
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public Point getLocation(){
    return new Point(x, y);
  }

  public void setX(int x){
    this.x = x;
  }

  public void setY(int y){
    this.y = y;
  }

  public void setLocation(int x, int y){
    this.x = x;
    this.y = y;
  }

  public void setLocation(Point location){
    x = (int) location.getX();
    y = (int) location.getY();
  }

  public void translate(int dx, int dy){
    x += dx;
    y += dy;
  }
}
