
package supersnake.graphic.ui;

import supersnake.attribute.Renderable;
import supersnake.util.Location;

public abstract class Component implements Renderable{
  protected int x, y;
  protected int width, height;

  public Component(int x, int y, int width, int height){
    setLocation(x, y);
    setSize(width, height);
  }

  public Component(Location location, int width, int height){
    setLocation(location);
    setSize(width, height);
  }

  public boolean contains(int x, int y){
    return this.x <= x && x < this.x + width && this.y <= y && y < this.y + height;
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public int getWidth(){
    return width;
  }

  public int getHeight(){
    return height;
  }

  public void setLocation(int x, int y){
    this.x = x;
    this.y = y;
  }

  public void setLocation(Location location){
    setLocation(location.getX(), location.getY());
  }

  public void setSize(int width, int height){
    this.width = width;
    this.height = height;
  }
}
